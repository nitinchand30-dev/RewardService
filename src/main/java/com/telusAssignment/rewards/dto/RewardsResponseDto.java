package com.telusAssignment.rewards.dto;

import java.util.Map;

/**
 * DTO for Rewards Response.
 * Contains the rewards data organized by customer and month.
 */
public class RewardsResponseDto {

    private Map<String, Map<String, Integer>> customerRewards;
    private int totalCustomers;
    private long totalRewards;

    public RewardsResponseDto() {
    }

    public RewardsResponseDto(Map<String, Map<String, Integer>> customerRewards,
                             int totalCustomers, long totalRewards) {
        this.customerRewards = customerRewards;
        this.totalCustomers = totalCustomers;
        this.totalRewards = totalRewards;
    }

    public Map<String, Map<String, Integer>> getCustomerRewards() {
        return customerRewards;
    }

    public void setCustomerRewards(Map<String, Map<String, Integer>> customerRewards) {
        this.customerRewards = customerRewards;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(long totalRewards) {
        this.totalRewards = totalRewards;
    }
}
