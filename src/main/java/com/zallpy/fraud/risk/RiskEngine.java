package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskEngine {

    private final List<RiskRule> rules;

    public RiskEngine(List<RiskRule> rules) {
        this.rules = rules;
    }

    public double calculateRisk(Transaction transaction) {
        return rules.stream()
                .mapToInt(rule -> rule.apply(transaction))
                .sum();
    }
}