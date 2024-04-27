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
}
