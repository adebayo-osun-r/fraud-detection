package com.zallpy.fraud.service;

import com.zallpy.fraud.domain.*;
import com.zallpy.fraud.ml.*;
import com.zallpy.fraud.risk.*;
import com.zallpy.fraud.repository.*;
import com.zallpy.fraud.util.RiskUtil;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;
        private final FraudAlertRepository fraudAlertRepository;

        private final RiskEngine riskEngine;
        private final FraudMLService mlService;
        private final FeatureService featureService;
        private final RiskAggregator riskAggregator;
        private final BlockService blockService;

        public TransactionService(
                        TransactionRepository transactionRepository,
                        UserRepository userRepository,
                        FraudAlertRepository fraudAlertRepository,
                        RiskEngine riskEngine,
                        FraudMLService mlService,
                        FeatureService featureService,
                        RiskAggregator riskAggregator,
                        BlockService blockService) {

                this.transactionRepository = transactionRepository;
                this.userRepository = userRepository;
                this.fraudAlertRepository = fraudAlertRepository;
                this.riskEngine = riskEngine;
                this.mlService = mlService;
                this.featureService = featureService;
                this.riskAggregator = riskAggregator;
                this.blockService = blockService;
        }

        public Transaction processTransaction(Long userId, Transaction tx) {

                User user = userRepository.findById(userId)
                                .orElseThrow();

                if (user.isBlocked())
                        throw new RuntimeException("Blocked");

                tx.setUser(user);

                var ruleResult = riskEngine.calculate(tx);

                FeatureVector features = FeatureExtractor.extract(tx, user, featureService);

                double mlScore = mlService.score(features.toArray());

                double finalScore = riskAggregator.combine(
                                ruleResult.totalScore(),
                                mlScore);

                tx.setRiskScore(finalScore);
                tx.setFlagged(finalScore >= 15);

                Transaction saved = transactionRepository.save(tx);

                if (saved.isFlagged()) {

                        fraudAlertRepository.save(
                                        FraudAlert.builder()
                                                        .user(user)
                                                        .riskScore(finalScore)
                                                        .riskLevel(RiskUtil.getRiskLevel(finalScore))
                                                        .message("Fraud detected")
                                                        .referenceId(saved.getId())
                                                        .build());

                        blockService.evaluateAndBlockUser(user);
                }

                return saved;
        }
}