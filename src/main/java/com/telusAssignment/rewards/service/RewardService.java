package com.telusAssignment.rewards.service;

import com.telusAssignment.rewards.dto.RewardsResponseDto;

/**
 * Service interface for calculating customer rewards.
 * Defines business logic for reward calculations.
 */
public interface RewardService {

    /**
     * Calculate rewards for all customers.
     *
     * @return RewardsResponseDto containing customer rewards and statistics
     * @throws com.telusAssignment.rewards.exception.DatabaseException if database operation fails
     */
    RewardsResponseDto calculateRewards();
}
