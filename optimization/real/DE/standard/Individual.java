package scheduling.optimization.real.DE.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.IndividualBase;
import scheduling.optimization.real.ProblemReal;

public class Individual<R extends Number> extends IndividualBase<R> {

    public Individual(ProblemReal<Double, R> problem, LoggerWrapper logger) {
        super(problem, logger);
    }

    public Individual(Individual<R> individual) {
        super(individual);
    }

    public Individual(ProblemReal<Double, R> problem, Solution<Double, R> solution, LoggerWrapper logger) {
        super(problem, solution, logger);
    }

}
