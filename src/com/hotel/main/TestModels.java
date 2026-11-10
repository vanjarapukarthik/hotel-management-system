package com.hotel.main;

import java.time.LocalDate;
import com.hotel.model.Booking;
import com.hotel.model.Customer;
import com.hotel.model.Room;

public class TestModels {
    public static void main(String[] args) {

        Customer c = new Customer(1, "Ravi", "9876543210", "ravi@gmail.com", "Hyderabad");
        Room r = new Room(1, 101, "Single", 1500.0, "AVAILABLE");
        Booking b = new Booking(1, 1, 1, LocalDate.of(2026, 11, 10),
                LocalDate.of(2026, 11, 12), 2, 3000.0, "BOOKED");

        System.out.println(c);
        System.out.println(r);
        System.out.println(b);
    }
}
