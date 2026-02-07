package com.insurance.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(
    name = "customer_policies",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "policy_id"})
    }
)
public class CustomerPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    @Column(nullable = false)
    private String status; // ACTIVE / EXPIRED

    private LocalDate startDate;
    private LocalDate endDate;

    // getters & setters
    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Policy getPolicy() {
        return policy;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
