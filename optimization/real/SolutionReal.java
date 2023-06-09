package scheduling.optimization.real;

import scheduling.optimization.Solution;

import java.util.List;

public class SolutionReal <T, R> extends Solution<T, R> {
    public SolutionReal(List<T> data, R value) {
        super(data, value);
    }

    public SolutionReal(Solution other) {
        super(other);
    }
}
