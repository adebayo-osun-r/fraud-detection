package com.zallpy.fraud.ml;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;
import smile.data.type.*;
import smile.data.vector.IntVector;

@Component
public class ModelManager {

    private RandomForest model;
    private StructType schema;

    @PostConstruct
    public void init() {
        trainModel();
    }

    private void trainModel() {

        double[][] X = {
                {100, 80, 0.2, 1, 2, 3, 0, 0.1, 100, 0, 0, 10, 0, 0},
                {20000, 500, 5, 5, 10, 15, 1, 0.5, 30, 1, 1, 1, 1, 1}
        };

        int[] y = {0, 1};

        schema = new StructType(
                new StructField("amount", DataTypes.DoubleType),
                new StructField("avgAmount", DataTypes.DoubleType),
                new StructField("deviation", DataTypes.DoubleType),
                new StructField("amountVsMax", DataTypes.DoubleType),
                new StructField("tx1Min", DataTypes.DoubleType),
                new StructField("tx5Min", DataTypes.DoubleType),
                new StructField("tx1Hour", DataTypes.DoubleType),
                new StructField("velocity", DataTypes.DoubleType),
                new StructField("isForeign", DataTypes.DoubleType),
                new StructField("locationRisk", DataTypes.DoubleType),
                new StructField("highRiskCountry", DataTypes.DoubleType),
                new StructField("accountAge", DataTypes.DoubleType),
                new StructField("isNewAccount", DataTypes.DoubleType),
                new StructField("isNight", DataTypes.DoubleType)
        );

        DataFrame df = DataFrame.of(X, schema.names());
        df = df.merge(IntVector.of("label", y));

        model = RandomForest.fit(Formula.lhs("label"), df);
    }

    public double predict(double[] features) {

        Tuple tuple = Tuple.of(features, schema);

        int result = model.predict(tuple);

        return result == 1 ? 0.95 : 0.05;
    }
}