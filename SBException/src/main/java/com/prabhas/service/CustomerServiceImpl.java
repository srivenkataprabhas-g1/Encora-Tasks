// Implementing the service class
package com.prabhas.service;

import com.prabhas.exception.CustomerAlreadyExistsException;
import com.prabhas.exception.NoSuchCustomerExistsException;
import com.prabhas.model.Customer;
import com.prabhas.repository.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRespository;

    // Method to get customer by Id. Throws
    // NoSuchElementException for invalid Id
    public Customer getCustomer(Long id) {
        return customerRespository.findById(id).orElseThrow(
            () -> new NoSuchElementException("NO CUSTOMER PRESENT WITH ID = " + id));
    }

  
    // Simplifying the addCustomer and updateCustomer
    // methods with Optional for better readability.
    
    public String addCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRespository.findById(customer.getId());
        if (!existingCustomer.isPresent()) {
            customerRespository.save(customer);
            return "Customer added successfully";
        } else {
            throw new CustomerAlreadyExistsException("Customer already exists!!");
        }
    }

    public String updateCustomer(Long id,Customer customer) {
        Optional<Customer> existingCustomer = customerRespository.findById(id);
        if (!existingCustomer.isPresent()) {
            throw new NoSuchCustomerExistsException("No Such Customer exists!!");
        } else {
            existingCustomer.get().setName(customer.getName());
            existingCustomer.get().setAddress(customer.getAddress());
            customerRespository.save(existingCustomer.get());
            return "Record updated Successfully";
        }
    }



	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}
}