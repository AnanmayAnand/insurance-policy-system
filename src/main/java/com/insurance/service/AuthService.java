package com.insurance.service;

import com.insurance.entity.Admin;
import com.insurance.entity.Customer;
import com.insurance.repository.AdminRepository;
import com.insurance.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public AuthService(AdminRepository adminRepository,
                       CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * @return ADMIN | CUSTOMER | INVALID
     */
    public String login(String username, String password) {

        // ✅ ADMIN FIRST (NON-NEGOTIABLE)
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return "ADMIN";
        }

        // ✅ CUSTOMER NEXT
        return customerRepository.findByUsername(username)
                .filter(c -> c.getPassword().equals(password))
                .map(c -> "CUSTOMER")
                .orElse("INVALID");
    }
}
