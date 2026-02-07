package com.insurance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.insurance.dto.AssignPolicyDTO;
import com.insurance.dto.PolicyRequestDTO;
import com.insurance.dto.PolicyResponseDTO;
import com.insurance.entity.CustomerPolicy;
import com.insurance.entity.Policy;
import com.insurance.repository.AdminRepository;
import com.insurance.repository.CustomerPolicyRepository;
import com.insurance.repository.PolicyRepository;

@Service
public class AdminService {

    private final PolicyRepository policyRepository;
    private final CustomerPolicyRepository customerPolicyRepository;
    private final AdminRepository adminRepository;

    public AdminService(
            PolicyRepository policyRepository,
            CustomerPolicyRepository customerPolicyRepository,
            AdminRepository adminRepository) {
        this.policyRepository = policyRepository;
        this.customerPolicyRepository = customerPolicyRepository;
        this.adminRepository = adminRepository;
    }

    // 🔒 CHECK IF USERNAME BELONGS TO ADMIN
    public boolean isAdminUsername(String username) {
        return adminRepository.findByUsername(username) != null;
    }

    // ✅ CREATE POLICY
    public void createPolicy(PolicyRequestDTO dto) {
        Policy policy = new Policy();
        policy.setPolicyName(dto.getPolicyName());
        policy.setPremiumAmount(dto.getPremiumAmount());
        policy.setDuration(dto.getDuration());
        policy.setDescription(dto.getDescription());
        policy.setActive(true); // 🔥 important for soft delete

        policyRepository.save(policy);
    }

    // ✅ VIEW ALL ACTIVE POLICIES
    public List<PolicyResponseDTO> getAllPolicies() {
        return policyRepository.findByActiveTrue()
                .stream()
                .map(p -> new PolicyResponseDTO(
                        p.getPolicyId(),
                        p.getPolicyName(),
                        p.getPremiumAmount(),
                        p.getDuration(),
                        p.getDescription()
                ))
                .collect(Collectors.toList());
    }

    // 🔍 SEARCH POLICIES (ACTIVE ONLY)
    public List<PolicyResponseDTO> searchPolicies(String keyword) {
        return policyRepository
                .findByPolicyNameContainingIgnoreCaseAndActiveTrue(keyword)
                .stream()
                .map(p -> new PolicyResponseDTO(
                        p.getPolicyId(),
                        p.getPolicyName(),
                        p.getPremiumAmount(),
                        p.getDuration(),
                        p.getDescription()
                ))
                .collect(Collectors.toList());
    }

    // ❌ SOFT DELETE POLICY
    public void deletePolicy(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setActive(false); // soft delete
        policyRepository.save(policy);
    }

    // ✅ VIEW PENDING REQUESTS
    public List<CustomerPolicy> getPendingRequests() {
        return customerPolicyRepository.findByStatus("PENDING");
    }

    // ✅ APPROVE / REJECT POLICY
    public void approvePolicy(AssignPolicyDTO dto) {

        CustomerPolicy cp = customerPolicyRepository.findById(dto.getCustomerPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy request not found"));

        if ("APPROVED".equalsIgnoreCase(dto.getStatus())) {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusYears(cp.getPolicy().getDuration());

            cp.setStatus("APPROVED");
            cp.setStartDate(startDate);
            cp.setEndDate(endDate);
        } else {
            cp.setStatus("REJECTED");
        }

        customerPolicyRepository.save(cp);
    }
}
