package scheduling.optimization.real.PSO.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PSO.standard.PSOParameters;
import scheduling.optimization.real.PSO.standard.Particle;
import scheduling.optimization.real.PSO.standard.Population;
import scheduling.optimization.real.PopulationBasedRealAlgorithm;

import java.util.List;

public class AdaptiveParticleSwarmOptimization<R extends Number> extends PopulationBasedRealAlgorithm<R> {
    private AdaptivePSOParameters parameters;

    protected AdaptiveParticle<R> totalBest;
    protected AdaptiveParticle<R> currentBest;
    protected AdaptivePopulation<R> population;
    protected LoggerWrapper socialLogger;
    protected LoggerWrapper cognitiveLogger;
    protected LoggerWrapper weightLogger;

    public AdaptiveParticleSwarmOptimization(int iterationMax, int individualsAmount,
                                             AdaptivePSOParameters parameters) {
        super("ScPSO");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public AdaptiveParticleSwarmOptimization(int iterationMax, int individualsAmount,
                                             AdaptivePSOParameters parameters, String name) {
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

        for (int i = 0; i < population.getSize(); i++){
            mainLogger.debug("i = " + i);
            AdaptiveParticle<R> current = population.getIndividual(i);
            current.computeVelocity(totalBest);
            current.computeNewCoordinates();
            current.calculateObjectiveFunction(problem);
            mainLogger.debug(current.toString());

            if (current.compareTo(totalBest) > 0){
                totalBest = new AdaptiveParticle<>(current, parameters);
                totalBestSolution = totalBest.getSolution();
            }
        }

        updateBest();

        mainLogger.debug("POPULATION:");
        for (Solution <Double, R> solution : population.getPopulation()){
            mainLogger.debug(solution.toString());
        }

        //mainLogger.info(totalBestSolution.getValue().toString());
        mainLogger.debug("finish");
    }

    protected void updateBest() {
        currentBest = population.findBest();
        currentBestSolution = currentBest.getSolution();
        population.updateOperatorProbability(iterationMax.getValue(), socialLogger, cognitiveLogger);
        printAllParametersValues();

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
        mainLogger.info("Particle Swarm Optimization");

        parameters.socialParameter.initialize();
        parameters.cognitiveParameter.initialize();

        population = new AdaptivePopulation<>(problem, individualAmount.getValue(), parameters, mainLogger);
        totalBest = population.findBest();
        totalBestSolution = totalBest.getSolution();
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");
        socialLogger = loggers.get(1);
        cognitiveLogger = loggers.get(2);
        weightLogger = loggers.get(3);

        printParametersName(cognitiveLogger, parameters.cognitiveParameter);
        printParametersName(socialLogger, parameters.socialParameter);
        printParametersName(weightLogger, parameters.w);

        printAllParametersValues();
    }

    private void printParametersName(LoggerWrapper logger, ParameterVariants<Double> param){
        StringBuilder sb = new StringBuilder("");
        logger.info("------- NEW RUN -------");
        for (double name: param.getNames()) {
            sb.append(name);
            sb.append("\t");
        }
        logger.info(sb.toString());
    }

    private void printAllParametersValues(){
        printParametersValues(cognitiveLogger, parameters.cognitiveParameter);
        printParametersValues(socialLogger, parameters.socialParameter);
        printParametersValues(weightLogger, parameters.w);
    }

    private void printParametersValues(LoggerWrapper logger, ParameterVariants<Double> param){
        StringBuilder sb = new StringBuilder("");
        for (double name: param.getProbabilities()) {
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
