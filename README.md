Hotel Management System

A console-based Hotel Management System developed using Core Java, JDBC, and MySQL.

Technologies Used

- Core Java
- JDBC
- MySQL
- SQL
- Eclipse IDE
- Git and GitHub

Features

- Add, view, search, update, and delete customers.
- View available rooms.
- Search rooms by ID and check room status.
- Book rooms using database transactions.
- View all bookings with JOIN queries.
- Search and cancel bookings.
- Calculate room rent.
- Calculate GST.
- Generate customer bills.

Project Structure

HotelManagementSystem
└── src
    └── com.hotel
        ├── dao
        │   ├── CustomerDAO.java
        │   ├── RoomDAO.java
        │   └── BookingDAO.java
        ├── main
        │   └── HotelManagementApp.java
        ├── model
        │   ├── Customer.java
        │   ├── Room.java
        │   ├── Booking.java
        │   └── BookingDetail.java
        ├── service
        │   └── BillService.java
        └── util
            └── DBConnection.java

Database

- Database: "hotel_db"
- Tables: Customers, Rooms, Bookings

How to Run

1. Clone or download this repository.
2. Import the project into Eclipse.
3. Create the MySQL database and required tables.
4. Configure your MySQL username and password in "DBConnection.java".
5. Add the MySQL JDBC driver.
6. Run "HotelManagementApp.java" as a Java Application.

Concepts Implemented

- Object-Oriented Programming
- JDBC Connectivity
- PreparedStatement
- SQL JOIN
- Database Transactions
- DAO Pattern
- Layered Architecture

Author

Vanjarapu Karthik
