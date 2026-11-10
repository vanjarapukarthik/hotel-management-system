package com.hotel.main;

import java.util.List;
import com.hotel.dao.CustomerDAO;
import com.hotel.model.Customer;

public class TestViewCustomers {
    public static void main(String[] args) {

        CustomerDAO dao = new CustomerDAO();
        List<Customer> customers = dao.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer c : customers) {
                System.out.println(c);
            }
        }
    }
}