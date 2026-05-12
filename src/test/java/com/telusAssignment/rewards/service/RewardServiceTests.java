package com.telusAssignment.rewards.service;

import com.telusAssignment.rewards.dto.RewardsResponseDto;
import com.telusAssignment.rewards.entity.Transaction;
import com.telusAssignment.rewards.exception.DatabaseException;
import com.telusAssignment.rewards.repository.TransactionRepository;
import com.telusAssignment.rewards.serviceImpl.RewardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for RewardService Implementation
 * Tests cover various scenarios including happy path, edge cases, and error handling
 */
@ExtendWith(MockitoExtension.class)
class RewardServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        rewardService = new RewardServiceImpl(transactionRepository);
    }

    /**
     * Test successful calculation of rewards with multiple transactions
     */
    @Test
    void testCalculateRewardsSuccess() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("John", 120.0, LocalDate.of(2025, 1, 15)));
        transactions.add(new Transaction("John", 55.0, LocalDate.of(2025, 1, 25)));
        transactions.add(new Transaction("Jane", 150.0, LocalDate.of(2025, 2, 10)));
        transactions.add(new Transaction("Jane", 75.0, LocalDate.of(2025, 2, 20)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getCustomerRewards());
        assertEquals(2, result.getTotalCustomers());
        assertTrue(result.getTotalRewards() > 0);
        assertTrue(result.getCustomerRewards().containsKey("John"));
        assertTrue(result.getCustomerRewards().containsKey("Jane"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test reward points calculation: $2 for every dollar over $100
     */
    @Test
    void testRewardPointsCalculationAbove100() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        // $120 = 50 + (20 * 2) = 50 + 40 = 90 points
        transactions.add(new Transaction("John", 120.0, LocalDate.of(2025, 1, 15)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        Map<String, Integer> johnRewards = result.getCustomerRewards().get("John");
        assertEquals(90, johnRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test reward points calculation: $1 for every dollar between $50 and $100
     */
    @Test
    void testRewardPointsCalculationBetween50And100() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        // $75 = (75 - 50) = 25 points
        transactions.add(new Transaction("Alice", 75.0, LocalDate.of(2025, 1, 10)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        Map<String, Integer> aliceRewards = result.getCustomerRewards().get("Alice");
        assertEquals(25, aliceRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test no reward points for amount below $50
     */
    @Test
    void testRewardPointsCalculationBelow50() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        // $30 = 0 points
        transactions.add(new Transaction("Bob", 30.0, LocalDate.of(2025, 1, 20)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        Map<String, Integer> bobRewards = result.getCustomerRewards().get("Bob");
        assertEquals(0, bobRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test edge case: amount exactly $50
     */
    @Test
    void testRewardPointsCalculationExactly50() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        // $50 = 0 points (not greater than 50)
        transactions.add(new Transaction("Charlie", 50.0, LocalDate.of(2025, 1, 15)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        Map<String, Integer> charlieRewards = result.getCustomerRewards().get("Charlie");
        assertEquals(0, charlieRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test edge case: amount exactly $100
     */
    @Test
    void testRewardPointsCalculationExactly100() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        // $100 = 50 points
        transactions.add(new Transaction("David", 100.0, LocalDate.of(2025, 1, 15)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        Map<String, Integer> davidRewards = result.getCustomerRewards().get("David");
        assertEquals(50, davidRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test monthly reward tracking for same customer
     */
    @Test
    void testMonthlyRewardTracking() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Emma", 100.0, LocalDate.of(2025, 1, 10)));
        transactions.add(new Transaction("Emma", 60.0, LocalDate.of(2025, 1, 20)));
        transactions.add(new Transaction("Emma", 120.0, LocalDate.of(2025, 2, 15)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        Map<String, Integer> emmaRewards = result.getCustomerRewards().get("Emma");

        // January: 50 + 10 = 60
        assertEquals(60, emmaRewards.get("JANUARY"));
        // February: 90
        assertEquals(90, emmaRewards.get("FEBRUARY"));
        // Total: 150
        assertEquals(150, emmaRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test empty transaction list
     */
    @Test
    void testCalculateRewardsWithEmptyList() {
        // Arrange
        when(transactionRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        assertTrue(result.getCustomerRewards().isEmpty());
        assertEquals(0, result.getTotalCustomers());
        assertEquals(0, result.getTotalRewards());

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test null transaction list
     */
    @Test
    void testCalculateRewardsWithNullList() {
        // Arrange
        when(transactionRepository.findAll()).thenReturn(null);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertNotNull(result);
        assertTrue(result.getCustomerRewards().isEmpty());
        assertEquals(0, result.getTotalCustomers());
        assertEquals(0, result.getTotalRewards());

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test database exception handling
     */
    @Test
    void testCalculateRewardsWithDatabaseException() {
        // Arrange
        when(transactionRepository.findAll())
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        assertThrows(DatabaseException.class, () -> rewardService.calculateRewards());

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test multiple customers with different transaction amounts
     */
    @Test
    void testMultipleCustomersVariousAmounts() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Customer1", 200.0, LocalDate.of(2025, 1, 1)));
        transactions.add(new Transaction("Customer2", 60.0, LocalDate.of(2025, 1, 5)));
        transactions.add(new Transaction("Customer3", 40.0, LocalDate.of(2025, 1, 10)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        assertEquals(3, result.getTotalCustomers());
        // Customer1: 50 + (100 * 2) = 250
        // Customer2: (60 - 50) = 10
        // Customer3: 0
        // Total: 260
        assertEquals(260, result.getTotalRewards());

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test reward accumulation across multiple transactions
     */
    @Test
    void testRewardAccumulationMultipleTransactions() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Frank", 75.0, LocalDate.of(2025, 1, 1)));
        transactions.add(new Transaction("Frank", 85.0, LocalDate.of(2025, 1, 2)));
        transactions.add(new Transaction("Frank", 110.0, LocalDate.of(2025, 1, 3)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        Map<String, Integer> frankRewards = result.getCustomerRewards().get("Frank");
        // 75: 25 + 85: 35 + 110: 70 = 130
        assertEquals(130, frankRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Test different months for same customer
     */
    @Test
    void testDifferentMonthsTrackingSameCustomer() {
        // Arrange
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Grace", 100.0, LocalDate.of(2025, 1, 15)));
        transactions.add(new Transaction("Grace", 100.0, LocalDate.of(2025, 2, 15)));
        transactions.add(new Transaction("Grace", 100.0, LocalDate.of(2025, 3, 15)));

        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        RewardsResponseDto result = rewardService.calculateRewards();

        // Assert
        Map<String, Integer> graceRewards = result.getCustomerRewards().get("Grace");
        assertEquals(50, graceRewards.get("JANUARY"));
        assertEquals(50, graceRewards.get("FEBRUARY"));
        assertEquals(50, graceRewards.get("MARCH"));
        assertEquals(150, graceRewards.get("TOTAL"));

        verify(transactionRepository, times(1)).findAll();
    }
}
