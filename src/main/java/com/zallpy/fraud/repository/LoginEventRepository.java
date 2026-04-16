package com.zallpy.fraud.repository;

import com.zallpy.fraud.domain.LoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LoginEventRepository extends JpaRepository<LoginEvent, Long> {

    List<LoginEvent> findByUserIdAndCreatedAtAfter(Long userId, LocalDateTime time);
}