package consistency;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.RealMatrix;

@RequiredArgsConstructor
public class LinearConsistencyOptimizer implements ConsistencyOptimizer {

    private final double alpha;

    @Override
    public RealMatrix optimizeConsistency(RealMatrix input) {
        return null;
    }

}
