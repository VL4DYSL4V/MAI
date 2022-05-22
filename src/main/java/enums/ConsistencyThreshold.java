package enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConsistencyThreshold {

    UPPER_BOUND(0.1),
    LOWER_BOUND(0.0);

    private final double value;

    public static boolean isConsistent(double consistencyCoefficient) {
        return consistencyCoefficient <= UPPER_BOUND.value && consistencyCoefficient >= LOWER_BOUND.value;
    }

}
