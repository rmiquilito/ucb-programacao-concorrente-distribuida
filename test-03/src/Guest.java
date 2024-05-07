import java.util.concurrent.CountDownLatch;

public class Guest extends Thread {
    protected Hotel hotel;
    protected Family family;
    protected String fullName;

    public Guest(String name, Hotel hotel, Family family) {
        super(name);
        this.hotel = hotel;
        this.family = family;
        this.fullName = this.getName() + " " + family.getSurname();
    }

    public void settle(CountDownLatch latch) {
        Room room = this.family.getRoom();
        try {
            room.getAccess().acquire();
            System.out.println(this.fullName + " se acomoda no quarto " + room.getNumber() + ".");

            if (latch != null) {
                latch.countDown();
            }
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            room.getAccess().release();
        }
    }

    public void walk(CountDownLatch latch) {
        if (latch != null) {
            latch.countDown();
        }
        try {
            System.out.println(this.fullName + " passeia pela cidade.");
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void rest(CountDownLatch latch) {
        Room room = this.family.getRoom();
        try {
            room.getAccess().acquire();
            System.out.println(this.fullName + " descança no quarto " + room.getNumber() + ".");

            if (latch != null) {
                latch.countDown();
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            room.getAccess().release();
        }
    }

    public void run() {
        try {
            this.family.getFather().getSettleLatch().await();
        } catch (InterruptedException e) {
            return;
        }

        this.settle(null);
        this.family.getFamilyWalkLatch().countDown();

        if (this.family.getPlans()) {
            try {
                this.family.getFather().getFatherWalkLatch().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.walk(null);
        }

        try {
            this.family.getFather().getRestLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.rest(null);
        System.out.println(this.fullName + " volta para casa.");
        this.family.getLeftLatch().countDown();
    }
}
