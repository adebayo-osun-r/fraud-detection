package com.zallpy.fraud.service;

import com.zallpy.fraud.domain.*;
import com.zallpy.fraud.repository.*;
import com.zallpy.fraud.risk.RiskEngine;
import com.zallpy.fraud.util.RiskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final FraudAlertRepository fraudAlertRepository;
    private final RiskEngine riskEngine;
    private final BlockService blockService;

    public TransactionService(TransactionRepository transactionRepository,
            UserRepository userRepository,
            FraudAlertRepository fraudAlertRepository,
            RiskEngine riskEngine,
            BlockService blockService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.fraudAlertRepository = fraudAlertRepository;
        this.riskEngine = riskEngine;
        this.blockService = blockService;
    }

    public Transaction processTransaction(Long userId, Transaction transaction) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isBlocked()) {
            throw new RuntimeException("User account is blocked");
        }
        transaction.setUser(user);

        // Calculate risk
        double riskScore = riskEngine.calculateRisk(transaction);

        transaction.setRiskScore(riskScore);
        transaction.setFlagged(riskScore >= 15);

        Transaction savedTx = transactionRepository.save(transaction);

        // Generate Fraud Alert
        if (savedTx.isFlagged()) {

            FraudAlert alert = FraudAlert.builder()
                    .alertType("TRANSACTION")
                    .message("High-risk transaction detected")
                    .riskScore(riskScore)
                    .riskLevel(RiskUtil.getRiskLevel(riskScore))
                    .referenceId(savedTx.getId())
                    .user(user)
                    .build();

            fraudAlertRepository.save(alert);
            blockService.evaluateAndBlockUser(user);

            log.warn(" FRAUD ALERT: User {} | Tx {} | Score {}",
                    user.getId(), savedTx.getId(), riskScore);
        } else {
            log.info(" Transaction OK: User {} | Score {}", user.getId(), riskScore);
        }

        return savedTx;
    }
}