import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Receptionist extends Thread {
    private Hotel hotel;
    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock service = new ReentrantLock();
    private ReentrantLock passkey;
    private ReentrantLock frontDesk;
    private Condition condition = lock.newCondition();
    private Boolean flag = false;

    public Receptionist(String name, Hotel hotel, ReentrantLock passkey) {
        super(name);
        this.hotel = hotel;
        this.passkey = passkey;
    }

    public ReentrantLock getLock() {
        return this.lock;
    }

    public ReentrantLock getService() {
        return this.service;
    }

    public ReentrantLock getFrontDesk() {
        return this.frontDesk;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFrontDesk(ReentrantLock frontDesk) {
        this.frontDesk = frontDesk;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
