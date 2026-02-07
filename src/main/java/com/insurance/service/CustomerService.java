package com.insurance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insurance.dto.CustomerRequestDTO;
import com.insurance.entity.Customer;
import com.insurance.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ✅ REGISTER CUSTOMER
    public Customer registerCustomer(CustomerRequestDTO dto) {

        Customer customer = new Customer();
        customer.setUsername(dto.getUsername());
        customer.setPassword(dto.getPassword()); // plain for now
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setDob(dto.getDob());

        return customerRepository.save(customer);
    }

    // ✅ FIND BY USERNAME
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
