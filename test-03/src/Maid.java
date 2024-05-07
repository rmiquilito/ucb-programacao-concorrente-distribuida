import java.util.Random;
import java.util.concurrent.TimeUnit;
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

    private ReentrantLock getKey() {
        try {
            if (this.passkey.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    if (this.hotel.getKeyStorage().size() > 0) {
                        int i = new Random().nextInt(this.hotel.getKeyStorage().size());
                        ReentrantLock key = this.hotel.getKeyStorage().get(i);

                        this.hotel.removeKey(key);
                        return key;
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

    private void clean(ReentrantLock key) {
        key.lock();
        try {
            this.hotel.getRoom(key).getAccess().acquire();

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.hotel.getRoom(key).getAccess().release();
            key.unlock();
        }
    }
}
