package com.zallpy.fraud.controller;

import com.zallpy.fraud.dto.DashboardStats;
import com.zallpy.fraud.dto.FraudTrend;
import com.zallpy.fraud.service.DashboardService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DashboardStats getStats() {
        return dashboardService.getStats();
    }

    @GetMapping("/trend")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<FraudTrend> trend() {
        return dashboardService.getFraudTrend();
    }
}