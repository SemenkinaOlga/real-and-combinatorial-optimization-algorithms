package scheduling.optimization.real.DE.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.DEType;
import scheduling.optimization.real.PopulationBasedRealAlgorithm;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class DifferentialEvolutionAlgorithm<R extends Number> extends PopulationBasedRealAlgorithm<R> {

    private DEParameters parameters;

    protected Individual<R> totalBest;
    protected Individual<R> currentBest;
    protected Population<R> population;

    public DifferentialEvolutionAlgorithm(int iterationMax, int individualsAmount,
                                          DEParameters parameters) {
        super("DE");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public DifferentialEvolutionAlgorithm(int iterationMax, int individualsAmount,
                                          DEParameters parameters, String name) {
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
        for (int i = 0; i < population.getSize(); i++) {
            mainLogger.debug("i = " + i);
            Individual<R> individual_i = population.getIndividual(i);
            List<Double> v = generateV(i);

            int k =  RandomUtils.random.nextInt(dimension);
            List<Double> u = new ArrayList<>();

            for (int j = 0; j < dimension; j++){
                if (RandomUtils.random.nextDouble() <= parameters.getCR() || j == k){
                    u.add(v.get(j));
                }
                else{
                    u.add(individual_i.getVector().get(j));
                }
            }
            mainLogger.debug("k = " + k);
            mainLogger.debug("u");
            printList(u);

            Individual<R> individual_u = new Individual<>(individual_i);
            individual_u.setVector(u);
            individual_u.calculateObjectiveFunction(problem);

            if (individual_u.compareTo(individual_i) > 0){
                mainLogger.debug(individual_i.getObjectiveFunctionValue() + " is better than " + individual_u.getObjectiveFunctionValue());
                population.addIndividualToNewGeneration(individual_u);
            }
            else {
                mainLogger.debug(individual_u.getObjectiveFunctionValue() + " is better than " + individual_i.getObjectiveFunctionValue());
                population.addIndividualToNewGeneration(individual_i);
            }
        }

        population.setupPopulation();

        updateBest();

        //mainLogger.info(totalBestSolution.getValue().toString());
    }

    protected void updateBest() {
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

    private List<Double> generateV(int i){
        mainLogger.debug("generateV");
        List<Double> v = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        indexes.add(i);

        int r1 = RandomUtils.getRandomIndexExclude(indexes, population.getSize());
        mainLogger.debug("r1 " + r1);
        indexes.add(r1);
        Individual<R> individual_r1 = population.getIndividual(r1);
        printList(individual_r1.getVector());

        int r2 = RandomUtils.getRandomIndexExclude(indexes, population.getSize());
        mainLogger.debug("r1 " + r1);
        indexes.add(r2);
        Individual<R> individual_r2 = population.getIndividual(r2);
        printList(individual_r2.getVector());

        if (parameters.getDEType() == DEType.BY_RANDOM) {
            mainLogger.debug("BY_RANDOM");
            int r3 = RandomUtils.getRandomIndexExclude(indexes, population.getSize());
            mainLogger.debug("r3 " + r3);

            Individual<R> individual_r3 = population.getIndividual(r3);
            printList(individual_r3.getVector());
            double tmp = 0;

            for (int j = 0; j < dimension; j++) {
                tmp = (individual_r1.getVector().get(j)) + parameters.getFF() *
                        (individual_r2.getVector().get(j) - individual_r3.getVector().get(j));
                if (tmp < problem.getLeftBound()) tmp = problem.getLeftBound();
                if (tmp > problem.getRightBound()) tmp = problem.getRightBound();
                v.add(tmp);
            }
            mainLogger.debug("v");
            printList(v);

            return v;
        }
        else {
            Individual<R> individual_i = population.getIndividual(i);
            mainLogger.debug("i = " + i);
            printList(individual_i.getVector());
            double tmp = 0;

            for (int j = 0; j < dimension; j++) {
                tmp = individual_i.getVector().get(j) +
                        parameters.getLymbda() * (totalBest.getVector().get(j) - individual_i.getVector().get(j)) +
                        parameters.getFF() *  (individual_r1.getVector().get(j) - individual_r2.getVector().get(j));
                if (tmp < problem.getLeftBound()) tmp = problem.getLeftBound();
                if (tmp > problem.getRightBound()) tmp = problem.getRightBound();
                v.add(tmp);
            }
            mainLogger.debug("v");
            printList(v);

            return v;
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
        mainLogger.info("Differential Algorithm");

        totalBest = new Individual<>(problem, mainLogger);
        totalBest.setObjectiveFunctionValue(problem.getWorst());
        population = new Population<>(problem, individualAmount.getValue(), mainLogger);
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