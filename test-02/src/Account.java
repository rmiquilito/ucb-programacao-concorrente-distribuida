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

}
