package com.zallpy.fraud.ml;

public record FeatureVector(
        double amount,
        double avgAmount,
        double deviation,
        double amountVsMax,

        double tx1Min,
        double tx5Min,
        double tx1Hour,
        double velocityScore,

        double isForeign,
        double locationRisk,
        double highRiskCountry,

        double accountAgeDays,
        double isNewAccount,
        double isNightTransaction
) {

    public double[] toArray() {
        return new double[]{
                amount, avgAmount, deviation, amountVsMax,
                tx1Min, tx5Min, tx1Hour, velocityScore,
                isForeign, locationRisk, highRiskCountry,
                accountAgeDays, isNewAccount, isNightTransaction
        };
    }
}