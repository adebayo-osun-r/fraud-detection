package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.Transaction;
import com.zallpy.fraud.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VelocityRule implements RiskRule {

    private final TransactionRepository transactionRepository;

    public VelocityRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public int apply(Transaction transaction) {

        if (transaction.getUser() == null) return 0;

        //  Check last 1 minute
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);

        List<Transaction> recentTransactions =
                transactionRepository.findByUserIdAndCreatedAtAfter(
                        transaction.getUser().getId(),
                        oneMinuteAgo
                );

        int count = recentTransactions.size();

        //  Rule Logic
        if (count >= 5) {
            return 10; // high risk
        } else if (count >= 3) {
            return 5; // medium risk
        }

        return 0;
    }
}