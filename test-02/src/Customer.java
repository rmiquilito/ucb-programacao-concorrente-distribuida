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

    public Double decideCost() {
        Double value = ((1 + new Random().nextInt(2)) * 100.0);
        if (this.account.getBalance() == 100.0) {
            value = 100.0;
        }

        return value;
    }

    public Store chooseStore() {
        Store store = (Store) this.bank.getStoreAccounts().get(this.targetStore).getHolder();
        this.targetStore = (this.targetStore + 1) % 2;

        return store;
    }

    public void run() {
        while (!this.account.scan()) {
            Store store = chooseStore();
            Double cost = decideCost();

            this.account.deposit(cost, store.getAccount());
        }
    }
}
