package com.hotel.main;

import java.util.List;
import com.hotel.dao.CustomerDAO;
import com.hotel.model.Customer;

public class TestUpdateDelete {
    public static void main(String[] args) {

        CustomerDAO dao = new CustomerDAO();

        // 1. Customer 1 ని update చేయడం
        Customer c = dao.getCustomerById(1);
        c.setAddress("Vijayawada");
        System.out.println("Update: " + dao.updateCustomer(c));
        System.out.println(dao.getCustomerById(1));

        // 2. తాత్కాలిక customer ని add చేసి delete చేయడం
        Customer temp = new Customer();
        temp.setName("Temp");
        temp.setPhone("1111111111");
        temp.setEmail("temp@mail.com");
        temp.setAddress("Test");
        dao.addCustomer(temp);

        List<Customer> all = dao.getAllCustomers();
        int lastId = all.get(all.size() - 1).getCustomerId();
        System.out.println("Delete id " + lastId + ": " + dao.deleteCustomer(lastId));

        // 3. చివరగా అందరినీ చూపించడం
        for (Customer x : dao.getAllCustomers()) {
            System.out.println(x);
        }
    }
}