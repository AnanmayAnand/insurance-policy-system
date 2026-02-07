package com.insurance.dto;

public class AssignPolicyDTO {

    private Long customerPolicyId;
    private String status; // APPROVED / REJECTED

    public Long getCustomerPolicyId() {
        return customerPolicyId;
    }

    public void setCustomerPolicyId(Long customerPolicyId) {
        this.customerPolicyId = customerPolicyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
