package com.telusAssignment.rewards.serviceImpl;

import com.telusAssignment.rewards.dto.RewardsResponseDto;
import com.telusAssignment.rewards.entity.Transaction;
import com.telusAssignment.rewards.exception.DatabaseException;
import com.telusAssignment.rewards.repository.TransactionRepository;
import com.telusAssignment.rewards.service.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of RewardService.
 * Handles the calculation of customer rewards based on transactions.
 */
@Service
public class RewardServiceImpl implements RewardService {

    private static final Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

    private final TransactionRepository repository;

    public RewardServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public RewardsResponseDto calculateRewards() {
        try {
            logger.info("Starting rewards calculation");

            List<Transaction> transactions = repository.findAll();

            if (transactions == null || transactions.isEmpty()) {
                logger.warn("No transactions found in database");
                return new RewardsResponseDto(new HashMap<>(), 0, 0);
            }

            Map<String, Map<String, Integer>> rewards = new HashMap<>();
            long totalRewardPoints = 0;

            for (Transaction transaction : transactions) {
                validateTransaction(transaction);

                int points = calculatePoints(transaction.getAmount());
                String customer = transaction.getCustomerName();
                String month = transaction.getTransactionDate().getMonth().name();

                rewards.putIfAbsent(customer, new HashMap<>());
                Map<String, Integer> customerRewards = rewards.get(customer);

                customerRewards.put(
                        month,
                        customerRewards.getOrDefault(month, 0) + points
                );

                customerRewards.put(
                        "TOTAL",
                        customerRewards.getOrDefault("TOTAL", 0) + points
                );

                totalRewardPoints += points;
            }

            logger.info("Rewards calculation completed successfully. Total customers: {}, Total reward points: {}",
                    rewards.size(), totalRewardPoints);

            return new RewardsResponseDto(rewards, rewards.size(), totalRewardPoints);

        } catch (Exception ex) {
            logger.error("Error calculating rewards", ex);
            throw new DatabaseException("Failed to calculate rewards: " + ex.getMessage(), ex);
        }
    }

    /**
     * Validate transaction data
     *
     * @param transaction the transaction to validate
     * @throws IllegalArgumentException if transaction is invalid
     */
    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getCustomerName() == null || transaction.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (transaction.getTransactionDate() == null) {
            throw new IllegalArgumentException("Transaction date is required");
        }
    }

    /**
     * Calculate reward points based on transaction amount.
     * Rules:
     * - $2 for every dollar spent over $100
     * - $1 for every dollar spent between $50 and $100
     *
     * @param amount the transaction amount
     * @return the calculated reward points
     */
    private int calculatePoints(Double amount) {
        int points = 0;

        if (amount > 100) {
            points += (amount.intValue() - 100) * 2;
            points += 50;
        } else if (amount > 50) {
            points += (amount.intValue() - 50);
        }

        return points;
    }
}


