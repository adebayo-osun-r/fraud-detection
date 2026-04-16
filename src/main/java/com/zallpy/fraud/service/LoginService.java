package com.zallpy.fraud.service;

import com.zallpy.fraud.domain.*;
import com.zallpy.fraud.repository.*;
import com.zallpy.fraud.risk.LoginRiskEngine;
import com.zallpy.fraud.util.RiskUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginEventRepository loginRepo;
    private final UserRepository userRepo;
    private final FraudAlertRepository alertRepo;
    private final LoginRiskEngine riskEngine;

    public LoginService(LoginEventRepository loginRepo,
            UserRepository userRepo,
            FraudAlertRepository alertRepo,
            LoginRiskEngine riskEngine) {
        this.loginRepo = loginRepo;
        this.userRepo = userRepo;
        this.alertRepo = alertRepo;
        this.riskEngine = riskEngine;
    }

    public LoginEvent processLogin(Long userId, LoginEvent event) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isBlocked()) {
            throw new RuntimeException("Account blocked due to suspicious activity");
        }
        event.setUser(user);

        int riskScore = riskEngine.calculateRisk(event);

        LoginEvent saved = loginRepo.save(event);

        // Generate alert
        if (riskScore >= 10) {

            FraudAlert alert = FraudAlert.builder()
                    .alertType("LOGIN")
                    .message("Suspicious login detected")
                    .riskScore((double) riskScore)
                    .riskLevel(RiskUtil.getRiskLevel(riskScore))
                    .referenceId(saved.getId())
                    .user(user)
                    .build();

            alertRepo.save(alert);
        }

        return saved;
    }
}