package scheduling.optimization.real.PSO.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PopulationBasedRealAlgorithm;

import java.util.List;

public class ParticleSwarmOptimization<R extends Number> extends PopulationBasedRealAlgorithm<R> {
    private PSOParameters parameters;

    protected Particle<R> totalBest;
    protected Particle<R> currentBest;
    protected Population<R> population;

    public ParticleSwarmOptimization(int iterationMax, int individualsAmount,
                                     PSOParameters parameters) {
        super("PSO");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public ParticleSwarmOptimization(int iterationMax, int individualsAmount,
                                     PSOParameters parameters, String name) {
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

        for (int i = 0; i < population.getSize(); i++){
            Particle<R> current = population.getIndividual(i);
            mainLogger.debug("i = " + i);
            printList(current.getVector());
            mainLogger.debug("computeVelocity");
            current.computeVelocity(totalBest);
            printList(current.getVelocity());
            mainLogger.debug("new coordinates");
            current.computeNewCoordinates();
            printList(current.getVector());
            current.calculateObjectiveFunction(problem);

            if (current.compareTo(totalBest) > 0){
                totalBest = new Particle<R>(current, parameters);
                totalBestSolution = totalBest.getSolution();
            }
        }

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
            mainLogger.debug(currentBest + " is better than " + totalBest);
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

        population = new Population<>(problem, individualAmount.getValue(), parameters, mainLogger);
        totalBest = population.findBest();
        totalBestSolution = totalBest.getSolution();
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
