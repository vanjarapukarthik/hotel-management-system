package com.hotel.model;

public class Room {

    private int roomId;
    private int roomNumber;
    private String roomType;
    private double pricePerDay;
    private String status;

    public Room() {
    }

    public Room(int roomId, int roomNumber, String roomType, double pricePerDay, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Room ID: " + roomId + " | Room No: " + roomNumber + " | Type: " + roomType
                + " | Price/Day: Rs." + pricePerDay + " | Status: " + status;
    }
}