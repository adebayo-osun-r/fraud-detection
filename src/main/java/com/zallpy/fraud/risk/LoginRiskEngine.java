package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.LoginEvent;
import org.springframework.stereotype.Service;

@Service
public class LoginRiskEngine {

    private final FailedLoginRule failedLoginRule;
    private final NewDeviceRule newDeviceRule;

    public LoginRiskEngine(FailedLoginRule failedLoginRule,
                           NewDeviceRule newDeviceRule) {
        this.failedLoginRule = failedLoginRule;
        this.newDeviceRule = newDeviceRule;
    }

    public int calculateRisk(LoginEvent event) {
        return failedLoginRule.apply(event)
                + newDeviceRule.apply(event);
    }
}