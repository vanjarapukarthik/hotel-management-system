package com.hotel.main;

import java.time.LocalDate;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.BookingDetail;

public class TestBooking {
    public static void main(String[] args) {

        BookingDAO bookingDAO = new BookingDAO();
        RoomDAO roomDAO = new RoomDAO();

        // 1. Customer 1 కి Room 1 ని Nov 10 నుంచి Nov 13 వరకు (3 రోజులు)
        int id = bookingDAO.bookRoom(1, 1, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 13));
        System.out.println("Booking id: " + id);

        // 2. అన్ని bookings చూపించడం
        System.out.println("--- All bookings ---");
        for (BookingDetail b : bookingDAO.getAllBookings()) {
            System.out.println(b);
        }

        // 3. Room 1 status
        System.out.println("--- Room 1 after booking ---");
        System.out.println(roomDAO.getRoomById(1));

        // 4. అదే room ని మళ్ళీ book చేయడానికి ప్రయత్నం (విఫలమవ్వాలి)
        int again = bookingDAO.bookRoom(2, 1, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        System.out.println("Second booking attempt: " + again);

        // 5. Cancel చేయడం
        System.out.println("Cancel: " + bookingDAO.cancelBooking(id));
        System.out.println("--- Room 1 after cancel ---");
        System.out.println(roomDAO.getRoomById(1));
    }
}