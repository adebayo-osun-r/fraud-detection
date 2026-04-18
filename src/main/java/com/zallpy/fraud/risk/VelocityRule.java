package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import com.zallpy.fraud.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VelocityRule implements FraudRule<Transaction> {

    private final TransactionRepository repo;

    public VelocityRule(TransactionRepository repo) {
        this.repo = repo;
    }

    @Override
    public RuleResult apply(Transaction tx) {

        if (tx.getUser() == null) {
            return new RuleResult("VELOCITY_RULE", 0, "No user", "VELOCITY");
        }

        LocalDateTime window = LocalDateTime.now().minusMinutes(1);

        int count = repo.findByUserIdAndCreatedAtAfter(
                tx.getUser().getId(),
                window
        ).size();

        if (count >= 5) {
            return new RuleResult("VELOCITY_RULE", 10, "High transaction velocity", "VELOCITY");
        }

        if (count >= 3) {
            return new RuleResult("VELOCITY_RULE", 5, "Medium velocity", "VELOCITY");
        }

        return new RuleResult("VELOCITY_RULE", 0, "OK", "VELOCITY");
    }
}