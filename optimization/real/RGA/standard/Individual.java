package scheduling.optimization.real.RGA.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.RGA.IndividualBase;

import java.util.List;

public class Individual<R extends Number> extends IndividualBase<R> {

    public Individual(ProblemReal<Double, R> problem, int bitNumberPerVariable, LoggerWrapper logger) {
        super(problem, bitNumberPerVariable, logger);
    }

    public Individual(ProblemReal<Double, R> problem, int bitNumberPerVariable, List<Integer> genotype, LoggerWrapper logger) {
        super(problem, bitNumberPerVariable, genotype, logger);
    }

    public Individual(Individual<R> individual) {
        super(individual);
    }

    public Individual(ProblemReal<Double, R> problem, int bitNumberPerVariable, Solution<Double, R> solution, LoggerWrapper logger) {
        super(problem, bitNumberPerVariable, solution, logger);
    }

}
