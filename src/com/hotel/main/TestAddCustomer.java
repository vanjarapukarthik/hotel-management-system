package com.hotel.main;

import com.hotel.dao.CustomerDAO;
import com.hotel.model.Customer;

public class TestAddCustomer {
    public static void main(String[] args) {

        Customer c = new Customer();
        c.setName("Ravi");
        c.setPhone("9876543210");
        c.setEmail("ravi@gmail.com");
        c.setAddress("Hyderabad");

        CustomerDAO dao = new CustomerDAO();
        boolean result = dao.addCustomer(c);

        if (result) {
            System.out.println("Customer added successfully!");
        } else {
            System.out.println("Failed to add customer.");
        }
    }
}