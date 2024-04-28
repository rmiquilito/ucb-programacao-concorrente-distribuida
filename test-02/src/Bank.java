import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private ReentrantLock transferLock; // Guarda um bloqueio para concorrência de transferências
    private ReentrantLock monitoringLock; // Guarda um bloqueio para concorrência de atualizações internas de monitoramento
    private ArrayList<Account> storeAccounts; // Guarda uma lista de contas de lojas
    private ArrayList<Account> employeeAccounts; // Guarda uma lista de contas de funcionários
    private ArrayList<Account> customerAccounts; // Guarda uma lista de contas de clientes
    private ArrayList<Account> monitoredAccounts; // Guarda uma lista de contas monitoradas

    public Bank() {
        this.transferLock = new ReentrantLock();
        this.monitoringLock = new ReentrantLock();

        this.storeAccounts = new ArrayList<Account>();
        this.employeeAccounts = new ArrayList<Account>();
        this.customerAccounts = new ArrayList<Account>();

        this.monitoredAccounts = new ArrayList<Account>();
    }

    public ArrayList<Account> getStoreAccounts() {
        return this.storeAccounts;
    }

    public ArrayList<Account> getEmployeeAccounts() {
        return this.employeeAccounts;
    }

    public ArrayList<Account> getCustomerAccounts() {
        return this.customerAccounts;
    }

    public void addStoreAccounts(Account account) {
        this.storeAccounts.add(account);
    }

    public void addEmployeeAccounts(Account account) {
        this.employeeAccounts.add(account);
    }

    public void addCustomerAccounts(Account account) {
        this.customerAccounts.add(account);
    }

    // Verifica se a quantidade de contas esvaziadas é igual a quantidade de contas titular-cliente, compradores, e retorna com a sinalização
    public Boolean pendingDeposits() {
        return monitoredAccounts.size() != getCustomerAccounts().size(); // Se há diferença, sinaliza que novos depósitos são possíveis
    }

    // Adiciona uma conta à lista de contas monitoradas
    public void monitor(Account account) {
        this.monitoringLock.lock(); // Garante a corretude da lista de contas monitoradas

        try {
            this.monitoredAccounts.add(account);
        } finally {
            this.monitoringLock.unlock();
        }
    }

    // Recalcula saldo e guarda um registro. Na representação, executa uma transferência e gera um registro no extrato
    public void transfer(Account accountSender, Double value, Account accountRecipient) {
        this.transferLock.lock(); // Garante a corretude dos saldos

        try {
            Double balance = accountSender.getBalance();

            accountSender.setBalance(balance - value); // Subtrai do saldo da conta remetente
            accountRecipient.setBalance(accountRecipient.getBalance() + value); // Adiciona ao saldo da conta destinatária

            accountSender.getStatement()
                    .add(accountSender.getHolderName() + "->" + value + "->" + accountRecipient.getHolderName()); // Registra no extrato da conta remetente a transação
            accountRecipient.getStatement()
                    .add(accountRecipient.getHolderName() + "<-" + value + "<-" + accountSender.getHolderName()); // Registra no extrato da conta destinatária a transação
        } finally {
            this.transferLock.unlock();
        }
    }
}
