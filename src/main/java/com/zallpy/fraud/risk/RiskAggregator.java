package com.zallpy.fraud.risk;

import org.springframework.stereotype.Component;

@Component
public class RiskAggregator {

    public double combine(double ruleScore, double mlScore) {

        double normalizedMl = Math.min(Math.max(mlScore, 0), 1);

        return (0.6 * ruleScore) + (0.4 * normalizedMl * 10);
    }
}