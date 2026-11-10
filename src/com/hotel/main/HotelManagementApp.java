package com.hotel.main;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.CustomerDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.BookingDetail;
import com.hotel.model.Customer;
import com.hotel.model.Room;
import com.hotel.service.BillService;

public class HotelManagementApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final RoomDAO roomDAO = new RoomDAO();
    private static final BookingDAO bookingDAO = new BookingDAO();
    private static final BillService billService = new BillService();

    public static void main(String[] args) {

        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:  addCustomer();        break;
                case 2:  viewAllCustomers();   break;
                case 3:  searchCustomer();     break;
                case 4:  updateCustomer();     break;
                case 5:  deleteCustomer();     break;
                case 6:  viewAvailableRooms(); break;
                case 7:  bookRoom();           break;
                case 8:  viewAllBookings();    break;
                case 9:  searchBooking();      break;
                case 10: cancelBooking();      break;
                case 11: calculateRent();      break;
                case 12: generateBill();       break;
                case 0:  System.out.println("Thank you! Goodbye."); break;
                default: System.out.println("Invalid choice. Please enter 0-12.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========== HOTEL MANAGEMENT SYSTEM ==========");
        System.out.println(" --- Customer ---");
        System.out.println("  1. Add Customer");
        System.out.println("  2. View All Customers");
        System.out.println("  3. Search Customer by ID");
        System.out.println("  4. Update Customer Details");
        System.out.println("  5. Delete Customer");
        System.out.println(" --- Rooms ---");
        System.out.println("  6. View Available Rooms");
        System.out.println("  7. Book Room");
        System.out.println(" --- Bookings ---");
        System.out.println("  8. View All Bookings");
        System.out.println("  9. Search Booking by ID");
        System.out.println(" 10. Cancel Booking");
        System.out.println(" --- Billing ---");
        System.out.println(" 11. Calculate Room Rent");
        System.out.println(" 12. GST Calculation and Bill Generation");
        System.out.println("  0. Exit");
        System.out.println("=============================================");
    }

    // ---------- Customer ----------

    private static void addCustomer() {
        String name = readNonEmpty("Name: ");
        String phone = readPhone("Phone (10 digits): ");
        String email = readLine("Email: ");
        String address = readLine("Address: ");

        Customer c = new Customer();
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);

        if (customerDAO.addCustomer(c)) {
            System.out.println("Customer added successfully.");
        } else {
            System.out.println("Failed to add customer.");
        }
    }

    private static void viewAllCustomers() {
        List<Customer> list = customerDAO.getAllCustomers();
        if (list.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        for (Customer c : list) {
            System.out.println(c);
        }
    }

    private static void searchCustomer() {
        int id = readInt("Customer ID: ");
        Customer c = customerDAO.getCustomerById(id);
        System.out.println(c != null ? c : "Customer not found.");
    }

    private static void updateCustomer() {
        int id = readInt("Customer ID to update: ");
        Customer c = customerDAO.getCustomerById(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("Current: " + c);
        System.out.println("(Press Enter to keep the current value)");

        String name = readLine("New name: ");
        String phone = readLine("New phone: ");
        String email = readLine("New email: ");
        String address = readLine("New address: ");

        if (!name.isEmpty()) c.setName(name);
        if (!phone.isEmpty()) {
            if (phone.matches("\\d{10}")) {
                c.setPhone(phone);
            } else {
                System.out.println("Invalid phone, keeping the old one.");
            }
        }
        if (!email.isEmpty()) c.setEmail(email);
        if (!address.isEmpty()) c.setAddress(address);

        System.out.println(customerDAO.updateCustomer(c) ? "Customer updated." : "Update failed.");
    }

    private static void deleteCustomer() {
        int id = readInt("Customer ID to delete: ");
        if (customerDAO.getCustomerById(id) == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println(customerDAO.deleteCustomer(id)
                ? "Customer deleted."
                : "Could not delete (customer may have bookings).");
    }

    // ---------- Rooms ----------

    private static void viewAvailableRooms() {
        List<Room> rooms = roomDAO.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    private static void bookRoom() {
        int customerId = readInt("Customer ID: ");
        if (customerDAO.getCustomerById(customerId) == null) {
            System.out.println("Customer not found. Add the customer first.");
            return;
        }

        viewAvailableRooms();
        int roomId = readInt("Room ID to book: ");
        LocalDate checkIn = readDate("Check-in date (yyyy-mm-dd): ");
        LocalDate checkOut = readDate("Check-out date (yyyy-mm-dd): ");

        int bookingId = bookingDAO.bookRoom(customerId, roomId, checkIn, checkOut);
        if (bookingId > 0) {
            System.out.println("Room booked successfully. Booking ID = " + bookingId);
        } else {
            System.out.println("Booking failed.");
        }
    }

    // ---------- Bookings ----------

    private static void viewAllBookings() {
        List<BookingDetail> list = bookingDAO.getAllBookings();
        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (BookingDetail b : list) {
            System.out.println(b);
        }
    }

    private static void searchBooking() {
        int id = readInt("Booking ID: ");
        BookingDetail b = bookingDAO.getBookingById(id);
        System.out.println(b != null ? b : "Booking not found.");
    }

    private static void cancelBooking() {
        int id = readInt("Booking ID to cancel: ");
        System.out.println(bookingDAO.cancelBooking(id)
                ? "Booking cancelled. Room is available again."
                : "Booking not found or already cancelled.");
    }

    // ---------- Billing ----------

    private static void calculateRent() {
        int roomId = readInt("Room ID: ");
        Room room = roomDAO.getRoomById(roomId);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        int days = readInt("Number of days: ");
        if (days <= 0) {
            System.out.println("Days must be more than 0.");
            return;
        }
        double rent = billService.calculateRent(room.getPricePerDay(), days);
        System.out.println("Room " + room.getRoomNumber() + " rent for " + days + " days = Rs. " + rent);
    }

    private static void generateBill() {
        int id = readInt("Booking ID: ");
        BookingDetail b = bookingDAO.getBookingById(id);
        if (b == null) {
            System.out.println("Booking not found.");
            return;
        }
        billService.printBill(b);
    }

    // ---------- Input helpers (validation) ----------

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            String s = readLine(prompt);
            if (!s.isEmpty()) return s;
            System.out.println("This field cannot be empty.");
        }
    }

    private static String readPhone(String prompt) {
        while (true) {
            String s = readLine(prompt);
            if (s.matches("\\d{10}")) return s;
            System.out.println("Phone must be exactly 10 digits.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            try {
                return LocalDate.parse(readLine(prompt));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use format yyyy-mm-dd (example: 2026-12-01).");
            }
        }
    }
}