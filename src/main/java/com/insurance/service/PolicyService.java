package com.insurance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.insurance.entity.Policy;
import com.insurance.repository.PolicyRepository;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // Admin creates policy
    public Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    // View all policies
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    // Delete policy
    public void deletePolicy(Long policyId) {
        policyRepository.deleteById(policyId);
    }

    public Policy getPolicyById(Long policyId) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }
}
