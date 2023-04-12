public class Room {
    private int id;
    private int capacity;
    private Type roomType;
    private boolean acAvailable;
    private boolean booked;

    public Room(int id, int capacity, Type roomType, boolean acAvailable,boolean booked) {
        this.id = id;
        this.booked = booked;
        this.capacity = capacity;
        this.roomType = roomType;
        this.acAvailable = acAvailable;
    }

    public Room() {
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Type getRoomType() {
        return roomType;
    }

    public void setRoomType(Type roomType) {
        this.roomType = roomType;
    }

    public boolean isAcAvailable() {
        return acAvailable;
    }

    public void setAcAvailable(boolean acAvailable) {
        this.acAvailable = acAvailable;
    }
}
