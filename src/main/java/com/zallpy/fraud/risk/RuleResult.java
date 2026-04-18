package com.zallpy.fraud.risk;

public record RuleResult(
        String ruleName,
        int score,
        String reason,
        String category
) {}