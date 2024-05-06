import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
    private Boolean occupied = false;
    private ReentrantLock key = new ReentrantLock();
    private Semaphore access = new Semaphore(4);
    private int number;

    public Room(int number) {
        this.number = number;
    }

    public Boolean getOccupied() {
        return this.occupied;
    }

    public ReentrantLock getKey() {
        return this.key;
    }

    public Semaphore getAccess() {
        return this.access;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public int getNumber() {
        return this.number;
    }
}
