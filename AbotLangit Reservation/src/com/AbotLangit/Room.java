package com.AbotLangit;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the Room class
 * Which basically the managing the formats of input value and variables inputed in Reservation Form then transfer it to the Availability Frame
 * Represents a room in a hotel or accommodation.
 * This class encapsulates information about room type, price, reservation details, and availability.
 */
public class Room {
    private String type; // Type of the room (e.g., single, double)
    private double price; // Price per night of the room
    private String customerName; // Name of the customer currently occupying the room
    private String contactNumber; // Contact number of the customer
    private Date checkInDate; // Date when the customer checks into the room
    private Date checkOutDate; // Date when the customer checks out of the room
    private boolean isAvailable; // Availability status of the room
    private int number; // Room number
    private double totalPrice; // Total price of the reservation
    private int guestCount; // Number of guests staying in the room

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * Constructor to initialize a Room object with type, price, and room number.
     * @param type The type of the room (e.g., single, double).
     * @param price The price per night of the room.
     * @param number The room number.
     */
    public Room(String type, double price, int number) {
        this.type = type;
        this.price = price;
        this.number = number;
        this.isAvailable = true; // Initialize room as available
    }

    // Getters and setters for all fields

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getNumber() {
        return number;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Calculate the total price of the reservation based on duration and number of guests.
     * @param duration The duration of stay in days.
     * @param guests The number of guests staying in the room.
     * @return The total price of the reservation.
     */
    public double calculateTotalPrice(int duration, int guests) {
        return price * duration * guests;
    }

    /**
     * Returns a string representation of the Room object.
     * @return A string representation containing all room details.
     */
    @Override
    public String toString() {
        return "Room{" +
                "type='" + type + '\'' +
                ", price=" + price +
                ", customerName='" + customerName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", isAvailable=" + isAvailable +
                ", number=" + number +
                ", totalPrice=" + totalPrice +
                '}';
    }

    /**
     * Get formatted check-in date.
     * @return Formatted check-in date as string.
     */
    public String getCheckInDateFormatted() {
        return checkInDate != null ? dateFormat.format(checkInDate) : "";
    }

    /**
     * Get formatted check-out date.
     * @return Formatted check-out date as string.
     */
    public String getCheckOutDateFormatted() {
        return checkOutDate != null ? dateFormat.format(checkOutDate) : "";
    }
}
