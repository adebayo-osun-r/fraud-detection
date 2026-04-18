package com.zallpy.fraud.kafka;

import com.zallpy.fraud.events.FraudScoredEvent;
import com.zallpy.fraud.repository.FraudAlertRepository;
import com.zallpy.fraud.domain.FraudAlert;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FraudAlertConsumer {

    private final FraudAlertRepository fraudAlertRepository;

    public FraudAlertConsumer(FraudAlertRepository fraudAlertRepository) {
        this.fraudAlertRepository = fraudAlertRepository;
    }

    @KafkaListener(topics = "fraud.scored", groupId = "fraud-alerts")
    public void consume(FraudScoredEvent event) {

        if (!event.flagged()) return;

        FraudAlert alert = FraudAlert.builder()
                .id(event.userId())
                .referenceId(event.transactionId())
                .riskScore(event.finalScore())
                .riskLevel(event.riskLevel())
                .message("Fraud detected via Kafka pipeline")
                .build();

        fraudAlertRepository.save(alert);
    }
}