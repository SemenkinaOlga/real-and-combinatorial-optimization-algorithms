package scheduling.optimization.combinatorial.GA;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.*;
import scheduling.optimization.combinatorial.IWDs.WaterDrop;
import scheduling.optimization.combinatorial.PopulationBasedCombinatorialAlgorithm;
import scheduling.optimization.combinatorial.ProblemCombinatorial;
import scheduling.optimization.combinatorial.GA.settings.*;
import scheduling.optimization.combinatorial.GA.util.*;
import scheduling.optimization.combinatorial.GA.operators.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scheduling.optimization.combinatorial.GA.util.OperatorType.*;
import static scheduling.optimization.combinatorial.GA.util.OperatorType.MUTATION;


public class GeneticAlgorithm<R extends Number> extends PopulationBasedCombinatorialAlgorithm<R> {

    //region Fields

    private static final int REPLACEMENT_PERCENT = 2;
    private LoggerWrapper selectionLogger;
    private LoggerWrapper recombinationLogger;
    private LoggerWrapper mutationLogger;

    private Population<R> population;
    protected Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators = new HashMap<>();

    private ProblemCombinatorial<Integer, R> problem;
    private int dimension;

    private GAParametersBase parameters;

    private Integer amountOfReplace;
    //endregion

    @Override
    public void setLoggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;
        this.mainLogger = loggers.get(0);
        this.selectionLogger = loggers.get(1);
        this.recombinationLogger = loggers.get(2);
        this.mutationLogger = loggers.get(3);
    }

    @Override
    public IAlgorithmParameters getParameters() {
        return parameters;
    }

    public GeneticAlgorithm(int iterationMax, int individualAmount, GAParametersBase parameters) {
        super("GA");
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "generationAmount");
        this.individualAmount = new Parameter<>(individualAmount, "individualAmount");
    }

    public GeneticAlgorithm(int iterationMax, int individualAmount, GAParametersBase parameters, String name) {
        super(name);
        this.parameters = parameters;
        this.iterationMax = new Parameter<>(iterationMax, "generationAmount");
        this.individualAmount = new Parameter<>(individualAmount, "individualAmount");
    }

    public void initOperatorSettings(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators,
                                     GAParametersBase parameters,
                                     Problem<Integer, R> problem) {
        mainLogger.debug("Инициализация настроек операторов. Начало");
        OperatorFactory operatorFactory = new OperatorFactory();

        for (OperatorType operatorType : OperatorType.values()) {
            operators.put(operatorType, new HashMap<>());
        }

        if (parameters instanceof GAParameters) {
            GAParameters gaParameters = (GAParameters) parameters;

            // Selection
            SelectionSettings selectionSettings = gaParameters.getSelectionSettings();
            Operator<R> operatorSelection = operatorFactory.createOperator(selectionSettings, problem);
            operators.get(selectionSettings.getOperatorType()).put(selectionSettings, operatorSelection);

            // Recombination
            RecombinationSettings recombinationSettings = gaParameters.getRecombinationSettings();
            Operator<R> operatorRecombination = operatorFactory.createOperator(recombinationSettings, problem);
            operators.get(recombinationSettings.getOperatorType()).put(recombinationSettings, operatorRecombination);

            // Mutation
            MutationSettings mutationSettings = gaParameters.getMutationSettings();
            Operator<R> operatorMutation = operatorFactory.createOperator(mutationSettings, problem);
            operators.get(mutationSettings.getOperatorType()).put(mutationSettings, operatorMutation);
        } else {
            AdaptiveGAParameters adaptiveGAParameters = (AdaptiveGAParameters) parameters;

            if (adaptiveGAParameters.getSelfConfiguringType() == SelfConfiguringType.TYPICAL) {
                for (SelectionSettings selectionSettings : adaptiveGAParameters.getSelections()) {
                    Operator<R> operatorSelection = operatorFactory.createOperator(selectionSettings, problem);
                    operators.get(selectionSettings.getOperatorType()).put(selectionSettings, operatorSelection);
                }

                for (RecombinationSettings recombinationSettings : adaptiveGAParameters.getRecombinations()) {
                    Operator<R> operatorRecombination = operatorFactory.createOperator(recombinationSettings, problem);
                    operators.get(recombinationSettings.getOperatorType()).put(recombinationSettings, operatorRecombination);
                }

                for (MutationSettings mutationSettings : adaptiveGAParameters.getMutations()) {
                    Operator<R> operatorMutation = operatorFactory.createOperator(mutationSettings, problem);
                    operators.get(mutationSettings.getOperatorType()).put(mutationSettings, operatorMutation);
                }
            }

        }

        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            setOperatorsInitialProbabilities(entry.getValue());
            setOperatorsDistribution(entry.getValue());
        }

        mainLogger.debug("Инициализация настроек операторов. Окончание");
    }

    public void setOperatorsDistribution(Map<OperatorSettings, Operator<R>> operators) {
        mainLogger.debug("Задание распределения операторов. Начало");
        Double distribution = 0.0;
        for (Map.Entry<OperatorSettings, Operator<R>> entry : operators.entrySet()) {
            distribution += entry.getValue().getProbability();
            entry.getValue().setDistribution(distribution);
        }
        mainLogger.debug("Задание распределения операторов. Окончание");
    }

    public void setOperatorsInitialProbabilities(Map<OperatorSettings, Operator<R>> operators) {
        mainLogger.debug("Задание изначальных вероятностей операторов. Начало");
        double probability = 1.0 / operators.size();
        mainLogger.debug("Вероятность: " + probability);
        for (Map.Entry<OperatorSettings, Operator<R>> entry : operators.entrySet()) {
            entry.getValue().setProbability(probability);
        }
        mainLogger.debug("Задание изначальных вероятностей операторов. Окончание");
    }

    public void initOperatorSettings() {
        initOperatorSettings(operators, parameters, problem);
        writeProbabilities(true);
    }

    private void writeProbabilities(){
        writeProbabilities(false);
    }

    private void writeProbabilities(boolean writeNames) {
        StringBuilder sbRecombinations = new StringBuilder();
        StringBuilder sbRecombinationNames = new StringBuilder();
        StringBuilder sbSelections = new StringBuilder();
        StringBuilder sbSelectionNames = new StringBuilder();
        StringBuilder sbMutations = new StringBuilder();
        StringBuilder sbMutationNames = new StringBuilder();

        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            for (Map.Entry<OperatorSettings, Operator<R>> operator : entry.getValue().entrySet()) {
                OperatorType operatorType = operator.getKey().getOperatorType();
                StringBuilder sb = null;
                StringBuilder sbName = null;
                if (operatorType == RECOMBINATION) {
                    sb = sbRecombinations;
                    sbName = sbRecombinationNames;
                } else if (operatorType == SELECTION) {
                    sb = sbSelections;
                    sbName = sbSelectionNames;
                } else if (operatorType == MUTATION) {
                    sb = sbMutations;
                    sbName = sbMutationNames;
                }
                if (sb.length() > 0) {
                    sb.append("\t");
                }
                sb.append(operator.getValue().getProbability());

                if(writeNames) {
                    if (sbName.length() > 0) {
                        sbName.append("\t");
                    }
                    sbName.append(operator.toString());
                }
            }
        }

        if(writeNames) {
            selectionLogger.info(sbSelectionNames.toString());
            recombinationLogger.info(sbRecombinationNames.toString());
            mutationLogger.info(sbMutationNames.toString());
        }
        selectionLogger.info(sbSelections.toString());
        recombinationLogger.info(sbRecombinations.toString());
        mutationLogger.info(sbMutations.toString());
    }

    public List<Solution<Integer, R>> getPopulation(){
        return population.getPopulation();
    }

    public void setPopulation(List<Solution<Integer, R>> solutions){
        population.setPopulation(solutions);
        updateBest();
    }

    public void setOperators(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators) {
        this.operators = operators;
    }

    //region ICooperative
    public void makeStep(){
        mainLogger.debug("makeStep");
        for (OperatorType operatorType : OperatorType.values()) {
            mainLogger.debug("operatorType " + operatorType.name());
            population.applyOperator(operatorType, operators);
        }
        mainLogger.debug("calcFitness");
        population.calcFitness();
        mainLogger.debug("findBest");
        population.findBest();
        population.calcOperatorsFitness(operators);
        population.configureOperators(operators, iterationMax.getValue());
        if (parameters.isUseElitism()) {
            population.rememberGeneration(amountOfReplace);
        }

        updateBest();
        mainLogger.debug("totalBestSolution");
        mainLogger.debug(totalBestSolution.toString());

        if (currentIteration != 0 &&
                (currentIteration % 10 == 0
                        || currentIteration == iterationMax.getValue() -1)){
            writeProbabilities();
        }

        mainLogger.info(totalBestSolution.getValue().toString());
    }

    protected void updateBest() {
        currentBestSolution = population.getBestIndividual().getSolution();
        totalBestSolution = population.getTotalBestIndividual().getSolution();
    }

    public void setTotalIterationAmount(int iterationMax){
        this.iterationMax = new Parameter<>(iterationMax, "generationAmount");
    }

    public void resizePopulation(int size){
        mainLogger.info("resizePopulation " + size);
        population.resizePopulation(size);
        List<Solution<Integer, R>> pop = population.getPopulation();
        for (int i = 0; i < pop.size(); i++) {
            mainLogger.debug(pop.get(i).toString());
        }
    }

    public void setInitialIndividualAmount(int individualAmount){
        this.individualAmount = new Parameter<>(individualAmount, "individualAmount");
    }

    public void initialization() {
        mainLogger.info("---------------------------------------------------------------");
        selectionLogger.info("---------------------------------------------------------------");
        recombinationLogger.info("---------------------------------------------------------------");
        mutationLogger.info("---------------------------------------------------------------");
        mainLogger.info("GeneticAlgorithm");
        mainLogger.info(parameters.toString());

        population = new Population<R>(problem, individualAmount.getValue(), mainLogger);
        amountOfReplace = (int) Math.ceil(individualAmount.getValue() * REPLACEMENT_PERCENT / 100d);
        currentIteration = 0;

        mainLogger.info("------- NEW RUN -------");
        selectionLogger.info("------- NEW RUN -------");
        recombinationLogger.info("------- NEW RUN -------");
        mutationLogger.info("------- NEW RUN -------");
        initOperatorSettings();
    }

    public int getPopulationSize(){
        return population.getIndividualsAmount();
    }

    @Override
    public void setProblem(Problem<Integer, R> problem){
        this.problem = (ProblemCombinatorial<Integer, R>) problem;
        dimension = problem.getDimension();
        mainLogger.info("set problem, dimension = " + dimension);
    }

    public R getBestResult(){
        return population.getBestIndividual().getSolution().getValue();
    }

    public double getBestFitness(){
        return population.getBestIndividual().getFitness();
    }

    //endregion

    //region Migration
    @Override
    public Solution<Integer, R> getCurrentBest() {
        return  population.getBestIndividual().getSolution();
    }

    @Override
    public Solution<Integer, R> getRandom() {
        return population.getRandomIndividual().getSolution();
    }

    @Override
    public void replaceRandom(List<Solution<Integer, R>> solutions) {
        population.replaceRandom(solutions);
        updateBest();
    }

    @Override
    public void replaceWorst(List<Solution<Integer, R>> solutions) {
        population.replaceWorst(solutions);
        updateBest();
    }
    //endregion
}
