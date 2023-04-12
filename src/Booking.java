import java.time.LocalTime;

public class Booking {
    private int id;
    private String name;
    private LocalTime bookingTime;
    private Room room;
    private int guestCount;

    public Booking(int id, String name, LocalTime bookingTime, Room room, int guestCount) {
        this.id = id;
        this.name = name;
        this.bookingTime = bookingTime;
        this.room = room;
        this.guestCount = guestCount;
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }
}
