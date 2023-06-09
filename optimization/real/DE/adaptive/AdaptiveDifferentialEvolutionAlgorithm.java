package scheduling.optimization.real.DE.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.Parameter;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.adaptive.AdaptiveAnt;
import scheduling.optimization.real.DE.DEType;
import scheduling.optimization.real.DE.standard.DEParameters;
import scheduling.optimization.real.DE.standard.Individual;
import scheduling.optimization.real.DE.standard.Population;
import scheduling.optimization.real.PopulationBasedRealAlgorithm;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptiveDifferentialEvolutionAlgorithm<R extends Number> extends PopulationBasedRealAlgorithm<R> {

    private AdaptiveDEParameters parameters;

    protected LoggerWrapper CRlogger;
    protected LoggerWrapper FFlogger;
    protected LoggerWrapper deTypelogger;
    protected LoggerWrapper lymbdalogger;

    protected AdaptiveIndividual<R> totalBest;
    protected AdaptiveIndividual<R> currentBest;
    protected AdaptivePopulation<R> population;

    public AdaptiveDifferentialEvolutionAlgorithm(int iterationMax, int individualsAmount,
                                                  AdaptiveDEParameters parameters) {
        super("DE");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(individualsAmount, "individualsAmount");
        this.totalBest = null;
        this.currentBest = null;
    }

    public AdaptiveDifferentialEvolutionAlgorithm(int iterationMax, int individualsAmount,
                                                  AdaptiveDEParameters parameters, String name) {
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

        CRlogger = loggers.get(1);
        FFlogger = loggers.get(2);
        deTypelogger = loggers.get(3);
        lymbdalogger = loggers.get(4);
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
            DEParameters params = new DEParameters();
            AdaptiveIndividual<R> individual_i = population.getIndividual(i);
            mainLogger.debug("generateV for ");
            mainLogger.debug(individual_i.toString());
            List<Double> v = generateV(i, params);
            mainLogger.debug("v");
            printList(v);

            int k =  RandomUtils.random.nextInt(dimension);
            List<Double> u = new ArrayList<>();

            double cr = parameters.chooseCR();
            params.setCR(cr);

            for (int j = 0; j < dimension; j++){
                if (RandomUtils.random.nextDouble() <= cr || j == k){
                    u.add(v.get(j));
                }
                else{
                    u.add(individual_i.getVector().get(j));
                }
            }
            mainLogger.debug("u");
            printList(u);

            AdaptiveIndividual<R> individual_u = new AdaptiveIndividual<>(individual_i);
            individual_u.setVector(u);
            individual_u.setCR(params.getCR());
            individual_u.setFF(params.getFF());
            individual_u.setLymbda(params.getLymbda());
            individual_u.setDeType(params.getDEType());
            individual_u.calculateObjectiveFunction(problem);

            if (individual_u.compareTo(individual_i) > 0){
                population.addIndividualToNewGeneration(individual_u);
            }
            else {
                population.addIndividualToNewGeneration(individual_i);
            }
        }

        population.setupPopulation();
        mainLogger.debug("updateOperatorProbability");
        population.updateOperatorProbability(iterationMax.getValue());
        printAllParametersValues();

        updateBest();
        mainLogger.debug("POPULATION:");
        for (Solution <Double, R> solution : population.getPopulation()){
            mainLogger.debug(solution.toString());
        }

        mainLogger.debug("finish");
        //mainLogger.info(totalBestSolution.getValue().toString());
    }

    protected void updateBest() {
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

    private <T> void printList(List<T> list){
        StringBuilder sb =new StringBuilder();
        for (T d : list) {
            sb.append(d);
            sb.append(" ");
        }
        mainLogger.debug(sb.toString());
    }

    private List<Double> generateV(int i, DEParameters params){
        List<Double> v = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        indexes.add(i);

        int r1 = RandomUtils.getRandomIndexExclude(indexes, population.getSize());
        indexes.add(r1);
        AdaptiveIndividual<R> individual_r1 = population.getIndividual(r1);
        mainLogger.debug("r1 = " + r1);

        int r2 = RandomUtils.getRandomIndexExclude(indexes, population.getSize());
        indexes.add(r2);
        AdaptiveIndividual<R> individual_r2 = population.getIndividual(r2);
        mainLogger.debug("r2 = " + r2);

        double ff = parameters.chooseFF();
        params.setFF(ff);
        mainLogger.debug("ff = " + ff);

        if (parameters.chooseDeType() == DEType.BY_RANDOM) {
            mainLogger.debug("BY_RANDOM");
            mainLogger.debug("population.getSize() = " + population.getSize());
            mainLogger.debug("indexes");
            printList(indexes);
            int r3 = RandomUtils.getRandomIndexExclude(indexes, population.getSize());
            mainLogger.debug("r3 = " + r3);

            AdaptiveIndividual<R> individual_r3 = population.getIndividual(r3);
            double tmp = 0;

            for (int j = 0; j < dimension; j++) {
                tmp = individual_r1.getVector().get(j) + ff *
                        (individual_r2.getVector().get(j) - individual_r3.getVector().get(j));
                if (tmp < problem.getLeftBound()) tmp = problem.getLeftBound();
                if (tmp > problem.getRightBound()) tmp = problem.getRightBound();
                v.add(tmp);
            }

            return v;
        }
        else {
            mainLogger.debug("BY_BEST");
            AdaptiveIndividual<R> individual_i = population.getIndividual(i);

            double lymbda = parameters.chooseLymbda();
            params.setLymbda(lymbda);
            mainLogger.debug("lymbda = " + lymbda);
            double tmp = 0;

            for (int j = 0; j < dimension; j++) {
                tmp = individual_i.getVector().get(j) +
                        lymbda * (totalBest.getVector().get(j) - individual_i.getVector().get(j)) +
                        ff *  (individual_r1.getVector().get(j) - individual_r2.getVector().get(j));
                if (tmp < problem.getLeftBound()) tmp = problem.getLeftBound();
                if (tmp > problem.getRightBound()) tmp = problem.getRightBound();
                v.add(tmp);
            }

            return v;
        }
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("Differential Algorithm");

        parameters.CR.initialize();
        parameters.FF.initialize();
        parameters.deType.initialize();
        parameters.lymbda.initialize();

        totalBest = new AdaptiveIndividual<>(problem, mainLogger);
        totalBest.setObjectiveFunctionValue(problem.getWorst());
        population = new AdaptivePopulation<>(problem, individualAmount.getValue(), mainLogger);
        population.parameters = parameters;
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");

        printParametersName(CRlogger, parameters.CR);
        printParametersName(FFlogger, parameters.FF);
        printParametersName(deTypelogger, parameters.deType);
        printParametersName(lymbdalogger, parameters.lymbda);
        printAllParametersValues();
    }

    private void printAllParametersValues(){
        printParametersValues(CRlogger, parameters.CR);
        printParametersValues(FFlogger, parameters.FF);
        printParametersValues(deTypelogger, parameters.deType);
        printParametersValues(lymbdalogger, parameters.lymbda);
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