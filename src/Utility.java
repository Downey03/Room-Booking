import Exceptions.InvalidFormatException;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static int validNumber(String id) throws InvalidFormatException {

        Pattern pattern = Pattern.compile("\\D");
        Matcher matcher = pattern.matcher(id);
        if(matcher.find()){
            throw new InvalidFormatException("Only numbers are allowed\n--------------------------");
        }
        return Integer.parseInt(id);
    }
    public static boolean checkAvailableNormalRooms(List<Room> roomList) {
        for(Room room : roomList){
            if(room.getRoomType() == Type.NORMAL && !room.isBooked())   return true;
        }
        return false;
    }

    public static Room getNormalRoom(List<Room> roomList) {
        for(Room room :roomList){
            if(room.getRoomType() == Type.NORMAL && !room.isBooked()) return room;
        }

        return  null;
    }
    public static Room getDeluxeRoom(List<Room> roomList) {
        for(Room room :roomList){
            if(room.getRoomType() == Type.DELUXE && !room.isBooked()) return room;
        }

        return  null;
    }
    public static Room getLuxuryRoom(List<Room> roomList) {
        for(Room room :roomList){
            if(room.getRoomType() == Type.LUXURY && !room.isBooked()) return room;
        }

        return  null;
    }

    public static void confirmBooking(List<Booking> bookingList, String name, int guestCount, Room room,Booking newBooking) {

        int bookingId = (int)(Math.random()*100+50);
        newBooking.setId(bookingId);
        newBooking.setBookingTime(LocalTime.now());
        newBooking.setName(name);
        newBooking.setGuestCount(guestCount);
        room.setBooked(true);
        newBooking.setRoom(room);

        bookingList.add(newBooking);

    }

    public static void createLists(List<Room> roomList){
        for(int i=0;i<10;i++){

            int id = 100+i;
            int capacity = (int)(Math.random()*3+1) * 5 ;
            Type type;
            if(capacity == 5) type = Type.NORMAL;
            else if (capacity == 10) type = Type.DELUXE;
            else type = Type.LUXURY;
            boolean ac = false;
            if(type != Type.NORMAL) ac = true;

            Room newRoom = new Room();
            newRoom.setRoomType(type);
            newRoom.setCapacity(capacity);
            newRoom.setId(id);
            newRoom.setAcAvailable(ac);
            newRoom.setBooked(false);
            roomList.add(newRoom);

        }
    }

    public static boolean sameRoomSelected(int choice, Type roomType) {
        if(choice == 1 && roomType == Type.NORMAL) return true;
        if(choice == 2 && roomType == Type.DELUXE) return true;
        if(choice == 3 && roomType == Type.LUXURY) return true;
        return false;
    }
}
