package consistency;

import enums.ConsistencyThreshold;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import util.ConsistencyUtils;

import java.util.Arrays;

@RequiredArgsConstructor
public class LinearConsistencyOptimizer implements ConsistencyOptimizer {

    private final double alpha;

    @Override
    public RealMatrix optimizeConsistency(RealMatrix input) {
        ConsistencyUtils.checkIfMatrixIsSquare(input);
        RealMatrix out = input.copy();
        double relativeConsistency = ConsistencyUtils.getRelativeConsistency(input);
        while (!ConsistencyThreshold.isConsistent(relativeConsistency)) {
            RealVector eigenVector = ConsistencyUtils.computeEigenVector(input);
            RealVector optimaVector = ConsistencyUtils.computeLocalOptimaVector(eigenVector);
            for (int i = 0; i < input.getRowDimension(); i++) {
                for (int j = i + 1; j < input.getColumnDimension(); j++) {
                    double oldA_ij = input.getEntry(i, j);
                    double a_ij_new = computeNewA_ij(oldA_ij, optimaVector, i, j);
                    out.setEntry(i, j, a_ij_new);
                    out.setEntry(j, i, 1.0 / a_ij_new);
                }
            }
            relativeConsistency = ConsistencyUtils.getRelativeConsistency(out);
        }
        return out;
    }

    private double computeNewA_ij(double oldA_ij, RealVector optimaVector, int i, int j) {
        double weight_i = optimaVector.getEntry(i);
        double weight_j = optimaVector.getEntry(j);
        return alpha * oldA_ij + (1 - alpha) * (weight_i / weight_j);
    }

}
