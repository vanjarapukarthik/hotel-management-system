package com.hotel.service;

import com.hotel.model.BookingDetail;

public class BillService {

    private static final double GST_RATE = 0.12;   // 12% GST

    // Feature 11: Room rent లెక్కించడం
    public double calculateRent(double pricePerDay, int days) {
        return pricePerDay * days;
    }

    // GST మొత్తం
    public double calculateGst(double rent) {
        return rent * GST_RATE;
    }

    // Feature 12: Bill print చేయడం
    public void printBill(BookingDetail b) {
    	if ("CANCELLED".equalsIgnoreCase(b.getBookingStatus())) {
    	    System.out.println("Cannot generate bill. This booking is cancelled.");
    	    return;
    	}
        double rent = b.getTotalAmount();
        double gst = calculateGst(rent);
        double grandTotal = rent + gst;

        System.out.println();
        System.out.println("=============== HOTEL BILL ===============");
        System.out.println("Booking ID   : " + b.getBookingId());
        System.out.println("Customer     : " + b.getCustomerName());
        System.out.println("Room         : " + b.getRoomNumber() + " (" + b.getRoomType() + ")");
        System.out.println("Check-in     : " + b.getCheckIn());
        System.out.println("Check-out    : " + b.getCheckOut());
        System.out.println("Days         : " + b.getTotalDays());
        System.out.println("Status       : " + b.getBookingStatus());
        System.out.println("------------------------------------------");
        System.out.printf("Room Rent    : Rs. %.2f%n", rent);
        System.out.printf("GST (12%%)    : Rs. %.2f%n", gst);
        System.out.printf("TOTAL        : Rs. %.2f%n", grandTotal);
        System.out.println("==========================================");
    }
}