package scheduling.optimization.combinatorial.LKH;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.AlgorithmCombinatorialOptimization;
import scheduling.optimization.combinatorial.ProblemCombinatorial;
import scheduling.optimization.statistic.Statistic;

import java.util.ArrayList;
import java.util.List;

public class LinKernighanHeuristic<R extends Number> implements AlgorithmCombinatorialOptimization<Integer, R> {
    protected List<LoggerWrapper> loggers;
    protected LoggerWrapper mainLogger;

    private LKHParameters parameters;

    protected LKHSolution<R> totalBest;
    protected LKHSolution<R> currentBest;
    protected List<LKHSolution<R>> variance;
    protected Integer resource;
    protected SolutionHistory<R> history;

    protected ProblemCombinatorial<Integer, R> problem;
    protected int dimension;

    protected Parameter<Integer> objectiveFunctionCalculationMax;

    protected String name;

    public LinKernighanHeuristic(int objectiveFunctionCalculationMax, LKHParameters parameters) {
        name = "LKH";
        this.parameters = parameters;
        this.objectiveFunctionCalculationMax = new Parameter<>(objectiveFunctionCalculationMax, "objectiveFunctionCalculationMax");
        this.totalBest = null;
        this.currentBest = null;
        history = new SolutionHistory<>();
    }

    public LinKernighanHeuristic(int objectiveFunctionCalculationMax, LKHParameters parameters, String name) {
        this.name = name;
        this.parameters = parameters;
        this.objectiveFunctionCalculationMax = new Parameter<>(objectiveFunctionCalculationMax, "objectiveFunctionCalculationMax");
        this.totalBest = null;
        this.currentBest = null;
        history = new SolutionHistory<>();
    }

    public void setLoggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;
        this.mainLogger = loggers.get(0);
        mainLogger.info(parameters.toString());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IAlgorithmParameters getParameters() {
        return parameters;
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("LinKernighanHeuristic");

        resource = objectiveFunctionCalculationMax.getValue();;
        mainLogger.info("resource = " + resource);

        totalBest = new LKHSolution<>(problem, mainLogger);
        totalBest.setValue(problem.getWorst());

        variance = new ArrayList<>();
        history = new SolutionHistory<>();
    }

    @Override
    public Solution<Integer, R> solveProblem(Problem<Integer, R> problem) {
        mainLogger.info("NEW RUN");
        initialization();

        int i = 0;
        LKHSolution<R> s;
        do{
            mainLogger.info("i = " + i);
            s = new LKHSolution<>(problem, mainLogger);
            s.calculateObjectiveFunction();
            history.add(s.getPath(), s.getSolution().getValue());
            resource--;
            resource = s.change_3(resource, history);
            mainLogger.info("resource = " + resource);
            mainLogger.info(s.getSolution().toString());

            if (totalBest.getSolution() == null) {
                totalBest = new LKHSolution<>(s);
            } else if (s.compareTo(totalBest) > 0) {
                totalBest = new LKHSolution<>(s);
            }

            variance.add(s);
            i++;
        } while(resource > 0);
        // Reverse order so, best individual is on index 0
        //variance.sort((o1, o2) -> problem.compare(o2.getSolution().getValue(), o1.getSolution().getValue()));
        //totalBest = variance.get(0);

        mainLogger.info("best solution " + totalBest.getSolution().getValue().toString());
        mainLogger.info(totalBest.getSolution().toString());

        return totalBest.getSolution();
    }

    @Override
    public Statistic<Integer, R> solveProblem(Problem<Integer, R> problem, int runNumber) {
        this.problem = (ProblemCombinatorial<Integer, R>)problem;
        dimension = problem.getDimension();
        mainLogger.info("set problem, dimension = " + dimension);

        Statistic<Integer, R> statistic = new Statistic<>(problem);

        for (int run = 0; run < runNumber; run++) {
            mainLogger.info("------- NEW RUN " + run + " -------");
            Solution<Integer, R> current = solveProblem(problem);
            statistic.addSolution(current);
        }

        mainLogger.info("----------------------------");
        mainLogger.info("---------- RESULT ----------");
        mainLogger.info(statistic.toString());
        mainLogger.info("----------------------------");

        return statistic;
    }

}
