package com.zallpy.fraud.ml;

import com.zallpy.fraud.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {

    private final TransactionRepository repo;

    public FeatureService(TransactionRepository repo) {
        this.repo = repo;
    }

    // =============================
    // AMOUNT FEATURES
    // =============================

    public double getAverageAmount(Long userId) {
        Double value = repo.getAverageAmount(userId);
        return value != null ? value : 0.0;
    }

    public double getMaxAmount(Long userId) {
        Double value = repo.getMaxAmount(userId);
        return value != null ? value : 0.0;
    }

    // =============================
    // VELOCITY FEATURES
    // =============================

    public long countLastMinute(Long userId) {
        return repo.countLastMinute(userId);
    }

    public long countLastFiveMinutes(Long userId) {
        return repo.countLastFiveMinutes(userId);
    }

    public long countLastHour(Long userId) {
        return repo.countLastHour(userId);
    }

    // =============================
    // LOCATION BEHAVIOR
    // =============================

    public double getForeignTransactionRatio(Long userId) {

        Long total = repo.countTotalTransactions(userId);
        Long foreign = repo.countForeignTransactions(userId);

        if (total == null || total == 0) {
            return 0.0;
        }

        return (double) foreign / total;
    }
}