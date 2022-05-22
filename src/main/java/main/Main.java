package main;

import consistency.ConsistencyOptimizer;
import consistency.LinearConsistencyOptimizer;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import util.ConsistencyUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("==============================================================");
        System.out.println("Optimized matrix:");
        printMatrixPretty(optimized, 3);
        System.out.println("");

        RealVector eigenVector = ConsistencyUtils.computeEigenVector(optimized);
        System.out.print("Eigen vector: ");
        printVectorPretty(eigenVector, 3);
        System.out.println("");

        RealVector localOptimaVector = ConsistencyUtils.computeLocalOptimaVector(eigenVector);
        System.out.print("Local optima vector: ");
        printVectorPretty(localOptimaVector, 3);
        System.out.println("");

        double relativeConsistency = ConsistencyUtils.getRelativeConsistency(optimized);
        System.out.printf("Relative consistency: %.04f%n", relativeConsistency);
        System.out.println("==============================================================");
    }

    private static void printMatrixPretty(RealMatrix matrix, int precision) {
        String template = String.format("%%.%df", precision);
        for (double[] row : matrix.getData()) {
            List<String> formatted = Arrays.stream(row)
                    .mapToObj(d -> String.format(template, d))
                    .collect(Collectors.toList());
            System.out.println(formatted);
        }
        ;
    }

    private static void printVectorPretty(RealVector vector, int precision) {
        String template = String.format("%%.%df", precision);
        List<String> formatted = Arrays.stream(vector.toArray())
                .mapToObj(d -> String.format(template, d))
                .collect(Collectors.toList());
        System.out.println(formatted);
    }

}
