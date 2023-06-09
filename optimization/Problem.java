package scheduling.optimization;

import java.util.List;

public interface Problem<T, R> extends IProblem {

    int compare(Solution<T, R> first, Solution<T, R> second);

    int compare(R first, R second);

    Solution<T, R> createSolution(List<T> data);

    R calculateObjectiveFunction(Solution<T, R> solution);

    R getWorst();

    Problem<T, R>  copy();

    R getOptimum();

    boolean checkProximity(Solution<T,R> first, Solution<T,R> second, T accuracy);

    Solution<T,R> getOptimumSolution();

    R getAccuracy();

    T getSolutionAccuracy();
}
