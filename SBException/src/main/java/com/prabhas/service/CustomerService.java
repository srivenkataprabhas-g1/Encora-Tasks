// Creating service interface
package com.prabhas.service;
import java.util.List;

import com.prabhas.model.Customer;

public interface CustomerService {

    // Method to get customer by its Id
    Customer getCustomer(Long id);

    // Method to add a new Customer
    // into the database
    String addCustomer(Customer customer);

    // Method to update details of a Customer
    String updateCustomer(Long id, Customer customer);

	List<Customer> getAllCustomers();
}