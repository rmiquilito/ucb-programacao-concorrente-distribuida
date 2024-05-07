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
    }
}
