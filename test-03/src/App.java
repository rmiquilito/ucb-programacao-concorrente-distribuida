import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Hotel hotel;
        ArrayList<Family> families = new ArrayList<Family>();

        hotel = new Hotel();
        for (int i = 0; i < 13; i++) {
            Family family = new Family("surname" + (1 + i));

            family.addMember(new Father("father" + (1 + i), hotel, family));
            for (int j = 0; j < 3; j++) {
                family.addMember(new Guest("guest" + (1 + j), hotel, family));
            }

            families.add(family);
        }

        for (Receptionist receptionist : hotel.getReceptionists()) {
            receptionist.start();
        }
        for (Maid maid : hotel.getMaids()) {
            maid.start();
        }
        for (Family family : families) {
            for (Guest guest : family.getMembers()) {
                guest.start();
            }
        }

        for (Family family : families) {
            for (Guest guest : family.getMembers()) {
                try {
                    guest.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Receptionist receptionist : hotel.getReceptionists()) {
            receptionist.interrupt();
        }
        for (Maid maid : hotel.getMaids()) {
            maid.setOnDuty(false);
        }

        for (Maid maid : hotel.getMaids()) {
            try {
                maid.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Maid maid : hotel.getMaids()) {
            try {
                maid.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Reclamações? " + hotel.getComplaints());
    }
}
