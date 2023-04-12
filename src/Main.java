import Exceptions.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws NoRoomsAvailableException {
        List<Room> roomList = new ArrayList<>();            //create lists to store the data
        List<Booking> bookingList = new ArrayList<>();

        Utility.createLists(roomList);      //create 10 rooms

        int ch = 0;
        System.out.println("****  Welcome  ****");      //initial welcome message
        System.out.println("Please Select A Option");
        System.out.println("1) Book Room");
        System.out.println("2) Update Booking");
        System.out.println("3) Get Available Rooms");
        System.out.println("4) Cancel Booking");
        System.out.println("5) View Booking Details");

        ch = sc.nextInt();

        do{

            switch (ch){
                case 1 :                                            //book room
                    try{
                        bookRoom(roomList,bookingList);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2 :                                            //update the booking
                    try {
                        updateBooking(roomList,bookingList);
                    }catch (Exception | MaximumCapacityReachedException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3 :                                            //to get the available rooms
                    getAvailableRooms(roomList,bookingList);
                    break;
                case 4 :                                            //to cancel a booking
                    try{
                        cancelBooking(roomList,bookingList);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5 :                                            //to view the details of the booking
                    try {
                        viewBookingDetails(bookingList);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0 :                                            //exit application
                    break;
                default:                                              //entered wrong option
                    System.out.println("Please choose the correct option");
                    System.out.println("--------------------------------");
            }

            System.out.println("Please Select A Option");           //display message
            System.out.println("1) Book Room");
            System.out.println("2) Update Booking");
            System.out.println("3) Get Available Rooms");
            System.out.println("4) Cancel Booking");
            System.out.println("5) View Booking Details");
            System.out.println("0) Exit");

            ch = sc.nextInt();
        }while(ch != 0);

        System.out.println("Thank You!");
        System.out.println("-------------------------");

    }


    private static void bookRoom(List<Room> roomList, List<Booking> bookingList) throws NoRoomsAvailableException, InvalidFormatException, InvalidChoiceException {

        if(bookingList.size() == 10) {              //check if all rooms are booked
            throw new NoRoomsAvailableException("Sorry! All rooms are booked.");
        }

        System.out.println("Please Enter Your Name:");
        String name = sc.next();
        System.out.println("Enter the number of guests:");

        int guestCount = -1;                        //getting guest count and name
        try {
            guestCount = Utility.validNumber(sc.next());
        }catch (Exception e){
            throw new InvalidFormatException(e.getMessage());
        }

        Room room;
        Booking booking = new Booking();
        if(guestCount >14){                     //guest count > 14 can only book luxury room

            room = Utility.getLuxuryRoom(roomList);

            if(room == null) throw new NoRoomsAvailableException("Sorry! Luxury rooms not available at the moment\n-------------------------------------------");
            System.out.println("Available Room Types");
            System.out.println("1) Luxury - A/C");

            Utility.confirmBooking(bookingList,name,guestCount,room,booking);

        }
        else if(guestCount > 5 ){               //guest count > 5 can book luxury and deluxe rooms

            System.out.println("Please select the room type");
            System.out.println("1) Deluxe - A/C");
            System.out.println("2) Luxury - A/C");

            int choice = sc.nextInt();
            if(choice != 1 && choice!= 2) throw new InvalidChoiceException("Please enter the correct choice");

            if(choice == 1) room = Utility.getDeluxeRoom(roomList);
            else room = Utility.getLuxuryRoom(roomList);

            Utility.confirmBooking(bookingList,name,guestCount,room,booking);
        }
        else {                                     // guest count less than 5 can book any type of room

            System.out.println("Please select the room type");
            System.out.println("1) Normal - Non-A/C");
            System.out.println("2) Deluxe - A/C");
            System.out.println("3) Luxury - A/C");

            int choice = sc.nextInt();
            if(choice != 1 && choice != 2 && choice !=3) throw new InvalidChoiceException("Please enter the correct choice");

            if(choice == 1) room = Utility.getNormalRoom(roomList);
            else if(choice == 2) room = Utility.getDeluxeRoom(roomList);
            else room = Utility.getLuxuryRoom(roomList);

            Utility.confirmBooking(bookingList,name,guestCount,room,booking);
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Hi "+name+" your room has been booked successfully");
        System.out.println("Your Booking Id is "+booking.getId()+" your room number is "+room.getId());
        System.out.println("----------------------------------------------------------");

    }


    private static void updateBooking(List<Room> roomList, List<Booking> bookingList) throws BookingNotFoundException, InvalidFormatException, InvalidChoiceException, NoRoomsAvailableException, SameRoomSelectedException, MaximumCapacityReachedException {
        System.out.println("Please Enter Your Booking Id");

        int bookingID = -1;
        try{
            bookingID = Utility.validNumber(sc.next());
        }catch (Exception e){
            throw new InvalidFormatException(e.getMessage());
        }

        Booking booking = null;
        Room room = null;                       //check if the booking id is valid
        for(Booking booking1 : bookingList){
            if(booking1.getId() == bookingID){
                booking = booking1;
                room = booking.getRoom();
                break;
            }
        }

        if(booking == null) throw new BookingNotFoundException("Sorry! The booking Id is not found. Please check your Booking ID\n----------------------------------------------------");

        System.out.println("Select An Option:");
        System.out.println("1) Update Name");           //display menu
        System.out.println("2) Update Guest Count");
        System.out.println("3) Change Room Type");

        int option = sc.nextInt();

        if(option !=1 && option != 2 && option !=3) throw new InvalidChoiceException("Please select correct option\n----------------------------");

        if(option ==1 ){

            System.out.println("Enter the new name: ");
            String newName = sc.next();
            booking.setName(newName);                         //update the name of guest
            System.out.println("Name changed successfully");
            System.out.println("--------------------------");

        } else if (option == 2) {

            System.out.println("Enter the guest count");
            int guestCount = -1;
            try {
                guestCount = Utility.validNumber(sc.next());
            }catch (Exception e){
                throw new InvalidFormatException(e.getMessage());       //get the guest count
            }

            Room newRoom = null;
            if(guestCount > 14){                        ///same as the booking function

                newRoom = Utility.getLuxuryRoom(roomList);
                if (newRoom == null ) throw new NoRoomsAvailableException("Sorry! All rooms with capacity greater than 14 is filled\n--------------------------------------------------");

                room.setBooked(false);
                newRoom.setBooked(true);
                booking.setGuestCount(guestCount);
                booking.setRoom(newRoom);

            } else if (guestCount > 5) {

                System.out.println("Select the room type");
                System.out.println("1) Deluxe - A/C");
                System.out.println("2) Luxury - A/C");

                int choice = -1;

                try {
                    choice = Utility.validNumber(sc.next());
                }catch (Exception e){
                    throw new InvalidFormatException(e.getMessage());
                }
                if(choice != 1 && choice!= 2) throw new InvalidChoiceException("Please enter the correct choice\n-------------------------------");

                if(choice == 1) newRoom = Utility.getDeluxeRoom(roomList);
                else newRoom = Utility.getLuxuryRoom(roomList);

                if(newRoom == null) throw new NoRoomsAvailableException("Sorry! All rooms are booked\n---------------------------------------");
                room.setBooked(false);
                newRoom.setBooked(true);
                booking.setGuestCount(guestCount);
                booking.setRoom(newRoom);

            }else {

                System.out.println("Please select the room type");
                System.out.println("1) Normal - Non-A/C");
                System.out.println("2) Deluxe - A/C");
                System.out.println("3) Luxury - A/C");

                int choice = -1;

                try {
                    choice = Utility.validNumber(sc.next());
                }catch (Exception e){
                    throw new InvalidFormatException(e.getMessage());
                }

                if(choice != 1 && choice != 2 && choice !=3) throw new InvalidChoiceException("Please enter the correct choice\n------------------------------------");

                if(choice == 1) newRoom = Utility.getNormalRoom(roomList);
                else if(choice == 2) newRoom = Utility.getDeluxeRoom(roomList);
                else newRoom = Utility.getLuxuryRoom(roomList);

                if(newRoom == null) throw new NoRoomsAvailableException("Sorry! All rooms are booked\n--------------------------------");
                room.setBooked(false);
                newRoom.setBooked(true);
                booking.setGuestCount(guestCount);
                booking.setRoom(newRoom);

            }
            System.out.println("Your guest count has been updated successfully");
            System.out.println("Your room has been changed ");
            System.out.println("Your new room number is "+newRoom.getId());
            System.out.println("------------------------------------------------------");
        }else{

            System.out.println("Please select the room type");
            System.out.println("1) Normal - Non-A/C");
            System.out.println("2) Deluxe - A/C");
            System.out.println("3) Luxury - A/C");

            int choice = -1;

            try {
                choice = Utility.validNumber(sc.next());
            }catch (Exception e){
                throw new InvalidFormatException(e.getMessage());
            }

            if(choice != 1 && choice != 2 && choice !=3) throw new InvalidChoiceException("Please enter the correct choice\n------------------------------------");

            if(Utility.sameRoomSelected(choice,room.getRoomType())) throw new SameRoomSelectedException("Please select other room types\n----------------------------------");

            Room newRoom = null;
            if(choice == 1) newRoom = Utility.getNormalRoom(roomList);
            else if(choice == 2) newRoom = Utility.getDeluxeRoom(roomList);
            else newRoom = Utility.getLuxuryRoom(roomList);     //assign the room with required room type

            if(newRoom == null) throw new NoRoomsAvailableException("Sorry! ALl rooms are booked.\n---------------------------------");
            if(booking.getGuestCount() > newRoom.getCapacity()) throw new MaximumCapacityReachedException("Sorry! The room is not available for "+booking.getGuestCount()+" guests\n----------------------------------------------------------");

            booking.setRoom(newRoom);
            room.setBooked(false);

            System.out.println("Your room has been changed ");
            System.out.println("Your new room number is "+newRoom.getId());
            System.out.println("------------------------------------------------------");

        }
    }


    private static void getAvailableRooms(List<Room> roomList,List<Booking> bookingList){
        if(bookingList.size() == 10){
            System.out.println("Sorry! All rooms are booked");         //check if all rooms are booked
            System.out.println("---------------------------");
            return;
        }

        System.out.println("Available Rooms");
        System.out.println("--------------------------------------");
        System.out.println("Room No. | Guest Capacity | Room Type");
        for(Room room : roomList){                      //prints the details of all unbooked  rooms
            if(!room.isBooked() ){
                if(room.getCapacity() == 5)
                    System.out.println(room.getId()+"      |       "+room.getCapacity()+"        |"+room.getRoomType());
                else
                    System.out.println(room.getId()+"      |       "+room.getCapacity()+"       |"+room.getRoomType());
            }

        }

        System.out.println("------------------------------------------");
    }

    private static void cancelBooking(List<Room> roomList, List<Booking> bookingList) throws BookingNotFoundException {
        System.out.println("Please Enter Your Booking Id");
        int bookingID = -1;

        try {
            bookingID = Utility.validNumber(sc.next());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        Room room = null;
        Booking booking = null;
        for(Booking booking1 : bookingList){        //gets the room booking from the list
            if(booking1.getId() == bookingID){
                booking = booking1;
                room = booking.getRoom();
                break;
            }
        }

        if(booking == null) throw new BookingNotFoundException("Sorry! The booking Id is not found. Please check your Booking ID\n----------------------------------------------------");

        int roomIndex = 0;

        for(Room room1 :roomList){
            if(room1 == room){
                break;
            }
            roomIndex++;
        }

        room.setBooked(false);              //makes the room as unbooked
        roomList.set(roomIndex,room);
        bookingList.remove(booking);        //removes the booking from the list

        System.out.println("Hi "+booking.getName()+" Your booking has been canceled successfully");
        System.out.println("----------------------------------------------------------------------");

    }

    private static void viewBookingDetails(List<Booking> bookingList) throws InvalidFormatException, BookingNotFoundException {
        System.out.println("Please enter your booking id");

        int bookingID = -1;
        try {
            bookingID = Utility.validNumber(sc.next());
        }catch (Exception e){
            throw new InvalidFormatException(e.getMessage());
        }

        Booking booking = null;
        for(Booking booking1 : bookingList){        //gets the booking details
            if(booking1.getId() == bookingID){
                booking = booking1;
                break;
            }
        }

        if(booking == null) throw new BookingNotFoundException("Sorry! The booking Id is not found. Please check your Booking ID\n-----------------------------------------------");


        Room room = booking.getRoom();

        System.out.println("Hi "+booking.getName());        //prints the details related to the booking
        System.out.println("Your Booking Id is "+booking.getId());
        System.out.println("Guest Count : "+booking.getGuestCount());
        System.out.println("Room Type : "+room.getRoomType());
        System.out.println("Room Number : "+room.getId());
        System.out.println("Time of Booking : "+booking.getBookingTime().getHour()+":"+booking.getBookingTime().getMinute()+":"+booking.getBookingTime().getSecond());
        System.out.println("------------------------------------------");

    }
}