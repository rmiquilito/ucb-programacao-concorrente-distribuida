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
        for (Receptionist receptionist : this.hotel.getReceptionists()) {
            try {
                if (receptionist.getService().tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(receptionist.getName() + " atende o " + this.fullName + ".");
                        while (receptionist.getLock().isLocked()) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(this.fullName + " pede uma reserva ao " + receptionist.getName() + ".");
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
                            System.out.println(this.fullName + " consegue uma reserva.");
                            this.family.setRoom(this.hotel.getRoom(key));
                            this.key = key;

                            return true;
                        } else {
                            System.out.println(this.fullName + " não consegue uma reserva.");
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
        for (Guest guest : this.family.getMembers()) {
            if (guest instanceof Father) {
                continue;
            }
            System.out.println(guest.fullName + " desiste e volta para casa.");
            guest.interrupt();
        }

        this.hotel.addComplaint("A família " + this.family.getSurname() + " deixa uma reclamação");
    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            if (this.booking()) {
                Room room = this.hotel.getRoom(this.key);
                this.key.lock();
                try {
                    System.out.println(this.fullName + " entra no quarto " + room.getNumber() + ".");
                    this.settle(this.settleLatch);
                } finally {
                    this.key.unlock();
                }

                if (this.family.getPlans()) {
                    try {
                        this.family.getFamilyWalkLatch().await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(this.fullName + " deixa chave do quarto " + room.getNumber() + " na recepção.");
                    this.hotel.addKey(this.key);
                    this.walk(this.fatherWalkLatch);
                }

                this.key.lock();
                try {
                    if (this.family.getPlans()) {
                        System.out.println(this.fullName + " entra no quarto " + room.getNumber() + ".");
                    }
                    this.rest(this.restLatch);
                } finally {
                    this.key.unlock();
                }

                try {
                    this.family.getLeftLatch().await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.fullName + " entrega chave do quarto " + room.getNumber() + " na recepção.");
                this.hotel.addKey(this.key);
                this.family.getRoom().setOccupied(false);
                System.out.println(this.fullName + " volta para casa.");
                return;
            }
        }

        System.out.println(this.fullName + " desiste e volta para casa.");
        this.giveUp();
    }
}
