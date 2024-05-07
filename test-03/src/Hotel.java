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
        for (int i = 0; i < 4; i++) {
            this.rooms.add(new Room(1 + i));
        }
        for (int i = 0; i < 2; i++) {
            this.receptionists.add(new Receptionist("receptionist" + (1 + i), this, roomsLock));
        }
        for (int i = 0; i < 6; i++) {
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

    public void addKey(ReentrantLock key) {
        this.keyStorage.add(key);
    }

    public void removeKey(ReentrantLock key) {
        this.keyStorage.remove(key);
    }

    public void addComplaint(String complaint) {
        this.complaints.add(complaint);
    }

    public ArrayList<Room> getUnoccupiedRooms() {
        ArrayList<Room> unoccupiedRooms = new ArrayList<Room>();
        for (Room room : this.rooms) {
            if (!room.getOccupied()) {
                unoccupiedRooms.add(room);
            }
        }

        return unoccupiedRooms;
    }

    public Room getRoom(ReentrantLock key) {
        for (Room room : rooms) {
            if (room.getKey() == key) {
                return room;
            }
        }

        return null;
    }
}
