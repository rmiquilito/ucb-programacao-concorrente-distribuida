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

    public Employee getCoworker() {
        int i = 0;
        for (Employee employee : this.store.getEmployees()) {
            if (employee != this) {
                i = this.store.getEmployees().indexOf(employee);
            }
        }

        return this.store.getEmployees().get(i);
    }

    public void run() {
        while (!this.store.communicate() || this.store.funds()) {
            Boolean lateWithdraw = this.salaryAccount.getStatement().size() < this.getCoworker().getSalaryAccount()
                    .getStatement().size();
            Boolean notLateWithdraw = this.salaryAccount.getStatement().size() == this.getCoworker().getSalaryAccount()
                    .getStatement().size();

            if (lateWithdraw) {
                if (this.store.getAccount().getBalance() >= 700.0) {
                    this.salaryAccount.withdraw(this.store.getAccount(), 700.0);
                    this.salaryAccount.deposit(700.0 * 0.2, this.getInvestmentAccount());
                }

            }

            if (notLateWithdraw) {
                if (this.store.getAccount().getBalance() >= 1400.0) {
                    this.salaryAccount.withdraw(this.store.getAccount(), 700.0);
                    this.salaryAccount.deposit(700.0 * 0.2, this.getInvestmentAccount());
                } else if (this.store.communicate()) {
                    return;
                }
            }
        }
    }
}
