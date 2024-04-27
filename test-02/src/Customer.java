import java.util.Random;

public class Customer extends Thread {
    private Bank bank;
    private Account account;
    private int targetStore;

    public Customer(String name, Bank bank) {
        super(name);
        this.bank = bank;
        this.account = new Account(bank, this, 1300.0);
        this.targetStore = new Random().nextInt(2);

        bank.setCustomerAccounts(this.account);
    }

    public Account getAccount() {
        return this.account;
    }
}
