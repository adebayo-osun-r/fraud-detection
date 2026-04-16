package com.zallpy.fraud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStats {

    private long totalTransactions;
    private long totalAlerts;
    private long highRiskAlerts;
    private long blockedUsers;
}