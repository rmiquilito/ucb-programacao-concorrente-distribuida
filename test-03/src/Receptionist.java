import java.util.Random;
import java.util.concurrent.TimeUnit;
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

    public Room getUnoccupiedRoom() {
        try {
            if (this.passkey.tryLock(4, TimeUnit.SECONDS)) {
                try {
                    if (this.hotel.getUnoccupiedRooms().size() > 0) {
                        int i = new Random().nextInt(this.hotel.getUnoccupiedRooms().size());
                        Room room = this.hotel.getUnoccupiedRooms().get(i);

                        room.setOccupied(true);
                        return room;
                    }
                } finally {
                    this.passkey.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void request() {
        this.lock.lock();
        try {
            condition.signal();
        } finally {
            this.lock.unlock();
        }
    }
}
