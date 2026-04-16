package com.zallpy.fraud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FraudTrend {

    private String date;
    private long count;
}