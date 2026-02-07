package com.insurance.dto;

public class LoginResponseDTO {

    private String role; // ADMIN / CUSTOMER
    private String message;

    public LoginResponseDTO(String role, String message) {
        this.role = role;
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }
}
