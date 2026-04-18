package com.zallpy.fraud.risk;

public interface FraudRule<T> {
    RuleResult apply(T event);
}