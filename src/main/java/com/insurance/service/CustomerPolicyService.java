package com.insurance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.insurance.entity.Customer;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.Policy;
import com.insurance.repository.CustomerPolicyRepository;

@Service
public class CustomerPolicyService {

    private final CustomerPolicyRepository customerPolicyRepository;

    public CustomerPolicyService(CustomerPolicyRepository customerPolicyRepository) {
        this.customerPolicyRepository = customerPolicyRepository;
    }

    // ✅ CUSTOMER applies for policy (DUPLICATE SAFE)
    public CustomerPolicy applyForPolicy(Customer customer, Policy policy) {

        boolean alreadyApplied =
                customerPolicyRepository
                        .existsByCustomerAndPolicy(customer, policy);

        if (alreadyApplied) {
            throw new RuntimeException("Policy already applied");
        }

        CustomerPolicy customerPolicy = new CustomerPolicy();
        customerPolicy.setCustomer(customer);
        customerPolicy.setPolicy(policy);
        customerPolicy.setStatus("PENDING");

        return customerPolicyRepository.save(customerPolicy);
    }

    // ✅ CUSTOMER views policies
    public List<CustomerPolicy> getPoliciesByCustomer(Customer customer) {
        return customerPolicyRepository.findByCustomer(customer);
    }

    // ✅ ADMIN views pending
    public List<CustomerPolicy> getPendingRequests() {
        return customerPolicyRepository.findByStatus("PENDING");
    }

    // ✅ ADMIN approves
    public CustomerPolicy approvePolicy(Long customerPolicyId) {

        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        cp.setStatus("APPROVED");
        cp.setStartDate(LocalDate.now());
        cp.setEndDate(LocalDate.now().plusYears(cp.getPolicy().getDuration()));

        return customerPolicyRepository.save(cp);
    }

    // ✅ ADMIN rejects
    public CustomerPolicy rejectPolicy(Long customerPolicyId) {

        CustomerPolicy cp = customerPolicyRepository.findById(customerPolicyId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        cp.setStatus("REJECTED");
        return customerPolicyRepository.save(cp);
    }
}
