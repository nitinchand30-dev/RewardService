package com.telusAssignment.rewards.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RewardService {

    Map<String, Map<String, Integer>> calculateRewards();
}
