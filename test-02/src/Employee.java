public class Employee extends Thread {
    private Store store;
    private Bank bank;
    private Account salaryAccount;
    private Account investmentAccount;

    public Employee(String name, Store store, Bank bank) {
        super(name);
        this.store = store;
        this.bank = bank;
        this.salaryAccount = new Account(bank, this, 0.0);
        this.investmentAccount = new Account(bank, this, 0.0);

        bank.setEmployeeAccounts(this.salaryAccount);
        bank.setEmployeeAccounts(this.investmentAccount);
    }

    public Store getStore() {
        return this.store;
    }

    public Account getSalaryAccount() {
        return this.salaryAccount;
    }

    public Account getInvestmentAccount() {
        return this.investmentAccount;
    }
}
