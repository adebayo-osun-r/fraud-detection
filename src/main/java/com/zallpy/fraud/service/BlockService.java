package com.zallpy.fraud.service;

import com.zallpy.fraud.domain.User;
import com.zallpy.fraud.repository.FraudAlertRepository;
import com.zallpy.fraud.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    private final FraudAlertRepository alertRepo;
    private final UserRepository userRepo;

    public BlockService(FraudAlertRepository alertRepo,
                        UserRepository userRepo) {
        this.alertRepo = alertRepo;
        this.userRepo = userRepo;
    }

    public void evaluateAndBlockUser(User user) {

        long highRiskCount = alertRepo.findByUserId(user.getId())
                .stream()
                .filter(a -> "HIGH".equals(a.getRiskLevel()))
                .count();

        if (highRiskCount >= 3 && !user.isBlocked()) {

            user.setBlocked(true);
            user.setBlockReason("Multiple high-risk fraud activities");

            userRepo.save(user);
        }
    }
}