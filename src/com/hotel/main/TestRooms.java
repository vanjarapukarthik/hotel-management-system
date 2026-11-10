package com.hotel.main;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

public class TestRooms {
    public static void main(String[] args) {

        RoomDAO dao = new RoomDAO();

        System.out.println("--- Available rooms ---");
        for (Room r : dao.getAvailableRooms()) {
            System.out.println(r);
        }

        System.out.println("--- Room 1 ---");
        System.out.println(dao.getRoomById(1));
    }
}