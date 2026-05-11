package com.telusAssignment.rewards.controller;

import com.telusAssignment.rewards.service.RewardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RewardsController {

    private final RewardService rewardService;

    public RewardsController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/api/rewards")
    public Map<String, Map<String, Integer>> getRewards() {

        return rewardService.calculateRewards();
    }
}
