package com.hotel.main;

import java.time.LocalDate;

import com.hotel.dao.BookingDAO;
import com.hotel.model.BookingDetail;
import com.hotel.service.BillService;

public class TestBill {
    public static void main(String[] args) {

        BookingDAO dao = new BookingDAO();
        BillService billService = new BillService();

        // కొత్త booking చేసి, దాని bill చూపిద్దాం (Room 2, 4 రోజులు)
        int id = dao.bookRoom(1, 2, LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 5));
        System.out.println("Booking id: " + id);

        BookingDetail b = dao.getBookingById(id);
        if (b != null) {
            billService.printBill(b);
        } else {
            System.out.println("Booking not found.");
        }
    }
}