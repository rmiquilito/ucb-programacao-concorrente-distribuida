import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Father extends Guest {
    private ReentrantLock key;
    private CountDownLatch settleLatch = new CountDownLatch(1);
    private CountDownLatch fatherWalkLatch = new CountDownLatch(1);
    private CountDownLatch restLatch = new CountDownLatch(1);

    public Father(String name, Hotel hotel, Family family) {
        super(name, hotel, family);
    }

    public CountDownLatch getSettleLatch() {
        return this.settleLatch;
    }

    public CountDownLatch getFatherWalkLatch() {
        return this.fatherWalkLatch;
    }

    public CountDownLatch getRestLatch() {
        return this.restLatch;
    }
}
