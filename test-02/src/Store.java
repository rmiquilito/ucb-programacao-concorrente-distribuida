import java.util.ArrayList;

public class Store {
    private String name;
    private Bank bank;
    private Account account;

    public Store(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
        this.account = new Account(bank, this, 0.0);

        bank.setStoreAccounts(this.account);
    }

    public String getName() {
        return this.name;
    }

    public Account getAccount() {
        return this.account;
    }

    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>();

        for (Account account : this.bank.getEmployeeAccounts()) {
            Employee employee = (Employee) account.getHolder();
            if (employee.getStore() == this) {
                employees.add(employee);
            }
        }

        return employees;
    }

    public Boolean communicate() {
        return !this.bank.pendingDeposits();
    }

    public Boolean funds() {
        return this.getAccount().getBalance() >= 700.0;
    }
}
