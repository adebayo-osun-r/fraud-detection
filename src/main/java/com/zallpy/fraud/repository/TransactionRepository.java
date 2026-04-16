package com.zallpy.fraud.repository;

import com.zallpy.fraud.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime time);

    long count();
}