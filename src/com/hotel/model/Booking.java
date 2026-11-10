package com.hotel.model;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private int customerId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalDays;
    private double totalAmount;
    private String bookingStatus;

    public Booking() {
    }

    public Booking(int bookingId, int customerId, int roomId, LocalDate checkIn,
                   LocalDate checkOut, int totalDays, double totalAmount, String bookingStatus) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalDays = totalDays;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + " | Customer ID: " + customerId
                + " | Room ID: " + roomId + " | " + checkIn + " to " + checkOut
                + " | Days: " + totalDays + " | Amount: Rs." + totalAmount
                + " | Status: " + bookingStatus;
    }
}