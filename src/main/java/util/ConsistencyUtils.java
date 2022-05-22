package util;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;

public class ConsistencyUtils {

    private ConsistencyUtils(){}

    public static void checkIfMatrixIsSquare(RealMatrix matrix) {
        if (!matrix.isSquare()) {
            throw new RuntimeException("Matrix is not square!");
        }
    }

    public static RealVector computeEigenVector(RealMatrix realMatrix) {
        checkIfMatrixIsSquare(realMatrix);
        double [] vector = new double[realMatrix.getRowDimension()];
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

}
