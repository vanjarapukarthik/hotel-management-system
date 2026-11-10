package com.hotel.model;

import java.time.LocalDate;

public class BookingDetail {

    private int bookingId;
    private String customerName;
    private int roomNumber;
    private String roomType;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalDays;
    private double totalAmount;
    private String bookingStatus;

    public BookingDetail(int bookingId, String customerName, int roomNumber, String roomType,
                         LocalDate checkIn, LocalDate checkOut, int totalDays,
                         double totalAmount, String bookingStatus) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalDays = totalDays;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public int getBookingId() { return bookingId; }
    public String getCustomerName() { return customerName; }
    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public int getTotalDays() { return totalDays; }
    public double getTotalAmount() { return totalAmount; }
    public String getBookingStatus() { return bookingStatus; }

    @Override
    public String toString() {
        return "Booking #" + bookingId + " | " + customerName + " | Room " + roomNumber
                + " (" + roomType + ") | " + checkIn + " to " + checkOut
                + " | " + totalDays + " days | Rs." + totalAmount + " | " + bookingStatus;
    }
}