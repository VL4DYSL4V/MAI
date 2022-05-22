package consistency;

import org.apache.commons.math3.linear.RealMatrix;

public interface ConsistencyOptimizer {

    RealMatrix optimizeConsistency(RealMatrix input);

}
