package com.zallpy.fraud.ml;

import com.zallpy.fraud.domain.Transaction;
import com.zallpy.fraud.domain.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class FeatureExtractor {

    private static final Set<String> HIGH_RISK = Set.of("North Korea", "Iran", "Syria");

    public static FeatureVector extract(
            Transaction tx,
            User user,
            FeatureService fs
    ) {

        LocalDateTime now = LocalDateTime.now();

        double amount = tx.getAmount();
        double avg = safe(fs.getAverageAmount(user.getId()));
        double max = safe(fs.getMaxAmount(user.getId()));

        double deviation = avg == 0 ? 0 : Math.abs(amount - avg) / avg;
        double amountVsMax = max == 0 ? 0 : amount / max;

        long tx1Min = fs.countLastMinute(user.getId());
        long tx5Min = fs.countLastFiveMinutes(user.getId());
        long tx1Hour = fs.countLastHour(user.getId());

        double velocity = (tx1Min * 3) + (tx5Min * 2) + (tx1Hour * 0.5);

        boolean foreign = !tx.getLocation().equalsIgnoreCase(user.getCountry());

        double locationRisk = fs.getForeignTransactionRatio(user.getId());

        double highRisk = HIGH_RISK.contains(tx.getLocation()) ? 1 : 0;

        double ageDays = Duration.between(user.getCreatedAt(), now).toDays();

        int hour = now.getHour();
        double night = (hour <= 5) ? 1 : 0;

        return new FeatureVector(
                amount,
                avg,
                deviation,
                amountVsMax,
                tx1Min,
                tx5Min,
                tx1Hour,
                velocity,
                foreign ? 1 : 0,
                locationRisk,
                highRisk,
                ageDays,
                ageDays < 7 ? 1 : 0,
                night
        );
    }

    private static double safe(Double v) {
        return v == null ? 0 : v;
    }
}