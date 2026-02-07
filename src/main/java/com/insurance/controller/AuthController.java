package com.insurance.controller;

import com.insurance.dto.CustomerRequestDTO;
import com.insurance.dto.LoginRequest;
import com.insurance.dto.LoginResponseDTO;
import com.insurance.entity.Customer;
import com.insurance.service.AuthService;
import com.insurance.service.CustomerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final CustomerService customerService;

    public AuthController(AuthService authService,
                          CustomerService customerService) {
        this.authService = authService;
        this.customerService = customerService;
    }

    // ✅ REGISTER CUSTOMER (WORKING ALREADY)
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody CustomerRequestDTO dto) {
        Customer saved = customerService.registerCustomer(dto);
        return ResponseEntity.ok(saved);
    }

    // ✅ FIXED LOGIN (THIS WAS CAUSING 400)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest request) {

        String role = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        if ("INVALID".equals(role)) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponseDTO("INVALID", "Invalid credentials"));
        }

        return ResponseEntity.ok(
                new LoginResponseDTO(role, "Login successful")
        );
    }
}
