package com.zallpy.fraud.events;

public record FraudAlertEvent(
        Long userId,
        Long transactionId,
        double riskScore,
        String reason
) {}