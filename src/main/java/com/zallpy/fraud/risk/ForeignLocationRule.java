package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class ForeignLocationRule implements RiskRule {

    @Override
    public int apply(Transaction transaction) {
        if (transaction.getUser() == null) return 0;

        return !transaction.getLocation()
                .equalsIgnoreCase(transaction.getUser().getCountry()) ? 5 : 0;
    }
}