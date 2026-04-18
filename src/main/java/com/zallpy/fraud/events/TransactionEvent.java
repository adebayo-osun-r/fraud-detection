package com.zallpy.fraud.events;

import java.time.LocalDateTime;

public record TransactionEvent(
        Long transactionId,
        Long userId,
        double amount,
        String location,
        LocalDateTime timestamp
) {}