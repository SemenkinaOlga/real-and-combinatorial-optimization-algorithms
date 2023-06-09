package scheduling.optimization.combinatorial.ACO.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.AntColonyOptimizationBase;
import scheduling.optimization.combinatorial.IWDs.WaterDrop;

import java.util.List;

public class AntColonyOptimization<R extends Number> extends AntColonyOptimizationBase<R, Ant<R>> {

    private ACOParameters parameters;
    private Ant<R> currentBest;
    private Ant<R> totalBest;
    private Colony<R> currentColony;

    public AntColonyOptimization(int objectiveFunctionCalculation, int antAmount, ACOParameters parameters) {
        super(objectiveFunctionCalculation, antAmount, parameters, "ACO");
        this.parameters = parameters;
    }

    public AntColonyOptimization(int objectiveFunctionCalculation, int antAmount, ACOParameters parameters, String name) {
        super(objectiveFunctionCalculation, antAmount, parameters, name);
        this.parameters = parameters;
    }

    @Override
    public void setLoggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;
        this.mainLogger = loggers.get(0);
        mainLogger.info(parameters.toString());
    }

    @Override
    public IAlgorithmParameters getParameters() {
        return parameters;
    }

    public void makeStep() {
        if (currentIteration != 0) {
            pheromoneMatrix.update(currentColony);
            pheromoneMatrix.updateByBest(currentBest);
        }

        currentColony.initialization();
        currentColony.buildPath(pheromoneMatrix, visibility);
        currentColony.calculateObjectiveFunction();

        updateBest();

        mainLogger.info(totalBestSolution.getValue().toString());
    }

    protected void updateBest() {
        currentBest = currentColony.findBest();
        currentBestSolution = currentBest.getSolution();

        if (totalBest.getSolution() == null) {
            totalBest = currentBest;
            totalBestSolution = totalBest.getSolution();
        } else if (currentBest.compareTo(totalBest) > 0) {
            totalBest = currentBest;
            totalBestSolution = totalBest.getSolution();
        }
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

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("AntColonyOptimization");

        visibility = createVisibility();
        totalBest = new Ant<>(problem, parameters);
        totalBest.setValue(problem.getWorst());
        currentColony = new Colony<>(individualAmount.getValue(), problem, parameters);
        createPheromoneMatrix();
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");
    }

    public int getPopulationSize() {
        return currentColony.size();
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
