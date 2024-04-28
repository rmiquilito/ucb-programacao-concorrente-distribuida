import java.util.ArrayList;

public class Account {
    private Bank bank;
    private Object holder; // Guarda titular
    private Double balance;
    private ArrayList<String> statement; // Guarda registros de transação

    public Account(Bank bank, Object holder, Double balance) {
        this.bank = bank;
        this.holder = holder;
        this.balance = balance;
        this.statement = new ArrayList<String>();
    }

    public Object getHolder() {
        return this.holder;
    }

    public Double getBalance() {
        return this.balance;
    }

    public ArrayList<String> getStatement() {
        return this.statement;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getHolderName() {
        if (this.holder.getClass() == Store.class) {
            return ((Store) this.holder).getName();
        } else if (this.holder.getClass() == Employee.class) {
            return ((Employee) this.holder).getName();
        } else {
            return ((Customer) this.holder).getName();
        }
    }

    // Faz uma varredura no saldo e retorna se está vazio
    public Boolean scan() {
        if (this.balance <= 0.0) {
            this.bank.monitor(this); // Sinaliza ao banco que monitore esta conta

            return true;
        }

        return false;
    }

    // Recebe um valor e uma conta destinatária, então segue com a lógica de tranferência
    public void deposit(Double value, Account accountRecipient) {
        try {
            Thread.sleep(1000); // Suspende a thread por um período
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        this.bank.transfer(this, value, accountRecipient); // Como argumento, envia, também, a referência desta conta remetente
    }

    // Recebe uma conta remetente e um valor, então segue com a lógica de tranferência
    public void withdraw(Account accountSender, Double value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        this.bank.transfer(accountSender, value, this); // Como argumento, envia, também, a referência desta conta destinatária
    }
}
