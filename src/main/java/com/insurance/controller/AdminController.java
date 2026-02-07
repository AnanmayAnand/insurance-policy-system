package com.insurance.controller;

import com.insurance.dto.AssignPolicyDTO;
import com.insurance.dto.PolicyRequestDTO;
import com.insurance.dto.PolicyResponseDTO;
import com.insurance.entity.Customer;
import com.insurance.service.AdminService;
import com.insurance.service.CustomerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final AdminService adminService;
    private final CustomerService customerService;

    public AdminController(
            AdminService adminService,
            CustomerService customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    // ✅ CREATE POLICY
    @PostMapping("/policies")
    public ResponseEntity<String> createPolicy(@RequestBody PolicyRequestDTO dto) {
        adminService.createPolicy(dto);
        return ResponseEntity.ok("Policy created successfully");
    }

    // ✅ VIEW POLICIES
    @GetMapping("/policies")
    public ResponseEntity<List<PolicyResponseDTO>> getAllPolicies() {
        return ResponseEntity.ok(adminService.getAllPolicies());
    }

    // 🔍 SEARCH POLICIES
    @GetMapping("/policies/search")
    public ResponseEntity<List<PolicyResponseDTO>> searchPolicies(
            @RequestParam String keyword) {
        return ResponseEntity.ok(adminService.searchPolicies(keyword));
    }

    // ❌ DELETE POLICY (SOFT DELETE)
    @DeleteMapping("/policies/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable Long id) {
        adminService.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted successfully");
    }

    // ✅ VIEW PENDING REQUESTS
    @GetMapping("/pending-requests")
    public ResponseEntity<?> getPendingRequests() {
        return ResponseEntity.ok(adminService.getPendingRequests());
    }

    // ✅ APPROVE / REJECT POLICY
    @PostMapping("/approve")
    public ResponseEntity<String> approvePolicy(@RequestBody AssignPolicyDTO dto) {
        adminService.approvePolicy(dto);
        return ResponseEntity.ok("Policy status updated");
    }

    // 👥 VIEW ALL CUSTOMERS
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
