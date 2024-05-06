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
}
