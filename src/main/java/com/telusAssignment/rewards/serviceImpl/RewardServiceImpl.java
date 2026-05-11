package com.telusAssignment.rewards.serviceImpl;

import com.telusAssignment.rewards.entity.Transaction;
import com.telusAssignment.rewards.repository.TransactionRepository;
import com.telusAssignment.rewards.service.RewardService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardServiceImpl implements RewardService {

    private final TransactionRepository repository;

    public RewardServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, Map<String, Integer>> calculateRewards() {

        List<Transaction> transactions = repository.findAll();

        Map<String, Map<String, Integer>> rewards = new HashMap<>();

        for (Transaction transaction : transactions) {

            int points = calculatePoints(transaction.getAmount());

            String customer = transaction.getCustomerName();

            String month =
                    transaction.getTransactionDate()
                            .getMonth()
                            .name();

            rewards.putIfAbsent(customer, new HashMap<>());

            Map<String, Integer> customerRewards =
                    rewards.get(customer);

            customerRewards.put(
                    month,
                    customerRewards.getOrDefault(month, 0) + points
            );

            customerRewards.put(
                    "TOTAL",
                    customerRewards.getOrDefault("TOTAL", 0) + points
            );
        }

        return rewards;
    }

    private int calculatePoints(Double amount) {

        int points = 0;

        if (amount > 100) {
            points += (amount.intValue() - 100) * 2;
            points += 50;
        }
        else if (amount > 50) {
            points += (amount.intValue() - 50);
        }

        return points;
    }
}
