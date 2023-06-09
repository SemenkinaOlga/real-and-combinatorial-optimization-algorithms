package scheduling.optimization.real.RGA.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PopulationBasedRealAlgorithm;

import java.util.List;

public class AdaptiveRealGeneticAlgorithm<R extends Number> extends PopulationBasedRealAlgorithm<R> {

    private AdaptiveRGAParameters parameters;

    protected LoggerWrapper selectionLogger;
    protected LoggerWrapper recombinationLogger;
    protected LoggerWrapper mutationLogger;

    protected AdaptiveIndividual<R> totalBest;
    protected AdaptiveIndividual<R> currentBest;
    protected AdaptivePopulation<R> population;

    public AdaptiveRealGeneticAlgorithm(int iterationMax, int individualsAmount,
                                        AdaptiveRGAParameters parameters) {
        super("RGA");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public AdaptiveRealGeneticAlgorithm(int iterationMax, int individualsAmount,
                                        AdaptiveRGAParameters parameters, String name) {
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
        mainLogger.debug("makeStep");

        mainLogger.debug("sort");
        population.sort();
        mainLogger.debug("selectionAndRecombination");
        population.selectionAndRecombination();
        mainLogger.debug("mutation");
        population.mutation();
        mainLogger.debug("createNewPopulation");
        population.createNewPopulation();

        mainLogger.debug("updateOperatorProbability");
        population.updateOperatorProbability(iterationMax.getValue());
        printAllParametersValues();

        updateBest();
        mainLogger.debug("POPULATION:");
        for (Solution <Double, R> solution : population.getPopulation()){
            mainLogger.debug(solution.toString());
        }

        //mainLogger.info(totalBestSolution.getValue().toString());
        mainLogger.debug("finish");
    }

    protected void updateBest(){
        currentBest = population.findBest();
        currentBestSolution = currentBest.getSolution();

        if (totalBest.getSolution() == null) {
            totalBest = currentBest;
            totalBestSolution = totalBest.getSolution();
        } else if (currentBest.compareTo(totalBest) > 0) {
            mainLogger.debug(currentBest + " is better then " + totalBest);
            totalBest = currentBest;
            totalBestSolution = totalBest.getSolution();
        }
    }

    private void printList(List<Double> list){
        StringBuilder sb =new StringBuilder();
        for (Double d : list) {
            sb.append(d);
            sb.append(" ");
        }
        mainLogger.debug(sb.toString());
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("Adaptive Real Genetic Algorithm");

        parameters.mutationSettings.initialize();
        parameters.selectionSettings.initialize();
        parameters.recombinationSettings.initialize();

        totalBest = new AdaptiveIndividual<>(problem, parameters.getBitNumberPerVariable(), mainLogger);
        totalBest.setObjectiveFunctionValue(problem.getWorst());
        population = new AdaptivePopulation<>(problem, individualAmount.getValue(), parameters, mainLogger);
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");

        selectionLogger = loggers.get(1);
        recombinationLogger = loggers.get(2);
        mutationLogger = loggers.get(3);

        printParametersName(selectionLogger, parameters.selectionSettings);
        printParametersName(recombinationLogger, parameters.recombinationSettings);
        printParametersName(mutationLogger, parameters.mutationSettings);

        printAllParametersValues();
    }

    private void printAllParametersValues(){
        printParametersValues(selectionLogger, parameters.selectionSettings);
        printParametersValues(recombinationLogger, parameters.recombinationSettings);
        printParametersValues(mutationLogger, parameters.mutationSettings);
    }

    private <T> void printParametersValues(LoggerWrapper logger, ParameterVariants<T> param){
        StringBuilder sb = new StringBuilder("");
        for (double name: param.getProbabilities()) {
            sb.append(name);
            sb.append("\t");
        }
        logger.info(sb.toString());
    }

    private <T> void printParametersName(LoggerWrapper logger, ParameterVariants<T> param){
        StringBuilder sb = new StringBuilder("");
        logger.info("------- NEW RUN -------");
        for (T name: param.getNames()) {
            sb.append(name);
            sb.append("\t");
        }
        logger.info(sb.toString());
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