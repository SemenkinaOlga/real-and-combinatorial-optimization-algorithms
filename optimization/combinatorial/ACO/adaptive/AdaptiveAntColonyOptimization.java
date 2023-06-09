package scheduling.optimization.combinatorial.ACO.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.AntColonyOptimizationBase;
import scheduling.optimization.combinatorial.ACO.ICooperativeACO;

import java.util.Collection;
import java.util.List;

public class AdaptiveAntColonyOptimization<R extends Number> extends AntColonyOptimizationBase<R, AdaptiveAnt<R>> {
    private AdaptiveACOParameters parameters;
    private AdaptiveAnt<R> currentBest;
    private AdaptiveAnt<R> totalBest;
    private AdaptiveColony<R> currentColony;

    protected LoggerWrapper alphaLogger;
    protected LoggerWrapper betaLogger;

    public AdaptiveAntColonyOptimization(int iterationMax, int antAmount, AdaptiveACOParameters parameters) {
        super(iterationMax, antAmount, parameters, "AACO");
        this.parameters = parameters;
    }

    public AdaptiveAntColonyOptimization(int iterationMax, int antAmount, AdaptiveACOParameters parameters, String name) {
        super(iterationMax, antAmount, parameters, name);
        this.parameters = parameters;
    }

    @Override
    public void setLoggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;
        this.mainLogger = loggers.get(0);
        this.alphaLogger = loggers.get(1);
        this.betaLogger = loggers.get(2);
    }

    @Override
    public IAlgorithmParameters getParameters() {
        return parameters;
    }

    private <T> String CollectionToString(Collection<T> list) {
        StringBuilder res = new StringBuilder();
        for (T d : list)
            res.append(d).append("\t");
        return res.toString();
    }

    public void resizePopulation(int size) {
        mainLogger.info("resizePopulation " + size);
        if (size > currentColony.size()) {
            currentColony.addAnts(size - currentColony.size());
        } else {
            currentColony.removeAnts(currentColony.size() - size);
        }
        for (int i = 0; i < currentColony.size(); i++) {
            mainLogger.debug(currentColony.getAnt(i).toString());
        }
    }

    private <T> void printList(LoggerWrapper logger, List<T> values){
        StringBuilder builder = new StringBuilder();
        for (T val: values){
            builder.append(val);
            builder.append('\t');
        }
        logger.info(builder.toString());
    }

    public void makeStep() {

        currentColony.initialization();
        currentColony.buildPath(pheromoneMatrix, visibility);
        currentColony.calculateObjectiveFunction();

//        for (int i = 0; i < currentColony.size(); i++) {
//            mainLogger.debug(currentColony.getAnt(i).getSolution().toString());
//        }

        updateBest();

        currentColony.updateOperatorProbability(iterationMax.getValue());

        if (currentIteration != 0 &&
                (currentIteration % 10 == 0
                        || currentIteration == iterationMax.getValue() -1)){
            alphaLogger.info(CollectionToString(parameters.alpha.getProbabilities()));
            betaLogger.info(CollectionToString(parameters.beta.getProbabilities()));
        }
        mainLogger.info(totalBestSolution.getValue().toString());

        pheromoneMatrix.update(currentColony);
        mainLogger.debug("updateByBest");
        mainLogger.debug(totalBest.getSolution().toString());
        pheromoneMatrix.updateByBest(totalBest);
    }

    protected void updateBest() {
        currentBest = currentColony.findBest();
        currentBestSolution = currentBest.getSolution();

        if (totalBest.getSolution() == null) {
            totalBest = new AdaptiveAnt<>(currentBest);
            totalBestSolution = totalBest.getSolution();
        } else if (currentBest.compareTo(totalBest) > 0) {
            totalBest = new AdaptiveAnt<>(currentBest);
            totalBestSolution = totalBest.getSolution();
        }
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        alphaLogger.info("---------------------------------------------------------------");
        betaLogger.info("---------------------------------------------------------------");
        mainLogger.info("AdaptiveAntColonyOptimization");
        mainLogger.info(parameters.toString());

        visibility = createVisibility();

        totalBest = new AdaptiveAnt<>(problem, parameters);
        totalBest.setValue(problem.getWorst());

        parameters.alpha.initialize();
        parameters.beta.initialize();

        alphaLogger.info("Names");
        alphaLogger.info(CollectionToString(parameters.alpha.getNames()));
        betaLogger.info("Names");
        betaLogger.info(CollectionToString(parameters.beta.getNames()));

        currentColony = new AdaptiveColony<>(individualAmount.getValue(), problem, parameters);
        createPheromoneMatrix();
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");
        alphaLogger.info("------- NEW RUN -------");
        betaLogger.info("------- NEW RUN -------");
    }

    public int getPopulationSize() {
        return currentColony.size();
    }

    public R getBestResult() {
        return totalBest.getValue();
    }

    public Solution<Integer, R> getBest() {
        return totalBest.getSolution();
    }

    //region Migration
    @Override
    public Solution<Integer, R> getCurrentBest() {
        return currentBest.getSolution();
    }

    @Override
    public Solution<Integer, R> getRandom() {
        return currentColony.chooseRandomAnt().getSolution();
    }

    @Override
    public void replaceRandom(List<Solution<Integer, R>> solutions) {
        currentColony.replaceRandom(solutions);
        updateBest();
    }

    @Override
    public void replaceWorst(List<Solution<Integer, R>> solutions) {
        currentColony.replaceWorst(solutions);
        updateBest();
    }

    public List<Solution<Integer, R>> getPopulation(){
        return currentColony.getPopulation();
    }

    public void setPopulation(List<Solution<Integer, R>> solutions){
        currentColony.setPopulation(solutions);
        updateBest();
    }
    //endregion
}
