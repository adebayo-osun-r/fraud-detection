package com.zallpy.fraud.repository;

import com.zallpy.fraud.domain.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {
    List<FraudAlert> findByUserId(Long userId);

    long count();

    long countByRiskLevel(String riskLevel);

    @Query(value = """
                SELECT DATE(created_at) as date, COUNT(*) as count
                FROM fraud_alerts
                GROUP BY DATE(created_at)
                ORDER BY date
            """, nativeQuery = true)
    List<Object[]> getFraudTrend();
}