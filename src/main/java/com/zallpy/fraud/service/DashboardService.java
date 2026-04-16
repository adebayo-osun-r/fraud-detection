package com.zallpy.fraud.service;

import com.zallpy.fraud.dto.DashboardStats;
import com.zallpy.fraud.dto.FraudTrend;
import com.zallpy.fraud.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final TransactionRepository transactionRepo;
    private final FraudAlertRepository alertRepo;
    private final UserRepository userRepo;

    public DashboardService(TransactionRepository transactionRepo,
            FraudAlertRepository alertRepo,
            UserRepository userRepo) {
        this.transactionRepo = transactionRepo;
        this.alertRepo = alertRepo;
        this.userRepo = userRepo;
    }

    public DashboardStats getStats() {

        long totalTx = transactionRepo.count();
        long totalAlerts = alertRepo.count();
        long highRisk = alertRepo.countByRiskLevel("HIGH");
        long blockedUsers = userRepo.countByIsBlockedTrue();

        return new DashboardStats(
                totalTx,
                totalAlerts,
                highRisk,
                blockedUsers);
    }

    public List<FraudTrend> getFraudTrend() {

        return alertRepo.getFraudTrend().stream()
                .map(obj -> new FraudTrend(
                        obj[0].toString(),
                        ((Number) obj[1]).longValue()))
                .toList();
    }
}