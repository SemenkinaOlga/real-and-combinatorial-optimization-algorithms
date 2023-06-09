package scheduling.business.logic.model.combinatorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Algorithm;
import scheduling.optimization.Model;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.AlgorithmCombinatorialOptimization;
import scheduling.optimization.real.AlgorithmRealOptimization;
import scheduling.optimization.statistic.Statistic;

public class ModelTravellingSalesman implements Model<Integer, Double> {

    protected Problem<Integer, Double> problem;

    private final static Logger LOGGER = LoggerFactory.getLogger(ModelTravellingSalesman.class);

    public Solution solve(Algorithm<Integer, Double> algorithm) throws IllegalArgumentException {
        LOGGER.info("solve start");
        AlgorithmCombinatorialOptimization<Integer, Double> algorithmTSP;
        Solution solution;
        if (!(algorithm instanceof AlgorithmCombinatorialOptimization)) {
            throw new IllegalArgumentException("Wrong algorithm for ModelTravellingSalesman");
        }
        algorithmTSP = (AlgorithmCombinatorialOptimization) algorithm;
        solution = algorithmTSP.solveProblem(problem);
        return solution;
    }

    public Statistic<Integer, Double> solve(Algorithm<Integer, Double> algorithm, int runNumber) throws IllegalArgumentException {
        LOGGER.info("solve start");
        AlgorithmCombinatorialOptimization<Integer, Double> algorithmTSP;

        if (!(algorithm instanceof AlgorithmCombinatorialOptimization)) {
            throw new IllegalArgumentException("Wrong algorithm for ProblemActivityPriority");
        }
        algorithmTSP = (AlgorithmCombinatorialOptimization) algorithm;
        Statistic<Integer, Double> statistic = algorithmTSP.solveProblem(problem, runNumber);
        return statistic;
    }
}
