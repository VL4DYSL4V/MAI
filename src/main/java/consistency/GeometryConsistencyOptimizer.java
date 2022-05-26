package consistency;

import enums.ConsistencyThreshold;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;
import util.ConsistencyUtils;

@RequiredArgsConstructor
public class GeometryConsistencyOptimizer implements ConsistencyOptimizer {

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
                for (int j = 0; j < input.getColumnDimension(); j++) {
                    double oldA_ij = input.getEntry(i, j);
                    double a_ij_new = computeNewA_ij(oldA_ij, optimaVector, i, j);
                    out.setEntry(i, j, a_ij_new);
                }
            }
            relativeConsistency = ConsistencyUtils.getRelativeConsistency(out);
        }
        return out;
    }

    private double computeNewA_ij(double oldA_ij, RealVector optimaVector, int i, int j) {
        double weight_i = optimaVector.getEntry(i);
        double weight_j = optimaVector.getEntry(j);
        return FastMath.pow(oldA_ij, alpha) * FastMath.pow(weight_i / weight_j, 1 - alpha);
    }

}
