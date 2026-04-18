package com.zallpy.fraud.risk;

import java.util.List;

public record RiskEvaluationResult(
        int totalScore,
        List<RuleResult> ruleResults
) {}