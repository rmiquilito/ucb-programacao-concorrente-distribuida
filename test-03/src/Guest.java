import java.util.concurrent.CountDownLatch;

public class Guest extends Thread {
    private Hotel hotel;
    private Family family;

    public Guest(String name, Hotel hotel, Family family) {
        super(name);
        this.hotel = hotel;
        this.family = family;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public Family getFamily() {
        return this.family;
    }

    public void settle(CountDownLatch latch) {
        Room room = this.getFamily().getRoom();
        try {
            room.getAccess().acquire();

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

    public void walk(CountDownLatch latch) {
        if (latch != null) {
            latch.countDown();
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void rest(CountDownLatch latch) {
        Room room = this.getFamily().getRoom();
        try {
            room.getAccess().acquire();

            if (latch != null) {
                latch.countDown();
            }
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            room.getAccess().release();
        }
    }
}
