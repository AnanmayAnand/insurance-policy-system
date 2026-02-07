package com.insurance.dto;

public class PolicyResponseDTO {

    private Long policyId;
    private String policyName;
    private Double premiumAmount;
    private Integer duration;
    private String description;

    public PolicyResponseDTO(Long policyId,
                             String policyName,
                             Double premiumAmount,
                             Integer duration,
                             String description) {
        this.policyId = policyId;
        this.policyName = policyName;
        this.premiumAmount = premiumAmount;
        this.duration = duration;
        this.description = description;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }
}
