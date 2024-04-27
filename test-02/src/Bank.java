import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private ReentrantLock transferLock;
    private ReentrantLock monitoringLock;
    private ArrayList<Account> storeAccounts;
    private ArrayList<Account> employeeAccounts;
    private ArrayList<Account> customerAccounts;
    private ArrayList<Account> monitoredAccounts;

    public Bank() {
        this.transferLock = new ReentrantLock();
        this.monitoringLock = new ReentrantLock();

        this.storeAccounts = new ArrayList<Account>();
        this.employeeAccounts = new ArrayList<Account>();
        this.customerAccounts = new ArrayList<Account>();

        this.monitoredAccounts = new ArrayList<Account>();
    }

    public ArrayList<Account> getStoreAccounts() {
        return this.storeAccounts;
    }

    public ArrayList<Account> getEmployeeAccounts() {
        return this.employeeAccounts;
    }

    public ArrayList<Account> getCustomerAccounts() {
        return this.customerAccounts;
    }

    public void setStoreAccounts(Account account) {
        this.storeAccounts.add(account);
    }

    public void setEmployeeAccounts(Account account) {
        this.employeeAccounts.add(account);
    }

    public void setCustomerAccounts(Account account) {
        this.customerAccounts.add(account);
    }

    public Boolean pendingDeposits() {
        return monitoredAccounts.size() != getCustomerAccounts().size();
    }

    public void monitor(Account account) {
        this.monitoringLock.lock();

        try {
            this.monitoredAccounts.add(account);
        } finally {
            this.monitoringLock.unlock();
        }
    }

    public void transfer(Account accountSender, Double value, Account accountRecipient) {
        this.transferLock.lock();

        try {
            Double balance = accountSender.getBalance();

            accountSender.setBalance(balance - value);
            accountRecipient.setBalance(accountRecipient.getBalance() + value);

            accountSender.getStatement()
                    .add(accountSender.getHolderName() + "->" + value + "->" + accountRecipient.getHolderName());
            accountRecipient.getStatement()
                    .add(accountRecipient.getHolderName() + "<-" + value + "<-" + accountSender.getHolderName());
        } finally {
            this.transferLock.unlock();
        }
    }
}
