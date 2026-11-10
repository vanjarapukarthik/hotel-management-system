package com.hotel.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hotel_db";

    private static final String USER = "root";

    private static final String PASSWORD = "Hotel@123";

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
