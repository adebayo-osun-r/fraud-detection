package com.zallpy.fraud.risk;

import com.zallpy.fraud.domain.LoginEvent;
import org.springframework.stereotype.Component;

@Component
public class NewDeviceRule {

    public int apply(LoginEvent event) {

        if (event.getUser() == null) return 0;

        // Simple version (upgrade later)
        if (!event.getLocation().equalsIgnoreCase(event.getUser().getCountry())) {
            return 5;
        }

        return 0;
    }
}