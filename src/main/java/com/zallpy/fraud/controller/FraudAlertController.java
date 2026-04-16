package com.zallpy.fraud.controller;

import com.zallpy.fraud.domain.FraudAlert;
import com.zallpy.fraud.repository.FraudAlertRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class FraudAlertController {

    private final FraudAlertRepository fraudAlertRepository;

    public FraudAlertController(FraudAlertRepository fraudAlertRepository) {
        this.fraudAlertRepository = fraudAlertRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<FraudAlert> getAlerts() {
        return fraudAlertRepository.findAll();
    }
}