package com.zallpy.fraud.events;

public record FraudScoredEvent(
        Long transactionId,
        Long userId,
        double ruleScore,
        double mlScore,
        double finalScore,
        boolean flagged,
        String riskLevel
) {}