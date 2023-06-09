package scheduling.optimization.combinatorial;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Parameter;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.interaction.coevolution.ICoevolutionary;
import scheduling.optimization.statistic.Statistic;

import java.util.ArrayList;
import java.util.List;

public abstract class PopulationBasedCombinatorialAlgorithm<R extends Number> implements
        AlgorithmCombinatorialOptimization<Integer, R>, ICoevolutionary<Integer, R> {
    protected List<LoggerWrapper> loggers;
    protected LoggerWrapper mainLogger;

    protected String name;

    protected ProblemCombinatorial<Integer, R> problem;
    protected int dimension;

    protected Parameter<Integer> individualAmount;
    protected Parameter<Integer> iterationMax;

    protected Solution<Integer, R> totalBestSolution;
    protected Solution<Integer, R> currentBestSolution;

    protected int currentIteration;

    public PopulationBasedCombinatorialAlgorithm(String name){
        this.name = name;
    }

    public List<R> makeSteps(int amount){
        List<R> results = new ArrayList<>(amount);

        for (int i = 0; i < amount; i++) {
            makeStep();
            results.add(currentBestSolution.getValue());
            currentIteration += 1;
        }

        return results;
    }

    public void setProblem(Problem<Integer, R> problem){
        this.problem = (ProblemCombinatorial<Integer, R>)problem;
        dimension = problem.getDimension();
        mainLogger.info("set problem, dimension = " + dimension);
        currentIteration = 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public R getBestResult(){
        return totalBestSolution.getValue();
    }

    public Solution<Integer, R> getTotalBest(){
        return totalBestSolution;
    }

    public Solution<Integer, R> getBest(){
        return currentBestSolution;
    }

    public void setTotalIterationAmount(int iterationAmount){
        this.iterationMax = new Parameter<>(iterationAmount, "iterationMax");
    }

    public void setInitialIndividualAmount(int individualAmount){
        this.individualAmount = new Parameter<>(individualAmount, "dropAmount");
    }

    public Statistic<Integer, R> solveProblem(Problem<Integer, R> problem, int runNumber)  {
        setProblem(problem);

        Statistic<Integer, R> statistic = new Statistic<>(problem);

        for (int run = 0; run < runNumber; run++) {
            mainLogger.info("------- NEW RUN " + run + " -------");
            Solution<Integer, R> current = solveProblem();
            statistic.addSolution(current);
        }

        mainLogger.info("----------------------------");
        mainLogger.info("---------- RESULT ----------");
        mainLogger.info(statistic.toString());
        mainLogger.info("----------------------------");

        return statistic;
    }

    public Solution<Integer, R> solveProblem(Problem<Integer, R> problem) {
        setProblem(problem);
        return solveProblem();
    }

    protected Solution<Integer, R> solveProblem() {
        mainLogger.info("NEW RUN");
        initialization();

        for (currentIteration = 0; currentIteration < iterationMax.getValue(); currentIteration++) {
            makeStep();
        }

        mainLogger.info("best solution " + totalBestSolution.getValue().toString());
        mainLogger.info(totalBestSolution.toString());

        return totalBestSolution;
    }
}
