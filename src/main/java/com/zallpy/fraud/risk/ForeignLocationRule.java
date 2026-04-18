package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class ForeignLocationRule implements FraudRule<Transaction> {

    @Override
    public RuleResult apply(Transaction tx) {

        if (tx.getUser() == null) {
            return new RuleResult("FOREIGN_RULE", 0, "No user", "LOCATION");
        }

        boolean foreign = !tx.getLocation()
                .equalsIgnoreCase(tx.getUser().getCountry());

        return foreign
                ? new RuleResult("FOREIGN_RULE", 5, "Foreign transaction detected", "LOCATION")
                : new RuleResult("FOREIGN_RULE", 0, "OK", "LOCATION");
    }
}