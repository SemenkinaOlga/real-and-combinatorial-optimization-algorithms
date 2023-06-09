package scheduling.optimization.real;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;

public interface ProblemReal <T, R> extends Problem<T, R> {
    double getLeftBound();
    double getRightBound();
    ProblemReal copy();
}
