package com.zallpy.fraud.kafka;

import com.zallpy.fraud.domain.Transaction;
import com.zallpy.fraud.domain.User;
import com.zallpy.fraud.events.*;
import com.zallpy.fraud.ml.*;
import com.zallpy.fraud.risk.RiskEngine;
import com.zallpy.fraud.util.RiskUtil;
import com.zallpy.fraud.risk.RiskAggregator;
import com.zallpy.fraud.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudStreamConsumer {

    private final UserRepository userRepository;
    private final RiskEngine riskEngine;
    private final FraudMLService mlService;
    private final FeatureService featureService;
    private final RiskAggregator riskAggregator;
    private final KafkaTemplate<String, FraudScoredEvent> kafkaTemplate;

    public FraudStreamConsumer(
            UserRepository userRepository,
            RiskEngine riskEngine,
            FraudMLService mlService,
            FeatureService featureService,
            RiskAggregator riskAggregator,
            KafkaTemplate<String, FraudScoredEvent> kafkaTemplate) {

        this.userRepository = userRepository;
        this.riskEngine = riskEngine;
        this.mlService = mlService;
        this.featureService = featureService;
        this.riskAggregator = riskAggregator;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "transaction.created", groupId = "fraud-engine")
    public void consume(TransactionEvent event) {

        User user = userRepository.findById(event.userId())
                .orElseThrow();

        Transaction tx = new Transaction();
        tx.setAmount(event.amount());
        tx.setLocation(event.location());
        tx.setUser(user);

        // =========================
        // RULE SCORE
        // =========================
        double ruleScore = riskEngine.calculate(tx).totalScore();

        // =========================
        // ML SCORE
        // =========================
        FeatureVector features =
                FeatureExtractor.extract(tx, user, featureService);

        double mlScore = mlService.score(features.toArray());

        // =========================
        // FINAL SCORE
        // =========================
        double finalScore = riskAggregator.combine(ruleScore, mlScore);

        boolean flagged = finalScore >= 15;

        String riskLevel = RiskUtil.getRiskLevel(finalScore);

        // =========================
        // PUBLISH RESULT
        // =========================
        FraudScoredEvent result = new FraudScoredEvent(
                event.transactionId(),
                event.userId(),
                ruleScore,
                mlScore,
                finalScore,
                flagged,
                riskLevel
        );

        kafkaTemplate.send("fraud.scored", result.userId().toString(), result);
    }
}