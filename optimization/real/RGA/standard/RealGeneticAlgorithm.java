package scheduling.optimization.real.RGA.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PopulationBasedRealAlgorithm;

import java.util.List;

public class RealGeneticAlgorithm<R extends Number> extends PopulationBasedRealAlgorithm<R> {

    private RGAParameters parameters;

    protected Individual<R> totalBest;
    protected Individual<R> currentBest;
    protected Population<R> population;

    public RealGeneticAlgorithm(int iterationMax, int individualsAmount,
                                RGAParameters parameters) {
        super("RGA");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public RealGeneticAlgorithm(int iterationMax, int individualsAmount,
                                RGAParameters parameters, String name) {
        super(name);
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public void setLoggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;
        this.mainLogger = loggers.get(0);
        mainLogger.info(parameters.toString());
    }

    @Override
    public IAlgorithmParameters getParameters() {
        return parameters;
    }

    //region ICooperative

    public void makeStep() {

        population.sort();
        population.selection();
        population.recombination();
        population.mutation();
        population.createNewPopulation();

        updateBest();

        //mainLogger.info(totalBestSolution.getValue().toString());
    }

    protected void updateBest(){
        currentBest = population.findBest();
        currentBestSolution = currentBest.getSolution();

        if (totalBest.getSolution() == null) {
            totalBest = currentBest;
            totalBestSolution = totalBest.getSolution();
        } else if (currentBest.compareTo(totalBest) > 0) {
            totalBest = currentBest;
            totalBestSolution = totalBest.getSolution();
        }
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("Real Genetic Algorithm");

        totalBest = new Individual<>(problem, parameters.getBitNumberPerVariable(), mainLogger);
        totalBest.setObjectiveFunctionValue(problem.getWorst());
        population = new Population<>(problem, individualAmount.getValue(), parameters, mainLogger);
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");
    }

    public void resizePopulation(int size) {
        mainLogger.info("resizePopulation " + size);
        population.resizePopulation(size);
    }

    public int getPopulationSize() {
        return population.getSize();
    }

    //endregion

    //region Migration
    @Override
    public Solution<Double, R> getCurrentBest() {
        return currentBest.getSolution();
    }

    @Override
    public Solution<Double, R> getRandom() {
        return population.chooseRandom().getSolution();
    }

    @Override
    public void replaceRandom(List<Solution<Double, R>> solutions) {
        population.replaceRandom(solutions);
    }

    @Override
    public void replaceWorst(List<Solution<Double, R>> solutions) {
        population.replaceWorst(solutions);
    }

    public List<Solution<Double, R>> getPopulation(){
        return population.getPopulation();
    }

    public void setPopulation(List<Solution<Double, R>> solutions){
        population.setPopulation(solutions);
        updateBest();
    }
    //endregion
}