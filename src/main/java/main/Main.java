package main;

import consistency.ConsistencyOptimizer;
import consistency.LinearConsistencyOptimizer;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import util.ConsistencyUtils;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        RealMatrix matrix = new Array2DRowRealMatrix(new double[][]{
                {1, 1.0 / 5, 1.0 / 7, 1.0 / 3, 1.0 / 4},
                {5, 1, 1.0 / 3, 8, 1.0 / 6},
                {7, 3, 1, 5, 2},
                {3, 1.0 / 8, 1.0 / 5, 1, 1.0 / 7},
                {4, 6, 1.0 / 2, 7, 1},
        });
        ConsistencyOptimizer optimizer = new LinearConsistencyOptimizer(0.5);
        RealMatrix optimized = optimizer.optimizeConsistency(matrix);
        for (double[] row: optimized.getData()) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("");
        double relativeConsistency = ConsistencyUtils.getRelativeConsistency(optimized);
        System.out.println(relativeConsistency);
    }



}
