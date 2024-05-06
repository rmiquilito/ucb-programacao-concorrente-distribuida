import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Family {
    private CountDownLatch familyWalkLatch = new CountDownLatch(3);
    private CountDownLatch leftLatch = new CountDownLatch(3);
    private ArrayList<Guest> members = new ArrayList<Guest>();
    private Room room;
    private Boolean plans = new Random().nextBoolean();
    private String surname;

    public Family(String surname) {
        this.surname = surname;
    }

    public CountDownLatch getFamilyWalkLatch() {
        return this.familyWalkLatch;
    }

    public CountDownLatch getLeftLatch() {
        return this.leftLatch;
    }

    public ArrayList<Guest> getMembers() {
        return this.members;
    }

    public Room getRoom() {
        return this.room;
    }

    public Boolean getPlans() {
        return this.plans;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
