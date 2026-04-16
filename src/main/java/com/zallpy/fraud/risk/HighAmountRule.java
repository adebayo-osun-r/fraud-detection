package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class HighAmountRule implements RiskRule {

    @Override
    public int apply(Transaction transaction) {
        return transaction.getAmount() > 10000 ? 10 : 0;
    }
}