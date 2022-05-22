package util;

import enums.MeanConsistency;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;

public class ConsistencyUtils {

    private ConsistencyUtils() {
    }

    public static void checkIfMatrixIsSquare(RealMatrix matrix) {
        if (!matrix.isSquare()) {
            throw new RuntimeException("Matrix is not square!");
        }
    }

    public static RealVector computeEigenVector(RealMatrix realMatrix) {
        checkIfMatrixIsSquare(realMatrix);
        double[] vector = new double[realMatrix.getRowDimension()];
        for (int i = 0; i < realMatrix.getRowDimension(); i++) {
            double result = 1;
            double[] row = realMatrix.getRow(i);
            for (double a_j : row) {
                result *= a_j;
            }
            vector[i] = FastMath.pow(result, 1.0 / realMatrix.getColumnDimension());
        }
        return new ArrayRealVector(vector);
    }

    public static RealVector computeLocalOptimaVector(RealVector eigenVector) {
        double sum = 0;
        for (double a_i : eigenVector.toArray()) {
            sum += a_i;
        }
        double finalSum = sum;
        return eigenVector.map(a_i -> a_i / finalSum);
    }

    private static double getMaxValue(double[] eigenValues) {
        if (eigenValues.length == 0) {
            throw new RuntimeException("No real eigen values!");
        }
        double currentMax = eigenValues[0];
        for (double value : eigenValues) {
            if (value >= currentMax) {
                currentMax = value;
            }
        }
        return currentMax;
    }

    public static double getMaxEigenValue(RealMatrix realMatrix) {
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);
        double[] realEigenValues = eigenDecomposition.getRealEigenvalues();
        return getMaxValue(realEigenValues);
    }

    public static double getConsistencyIndex(double maxEigenValue, int matrixDimension) {
        return (maxEigenValue - matrixDimension) / (matrixDimension - 1);
    }

    public static double getRelativeConsistency(RealMatrix realMatrix) {
        double maxEigenValue = getMaxEigenValue(realMatrix);
        double consistencyIndex = getConsistencyIndex(maxEigenValue, realMatrix.getRowDimension());
        return consistencyIndex / MeanConsistency.getCoefficientByMatrixDimension(realMatrix.getRowDimension());
    }
}
