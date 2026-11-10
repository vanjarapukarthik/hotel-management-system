package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Room;
import com.hotel.util.DBConnection;

public class RoomDAO {

    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = 'AVAILABLE' ORDER BY room_number";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching rooms: " + e.getMessage());
        }
        return list;
    }

    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching room: " + e.getMessage());
        }
        return null;
    }

    public boolean updateRoomStatus(int roomId, String status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating room status: " + e.getMessage());
            return false;
        }
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        Room r = new Room();
        r.setRoomId(rs.getInt("room_id"));
        r.setRoomNumber(rs.getInt("room_number"));
        r.setRoomType(rs.getString("room_type"));
        r.setPricePerDay(rs.getDouble("price_per_day"));
        r.setStatus(rs.getString("status"));
        return r;
    }
}