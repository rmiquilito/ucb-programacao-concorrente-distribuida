import java.util.concurrent.locks.ReentrantLock;

public class Maid extends Thread {
    private Hotel hotel;
    private ReentrantLock passkey;
    private Boolean onDuty = true;

    public Maid(String name, Hotel hotel, ReentrantLock passkey) {
        super(name);
        this.hotel = hotel;
        this.passkey = passkey;
    }

    public ReentrantLock getPasskey() {
        return this.passkey;
    }

    public void setOnDuty(Boolean onDuty) {
        this.onDuty = onDuty;
    }
}
