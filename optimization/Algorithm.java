package scheduling.optimization;

import scheduling.optimization.statistic.Statistic;

public interface Algorithm<T, R extends Number> extends IAlgorithm<R> {

    Solution<T, R> solveProblem(Problem<T, R> problem);

    Statistic<T, R> solveProblem(Problem<T, R> problem, int runNumber);

}
