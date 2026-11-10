package com.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.BookingDetail;
import com.hotel.util.DBConnection;

public class BookingDAO {

    private static final String SELECT_JOIN =
        "SELECT b.booking_id, c.name, r.room_number, r.room_type, "
      + "b.check_in, b.check_out, b.total_days, b.total_amount, b.booking_status "
      + "FROM bookings b "
      + "JOIN customers c ON b.customer_id = c.customer_id "
      + "JOIN rooms r ON b.room_id = r.room_id ";

    public int bookRoom(int customerId, int roomId, LocalDate checkIn, LocalDate checkOut) {

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) {
            System.out.println("Check-out date must be after check-in date.");
            return -1;
        }

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                double price;
                String checkSql = "SELECT price_per_day, status FROM rooms WHERE room_id = ? FOR UPDATE";
                try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                    ps.setInt(1, roomId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Room not found.");
                            return -1;
                        }
                        if (!"AVAILABLE".equals(rs.getString("status"))) {
                            System.out.println("Room is not available.");
                            return -1;
                        }
                        price = rs.getDouble("price_per_day");
                    }
                }

                double total = price * days;
                int bookingId = -1;
                String insertSql = "INSERT INTO bookings (customer_id, room_id, check_in, check_out, total_days, total_amount) "
                                 + "VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, customerId);
                    ps.setInt(2, roomId);
                    ps.setDate(3, Date.valueOf(checkIn));
                    ps.setDate(4, Date.valueOf(checkOut));
                    ps.setInt(5, (int) days);
                    ps.setDouble(6, total);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            bookingId = keys.getInt(1);
                        }
                    }
                }

                String updateSql = "UPDATE rooms SET status = 'BOOKED' WHERE room_id = ?";
                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setInt(1, roomId);
                    ps.executeUpdate();
                }

                con.commit();
                return bookingId;

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Booking failed, rolled back: " + e.getMessage());
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return -1;
        }
    }

    public List<BookingDetail> getAllBookings() {
        List<BookingDetail> list = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY b.booking_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
        return list;
    }

    public BookingDetail getBookingById(int bookingId) {
        String sql = SELECT_JOIN + "WHERE b.booking_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching booking: " + e.getMessage());
        }
        return null;
    }

    public boolean cancelBooking(int bookingId) {
        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int roomId;
                String findSql = "SELECT room_id FROM bookings WHERE booking_id = ? AND booking_status = 'BOOKED'";
                try (PreparedStatement ps = con.prepareStatement(findSql)) {
                    ps.setInt(1, bookingId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            return false;
                        }
                        roomId = rs.getInt("room_id");
                    }
                }

                String cancelSql = "UPDATE bookings SET booking_status = 'CANCELLED' WHERE booking_id = ?";
                try (PreparedStatement ps = con.prepareStatement(cancelSql)) {
                    ps.setInt(1, bookingId);
                    ps.executeUpdate();
                }

                String roomSql = "UPDATE rooms SET status = 'AVAILABLE' WHERE room_id = ?";
                try (PreparedStatement ps = con.prepareStatement(roomSql)) {
                    ps.setInt(1, roomId);
                    ps.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Cancel failed, rolled back: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    private BookingDetail mapRow(ResultSet rs) throws SQLException {
        return new BookingDetail(
            rs.getInt("booking_id"),
            rs.getString("name"),
            rs.getInt("room_number"),
            rs.getString("room_type"),
            rs.getDate("check_in").toLocalDate(),
            rs.getDate("check_out").toLocalDate(),
            rs.getInt("total_days"),
            rs.getDouble("total_amount"),
            rs.getString("booking_status")
        );
    }
}