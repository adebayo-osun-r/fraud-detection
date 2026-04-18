package com.zallpy.fraud.ml;

import org.springframework.stereotype.Service;

@Service
public class FraudMLService {

    private final ModelManager modelManager;

    public FraudMLService(ModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public double score(double[] features) {
        return modelManager.predict(features);
    }
}