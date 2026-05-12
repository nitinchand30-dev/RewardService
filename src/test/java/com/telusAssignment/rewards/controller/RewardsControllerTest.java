package com.telusAssignment.rewards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.telusAssignment.rewards.dto.RewardsResponseDto;
import com.telusAssignment.rewards.service.RewardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Unit test cases for RewardsController
 */
@WebMvcTest(RewardsController.class)
class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return rewards successfully")
    void testGetRewardsSuccess() throws Exception {

        // Arrange
        RewardsResponseDto rewardsResponseDto = new RewardsResponseDto();

        when(rewardService.calculateRewards()).thenReturn(rewardsResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/rewards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Rewards calculated successfully"))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    @DisplayName("Should throw exception when service fails")
    void testGetRewardsException() throws Exception {

        // Arrange
        when(rewardService.calculateRewards())
                .thenThrow(new RuntimeException("Something went wrong"));

        // Act & Assert
        mockMvc.perform(get("/api/rewards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}