package com.telusAssignment.rewards.controller;

import com.telusAssignment.rewards.dto.ApiResponse;
import com.telusAssignment.rewards.dto.RewardsResponseDto;
import com.telusAssignment.rewards.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing rewards-related endpoints.
 * Provides API endpoints for calculating and retrieving customer rewards.
 */
@RestController
@RequestMapping("/api")
public class RewardsController {

    private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

    private final RewardService rewardService;

    public RewardsController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Get calculated rewards for all customers.
     *
     * @return ResponseEntity containing ApiResponse with RewardsResponseDto
     */
    @GetMapping("/rewards")
    public ResponseEntity<ApiResponse<RewardsResponseDto>> getRewards() {
        try {
            logger.info("Received request to calculate rewards");

            RewardsResponseDto rewards = rewardService.calculateRewards();

            ApiResponse<RewardsResponseDto> response = new ApiResponse<>(
                    true,
                    "Rewards calculated successfully",
                    rewards,
                    HttpStatus.OK.value()
            );

            logger.info("Successfully returned rewards calculation");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Error processing rewards request", ex);
            // Let GlobalExceptionHandler handle the exception
            throw ex;
        }
    }
}

