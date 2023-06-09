package scheduling.optimization.combinatorial.IWDs;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.combinatorial.PopulationBasedCombinatorialAlgorithm;
import scheduling.optimization.Solution;

import java.util.ArrayList;
import java.util.List;

public class IntelligentWaterDropsAlgorithm<R extends Number> extends PopulationBasedCombinatorialAlgorithm<R> {

    private IWDsParameters parameters;

    private SoilMatrix soilMatrix;
    protected WaterDrop<R> totalBest;
    protected WaterDrop<R> currentBest;
    protected Flow<R> currentFlow;
    protected List<List<Double>> visibility;

    public IntelligentWaterDropsAlgorithm(int iterationMax, int dropAmount, IWDsParameters parameters) {
        super("IWDs");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(dropAmount, "dropAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public IntelligentWaterDropsAlgorithm(int iterationMax, int dropAmount, IWDsParameters parameters, String name) {
        super(name);
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(dropAmount, "dropAmount");
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

    private List<List<Double>> createVisibility() {
        List<List<Double>> result = new ArrayList<>();
        if (problem.hasDistances()) {
            for (int i = 0; i < problem.getDimension(); i++) {
                List<Double> current = new ArrayList<>();
                for (int j = 0; j < problem.getDimension(); j++) {
                    current.add(1. / problem.getDistance(i, j));
                }
                result.add(current);
            }
        } else {
            for (int i = 0; i < problem.getDimension(); i++) {
                List<Double> current = new ArrayList<>();
                for (int j = 0; j < problem.getDimension(); j++) {
                    if (i == j) {
                        current.add(0.0);
                    } else {
                        current.add(1.0);
                    }
                }
                result.add(current);
            }
        }
        return result;
    }

    //region ICooperative

    public void makeStep() {
        if (currentIteration != 0)
            soilMatrix.updateByBest(currentBest);

        if (currentIteration != 0 && currentIteration % parameters.getN1() == 0)
            soilMatrix.restartByBest(totalBest);

        currentFlow.clear();// clear and choose first point

        for (int j = 0; j < problem.getDimension() - 1; j++) {   // choose remain n-1 city
            currentFlow.chooseNext(soilMatrix);
            currentFlow.updateVelocity(soilMatrix);
            currentFlow.updateSoil(soilMatrix, visibility);
        }
        currentFlow.calculateObjectiveFunction();

        updateBest();

        mainLogger.info(totalBestSolution.getValue().toString());
    }

    protected void updateBest() {
        currentBest = currentFlow.findBest();
        currentBestSolution = currentBest.getSolution();

        if (totalBest.getSolution() == null) {
            totalBest = new WaterDrop<>(currentBest);
            totalBestSolution = totalBest.getSolution();
        } else if (currentBest.compareTo(totalBest) > 0) {
            totalBest = new WaterDrop<>(currentBest);
            totalBestSolution = totalBest.getSolution();
        }
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("IntelligentWaterDropsAlgorithm");

        totalBest = new WaterDrop<>(problem, parameters);
        totalBest.setValue(problem.getWorst());
        currentFlow = new Flow<>(individualAmount.getValue(), problem, parameters);
        soilMatrix = new SoilMatrix(problem.getDimension(), parameters);
        visibility = createVisibility();
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");
    }

    public void resizePopulation(int size) {
        mainLogger.info("resizePopulation " + size);
        currentFlow.resizePopulation(size);
        for (int i = 0; i < currentFlow.size(); i++) {
            mainLogger.debug(currentFlow.getDrop(i).toString());
        }
    }

    public int getPopulationSize() {
        return currentFlow.size();
    }

    // TODO: WTF fitness for IWDs
    public R getBestFitness() {
        return totalBest.getValue();
    }

    //endregion

    //region Migration
    @Override
    public Solution<Integer, R> getCurrentBest() {
        return currentBest.getSolution();
    }

    @Override
    public Solution<Integer, R> getRandom() {
        return currentFlow.chooseRandom().getSolution();
    }

    @Override
    public void replaceRandom(List<Solution<Integer, R>> solutions) {
        currentFlow.replaceRandom(solutions, soilMatrix, visibility);
        updateBest();
    }

    @Override
    public void replaceWorst(List<Solution<Integer, R>> solutions) {
        currentFlow.replaceWorst(solutions, soilMatrix, visibility);
        updateBest();
    }

    public List<Solution<Integer, R>> getPopulation(){
        return currentFlow.getPopulation();
    }

    public void setPopulation(List<Solution<Integer, R>> solutions){
        currentFlow.setPopulation(solutions, soilMatrix, visibility);
        updateBest();
    }
    //endregion
}
