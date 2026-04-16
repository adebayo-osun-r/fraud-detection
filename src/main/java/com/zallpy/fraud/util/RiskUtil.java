package com.zallpy.fraud.util;

public class RiskUtil {

    public static String getRiskLevel(double score) {
        if (score >= 15) return "HIGH";
        if (score >= 8) return "MEDIUM";
        return "LOW";
    }
}