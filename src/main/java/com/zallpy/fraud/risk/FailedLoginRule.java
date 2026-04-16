package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.LoginEvent;
import com.zallpy.fraud.repository.LoginEventRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FailedLoginRule {

    private final LoginEventRepository loginRepo;

    public FailedLoginRule(LoginEventRepository loginRepo) {
        this.loginRepo = loginRepo;
    }

    public int apply(LoginEvent event) {

        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

        List<LoginEvent> recent =
                loginRepo.findByUserIdAndCreatedAtAfter(
                        event.getUser().getId(),
                        fiveMinutesAgo
                );

        long failedCount = recent.stream()
                .filter(e -> !e.isSuccess())
                .count();

        if (failedCount >= 5) return 10; // high risk
        if (failedCount >= 3) return 5;  // medium risk

        return 0;
    }
}