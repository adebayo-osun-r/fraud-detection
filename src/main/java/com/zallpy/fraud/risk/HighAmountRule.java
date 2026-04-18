package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class HighAmountRule implements FraudRule<Transaction> {

    @Override
    public RuleResult apply(Transaction tx) {

        if (tx.getAmount() > 10000) {
            return new RuleResult(
                    "HIGH_AMOUNT_RULE",
                    10,
                    "Transaction exceeds 10,000",
                    "AMOUNT"
            );
        }

        return new RuleResult("HIGH_AMOUNT_RULE", 0, "OK", "AMOUNT");
    }
}