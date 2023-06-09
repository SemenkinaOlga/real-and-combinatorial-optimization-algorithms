package scheduling.business.logic.model.real;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Algorithm;
import scheduling.optimization.Model;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.real.AlgorithmRealOptimization;
import scheduling.optimization.statistic.Statistic;

public class ModelRealOptimization implements Model<Double, Double> {

    protected Problem<Double, Double> problem;

    private final static Logger LOGGER = LoggerFactory.getLogger(ModelRealOptimization.class);

    public Solution solve(Algorithm<Double, Double> algorithm) throws IllegalArgumentException {
        LOGGER.info("solve start");
        AlgorithmRealOptimization<Double, Double> algorithmReal;
        Solution solution;
        if (!(algorithm instanceof AlgorithmRealOptimization)) {
            throw new IllegalArgumentException("Wrong algorithm for ProblemActivityPriority");
        }
        algorithmReal = (AlgorithmRealOptimization) algorithm;
        solution = algorithmReal.solveProblem(problem);
        return solution;
    }

    public Statistic<Double, Double> solve(Algorithm<Double, Double> algorithm, int runNumber) throws IllegalArgumentException {
        LOGGER.info("solve start");
        AlgorithmRealOptimization<Double, Double> algorithmReal;

        if (!(algorithm instanceof AlgorithmRealOptimization)) {
            throw new IllegalArgumentException("Wrong algorithm for ProblemActivityPriority");
        }
        algorithmReal = (AlgorithmRealOptimization) algorithm;
        Statistic<Double, Double> statistic = algorithmReal.solveProblem(problem, runNumber);
        return statistic;
    }
}
