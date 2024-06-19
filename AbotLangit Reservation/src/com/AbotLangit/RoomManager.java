package com.AbotLangit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the RoomManager class
 * Basically the one that Manages a collection of rooms and provides operations to reserve rooms, check availability,
 * and retrieve information about rooms which are used in Reservation and availability.
 */
public class RoomManager {
    private List<Room> rooms; // List to store all rooms managed by this RoomManager

    /**
     * Constructor to initialize the RoomManager with some predefined rooms.
     * Initializes single, double, and VIP rooms with their respective types, prices, and numbers.
     */
    public RoomManager() {
        rooms = new ArrayList<>();
        // Initialize rooms with some data
        // Room Type/Price/Room number format
        // For Single rooms
        rooms.add(new Room("Single", 1525, 1));
        rooms.add(new Room("Single", 1525, 3));
        rooms.add(new Room("Single", 1525, 5));
        // For Double rooms
        rooms.add(new Room("Double", 2500, 2));
        rooms.add(new Room("Double", 2500, 4));
        rooms.add(new Room("Double", 2500, 6));
        // For VIP rooms
        rooms.add(new Room("VIP", 4500, 8));
        rooms.add(new Room("VIP", 4500, 9));
        rooms.add(new Room("VIP", 4500, 10));
    }

    /**
     * Retrieves the list of all rooms managed by this RoomManager.
     * @return List of Room objects.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Retrieves a list of available room types (e.g., "Single", "Double", "VIP").
     * @return List of available room types as strings.
     */
    public List<String> getAvailableRoomTypes() {
        List<String> availableRoomTypes = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRoomTypes.add(room.getType());
            }
        }
        return availableRoomTypes;
    }

    /**
     * Reserves the specified room for a customer with the provided details.
     * Sets customer name, contact number, check-in and check-out dates, guest count,
     * and marks the room as unavailable.
     * @param room The Room object to reserve.
     * @param customerName The name of the customer reserving the room.
     * @param contactNumber The contact number of the customer.
     * @param checkInDate The check-in date.
     * @param checkOutDate The check-out date.
     * @param guestCount The number of guests staying in the room.
     */
    public void reserveRoom(Room room, String customerName, String contactNumber, Date checkInDate, Date checkOutDate, int guestCount) {
        room.setCustomerName(customerName);
        room.setContactNumber(contactNumber);
        room.setCheckInDate(checkInDate);
        room.setCheckOutDate(checkOutDate);
        room.setGuestCount(guestCount);
        room.setAvailable(false);

        // Calculate and set total price
        int duration = calculateDuration(checkInDate, checkOutDate);
        double totalPrice = room.getPrice() * duration * guestCount;
        room.setTotalPrice(totalPrice);
    }

    /**
     * Retrieves a list of all available rooms.
     * @return List of Room objects that are currently available.
     */
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * Calculates the duration in days between the check-in and check-out dates.
     * @param checkInDate The check-in date.
     * @param checkOutDate The check-out date.
     * @return The duration in days as an integer.
     */
    private int calculateDuration(Date checkInDate, Date checkOutDate) {
        if (checkInDate != null && checkOutDate != null) {
            long differenceInMillis = checkOutDate.getTime() - checkInDate.getTime();
            return (int) (differenceInMillis / (1000 * 60 * 60 * 24)); // Calculate duration in days
        } else {
            return 0; // Handle case where dates are not set properly
        }
    }
}
