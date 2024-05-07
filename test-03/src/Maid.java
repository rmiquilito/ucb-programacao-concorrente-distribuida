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
            if (this.passkey.tryLock(10, TimeUnit.SECONDS)) {
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
        System.out.println(this.getName() + " encontra o quarto " + this.hotel.getRoom(key).getNumber() + ".");
        try {
            this.hotel.getRoom(key).getAccess().acquire();
            System.out.println(this.getName() + " limpa o quarto " + this.hotel.getRoom(key).getNumber() + ".");

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.getName() + " finaliza a limpeza.");
            this.hotel.getRoom(key).getAccess().release();
            key.unlock();
        }
    }

    public void run() {
        while (this.onDuty) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ReentrantLock key = this.getKey();
            if (key != null) {
                clean(key);
            }
        }
    }
}
