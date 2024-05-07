import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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

    public Boolean booking() {
        for (Receptionist receptionist : this.getHotel().getReceptionists()) {
            try {
                if (receptionist.getService().tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        while (receptionist.getLock().isLocked()) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        receptionist.request();

                        while (!receptionist.getFlag()) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        ReentrantLock key = receptionist.getFrontDesk();
                        if (key != null) {
                            this.getFamily().setRoom(this.getHotel().getRoom(key));
                            this.key = key;

                            return true;
                        } else {
                            return false;
                        }
                    } finally {
                        receptionist.setFlag(false);
                        receptionist.getService().unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void giveUp() {
        for (Guest guest : this.getFamily().getMembers()) {
            if (guest instanceof Father) {
                continue;
            }
            guest.interrupt();
        }
    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            if (this.booking()) {
                this.key.lock();
                try {
                    this.settle(this.settleLatch);
                } finally {
                    this.key.unlock();
                }

                if (this.getFamily().getPlans()) {
                    try {
                        this.getFamily().getFamilyWalkLatch().await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.getHotel().addKey(this.key);
                    this.walk(this.fatherWalkLatch);
                }

                this.key.lock();
                try {
                    this.rest(this.restLatch);
                } finally {
                    this.key.unlock();
                }

                try {
                    this.getFamily().getLeftLatch().await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.getHotel().addKey(this.key);
                this.getFamily().getRoom().setOccupied(false);
                return;
            }
        }

        this.giveUp();
    }
}
