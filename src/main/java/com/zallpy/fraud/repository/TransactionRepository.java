package com.zallpy.fraud.repository;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime time);

    long count();

    // =============================
    // AMOUNT
    // =============================
    @Query("SELECT AVG(t.amount) FROM Transaction t WHERE t.user.id = :userId")
    Double getAverageAmount(Long userId);

    @Query("SELECT MAX(t.amount) FROM Transaction t WHERE t.user.id = :userId")
    Double getMaxAmount(Long userId);

  

    // =============================
    // VELOCITY
    // =============================
    @Query("""
                SELECT COUNT(t)
                FROM Transaction t
                WHERE t.user.id = :userId
                AND t.createdAt > CURRENT_TIMESTAMP - 1 MINUTE
            """)
    long countLastMinute(Long userId);

    @Query("""
                SELECT COUNT(t)
                FROM Transaction t
                WHERE t.user.id = :userId
                AND t.createdAt > CURRENT_TIMESTAMP - 5 MINUTE
            """)
    long countLastFiveMinutes(Long userId);

    @Query("""
                SELECT COUNT(t)
                FROM Transaction t
                WHERE t.user.id = :userId
                AND t.createdAt > CURRENT_TIMESTAMP - 1 HOUR
            """)
    long countLastHour(Long userId);

    // =============================
    // LOCATION
    // =============================
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.user.id = :userId")
    Long countTotalTransactions(Long userId);

    @Query("""
                SELECT COUNT(t)
                FROM Transaction t
                WHERE t.user.id = :userId
                AND LOWER(t.location) <> LOWER(t.user.country)
            """)
    Long countForeignTransactions(Long userId);
}