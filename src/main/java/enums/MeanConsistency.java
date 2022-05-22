package enums;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public enum MeanConsistency {

    MEAN_CONSISTENCY(new ConcurrentHashMap<>() {{
        put(1, 0.0);
        put(2, 0.0);
        put(3, 0.58);
        put(4, 0.9);
        put(5, 1.12);
        put(6, 1.24);
        put(7, 1.32);
        put(8, 1.41);
        put(9, 1.45);
        put(10, 1.49);
    }});

    private final Map<Integer, Double> consistency;

    public static double getCoefficientByMatrixDimension(int dimension) {
        Double out = MEAN_CONSISTENCY.consistency.get(dimension);
        if (out == null) {
            throw new RuntimeException(String.format("Unsupported dimension %d", dimension));
        }
        return out;
    }

}
