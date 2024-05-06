import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    private ReentrantLock roomsLock = new ReentrantLock();
    private ReentrantLock storageLock = new ReentrantLock();
    private ArrayList<ReentrantLock> keyStorage = new ArrayList<ReentrantLock>();
    private ArrayList<String> complaints = new ArrayList<String>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Receptionist> receptionists = new ArrayList<Receptionist>();
    private ArrayList<Maid> maids = new ArrayList<Maid>();

    public Hotel() {
        for (int i = 0; i < 10; i++) {
            this.rooms.add(new Room(1 + i));
        }
        for (int i = 0; i < 5; i++) {
            this.receptionists.add(new Receptionist("receptionist" + (1 + i), this, roomsLock));
        }
        for (int i = 0; i < 10; i++) {
            this.maids.add(new Maid("maid" + (1 + i), this, storageLock));
        }
    }

    public ArrayList<ReentrantLock> getKeyStorage() {
        return this.keyStorage;
    }

    public ArrayList<String> getComplaints() {
        return this.complaints;
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    public ArrayList<Receptionist> getReceptionists() {
        return this.receptionists;
    }

    public ArrayList<Maid> getMaids() {
        return this.maids;
    }
}
