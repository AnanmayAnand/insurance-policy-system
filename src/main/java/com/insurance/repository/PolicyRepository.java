package com.insurance.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.insurance.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    // show only active policies
    List<Policy> findByActiveTrue();

    // search policies by name
    List<Policy> findByPolicyNameContainingIgnoreCaseAndActiveTrue(String keyword);
}
