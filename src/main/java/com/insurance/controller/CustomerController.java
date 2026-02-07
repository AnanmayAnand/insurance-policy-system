package com.insurance.controller;

import com.insurance.dto.CustomerRequestDTO;
import com.insurance.entity.Customer;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.Policy;
import com.insurance.service.AdminService;
import com.insurance.service.CustomerPolicyService;
import com.insurance.service.CustomerService;
import com.insurance.service.PolicyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private final CustomerService customerService;
    private final PolicyService policyService;
    private final CustomerPolicyService customerPolicyService;
    private final AdminService adminService;

    public CustomerController(CustomerService customerService,
                              PolicyService policyService,
                              CustomerPolicyService customerPolicyService,
                              AdminService adminService) {
        this.customerService = customerService;
        this.policyService = policyService;
        this.customerPolicyService = customerPolicyService;
        this.adminService = adminService;
    }

    // ✅ REGISTER CUSTOMER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerRequestDTO dto) {
        try {
            Customer customer = customerService.registerCustomer(dto);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Username or email already exists");
        }
    }

    // ✅ VIEW CUSTOMER PROFILE
    @GetMapping("/{username}")
    public Customer getCustomer(@PathVariable String username) {
        return customerService.getCustomerByUsername(username);
    }

    // ✅ VIEW ALL AVAILABLE POLICIES
    @GetMapping("/policies")
    public List<Policy> getAllPolicies() {
        return policyService.getAllPolicies();
    }

    // ✅ APPLY FOR POLICY (SAFE)
    @PostMapping("/apply")
    public ResponseEntity<?> applyPolicy(@RequestParam String username,
                                         @RequestParam Long policyId) {
        try {
            if (adminService.isAdminUsername(username)) {
                return ResponseEntity
                        .badRequest()
                        .body("Admin cannot apply for policies");
            }

            Customer customer = customerService.getCustomerByUsername(username);
            Policy policy = policyService.getPolicyById(policyId);

            CustomerPolicy cp =
                    customerPolicyService.applyForPolicy(customer, policy);

            return ResponseEntity.ok(cp);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ VIEW MY POLICIES
    @GetMapping("/my-policies")
    public ResponseEntity<?> myPolicies(@RequestParam String username) {

        if (adminService.isAdminUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body("Admin does not have customer policies");
        }

        Customer customer = customerService.getCustomerByUsername(username);
        List<CustomerPolicy> policies =
                customerPolicyService.getPoliciesByCustomer(customer);

        return ResponseEntity.ok(policies);
    }
}
