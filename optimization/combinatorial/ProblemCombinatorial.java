package scheduling.optimization.combinatorial;

import scheduling.optimization.Problem;

public interface ProblemCombinatorial <T, R> extends Problem<T, R>{
    boolean hasDistances();
    double getDistance(T first, T second);
    ProblemCombinatorial copy();
}
