package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskEngine {

    private final List<FraudRule<Transaction>> rules;

    public RiskEngine(List<FraudRule<Transaction>> rules) {
        this.rules = rules;
    }

    public RiskEvaluationResult calculate(Transaction transaction) {

        List<RuleResult> results = rules.stream()
                .map(rule -> rule.apply(transaction))
                .toList();

        int total = results.stream()
                .mapToInt(RuleResult::score)
                .sum();

        return new RiskEvaluationResult(total, results);
    }
}