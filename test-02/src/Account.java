import java.util.ArrayList;

public class Account {
    private Bank bank;
    private Object holder;
    private Double balance;
    private ArrayList<String> statement;

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

    public Boolean scan() {
        if (this.balance <= 0.0) {
            this.bank.monitor(this);

            return true;
        }

        return false;
    }

    public void deposit(Double value, Account accountRecipient) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        this.bank.transfer(this, value, accountRecipient);
    }

    public void withdraw(Account accountSender, Double value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        this.bank.transfer(accountSender, value, this);
    }
}
