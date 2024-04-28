import java.util.ArrayList;

public class Store {
    private String name;
    private Bank bank;
    private Account account;

    public Store(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
        this.account = new Account(bank, this, 0.0); // Inicializa conta com 0.0 de saldo

        bank.addStoreAccounts(this.account); // Faz guardar conta na lista do banco
    }

    public String getName() {
        return this.name;
    }

    public Account getAccount() {
        return this.account;
    }

    // Percorre a lista de contas de titular-funcionário no qual trabalha nesta loja e retorna com os funcionários encontrados
    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>();

        for (Account account : this.bank.getEmployeeAccounts()) {
            Employee employee = (Employee) account.getHolder(); // Converte o tipo Object do titular em Employee para guardar na lista de retorno
            if (employee.getStore() == this) {
                employees.add(employee);
            }
        }

        return employees;
    }

    // Observa a sinalização esclarecida em ./Bank e a calcula: se não houver de receber mais depósito, sinaliza, a fim de honrar com os pagamentos só até com o montante acumulado
    public Boolean communicate() {
        return !this.bank.pendingDeposits();
    }

    // Observa o saldo e retorna se há valor para salário. Faz permitir que, a despeito de communicate(), seja possível o saque de salário remanescente, quando não há equidistância ou quando há um ou mais ciclos de pagamento
    public Boolean funds() {
        return this.getAccount().getBalance() >= 700.0;
    }
}
