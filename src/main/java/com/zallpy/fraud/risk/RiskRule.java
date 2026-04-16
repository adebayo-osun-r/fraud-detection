package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;

public interface RiskRule {
    int apply(Transaction transaction);
}  