package com.insurance.dto;

import java.time.LocalDate;

public class CustomerResponseDTO {

    private Long customerId;
    private String username;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate dob;

    public CustomerResponseDTO(Long customerId, String username, String name,
                               String phone, String email, String address, LocalDate dob) {
        this.customerId = customerId;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDob() {
        return dob;
    }
}
