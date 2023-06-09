package scheduling.optimization.real.DE.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.DEType;
import scheduling.optimization.real.DE.PopulationBase;
import scheduling.optimization.real.ProblemReal;

import java.util.*;

public class AdaptivePopulation<R extends Number> extends PopulationBase<R, AdaptiveIndividual<R>> {

    public AdaptiveDEParameters parameters;

    public AdaptivePopulation(ProblemReal<Double, R> problem, int individualsAmount, LoggerWrapper logger) {
        super(problem, individualsAmount, logger);

        logger.debug("Ининициализация популяции");
        for (int i = 0; i < individualsAmount; i++) {
            generation.add(new AdaptiveIndividual<R>(problem, logger));
        }
        calculateObjectiveFunction();
        findBest();
        logger.debug("Ининициализация популяции. Окончание");
    }

    @Override
    public void addToGenerationNewIndividual(Solution<Double, R> solution) {
        generation.add(new AdaptiveIndividual<R>(problem, solution, logger));
    }

    @Override
    public void addToGenerationNewIndividual() {
        AdaptiveIndividual<R> individual = new AdaptiveIndividual<>(problem, logger);
        individual.calculateObjectiveFunction(problem);
        generation.add(individual);
    }

    public void addIndividualToNewGeneration(AdaptiveIndividual<R> individual){
        newGeneration.add(individual);
    }

    public void updateOperatorProbability(int iterationMax){
        Map<Double, Double> averageFitnessCR = new HashMap<>();
        Map<Double, Integer> countCR = new HashMap<>();
        Map<Double, Double> averageFitnessFF = new HashMap<>();
        Map<Double, Integer> countFF = new HashMap<>();
        Map<Double, Double> averageFitnessLymbda = new HashMap<>();
        Map<Double, Integer> countLymbda = new HashMap<>();
        Map<DEType, Double> averageFitnessDEType = new HashMap<>();
        Map<DEType, Integer> countDEType = new HashMap<>();

        for (double a : parameters.CR.getNames()) {
            averageFitnessCR.put(a, 0.);
            countCR.put(a, 0);
        }
        for (double b : parameters.FF.getNames()) {
            averageFitnessFF.put(b, 0.);
            countFF.put(b, 0);
        }
        for (double b : parameters.lymbda.getNames()) {
            averageFitnessLymbda.put(b, 0.);
            countLymbda.put(b, 0);
        }
        for (DEType b : parameters.deType.getNames()) {
            averageFitnessDEType.put(b, 0.);
            countDEType.put(b, 0);
        }

        for (AdaptiveIndividual<R> individual: generation) {
            double CR = individual.getCR();
            double FF = individual.getFF();
            double lymbda = individual.getLymbda();
            DEType DEType = individual.getDeType();

            double val = averageFitnessCR.get(CR);
            averageFitnessCR.replace(CR, val + individual.getObjectiveFunctionValue().doubleValue());
            countCR.replace(CR, countCR.get(CR) + 1);

            val = averageFitnessFF.get(FF);
            averageFitnessFF.replace(FF, val + individual.getObjectiveFunctionValue().doubleValue());
            countFF.replace(FF, countFF.get(FF) + 1);

            val = averageFitnessDEType.get(DEType);
            averageFitnessDEType.replace(DEType, val + individual.getObjectiveFunctionValue().doubleValue());
            countDEType.replace(DEType, countDEType.get(DEType) + 1);

            val = averageFitnessLymbda.get(lymbda);
            averageFitnessLymbda.replace(lymbda, val + individual.getObjectiveFunctionValue().doubleValue());
            countLymbda.replace(lymbda, countLymbda.get(lymbda) + 1);
        }

        updateOperatorProbability(iterationMax, parameters.CR,
                averageFitnessCR, countCR);
        updateOperatorProbability(iterationMax, parameters.FF,
                averageFitnessFF, countFF);
        updateOperatorProbability(iterationMax, parameters.lymbda,
                averageFitnessLymbda, countLymbda);
        updateOperatorProbability(iterationMax, parameters.deType,
                averageFitnessDEType, countDEType);
    }

    public <T> void updateOperatorProbability(int iterationMax, ParameterVariants<T> parameterVariants,
                                          Map<T, Double> averageFitnessMap, Map<T, Integer> countMap) {
        int n = parameterVariants.size();

        double minFit = Double.MAX_VALUE;
        T minFitKey = averageFitnessMap.keySet().stream().findFirst().get();

        for (Map.Entry<T, Double> entry : averageFitnessMap.entrySet()) {
            if (countMap.get(entry.getKey()) > 0) {
                double val = (double) entry.getValue() / (double) countMap.get(entry.getKey());
                entry.setValue(val);
                if (val < minFit) {
                    minFit = val;
                    minFitKey = entry.getKey();
                }
            }
        }

        int K = 2;
        Map<T, Double> probabilitiesMap = parameterVariants.getProbabilitiesMap();

        for (Map.Entry<T, Double> entry : averageFitnessMap.entrySet()) {
            T key = entry.getKey();
            if (key != minFitKey) {
                double tmp = Math.min(probabilitiesMap.get(key) - parameters.minProbability, (double) ((n - 1) * K) /
                        (double) (n * iterationMax));
                probabilitiesMap.replace(minFitKey, probabilitiesMap.get(minFitKey) + tmp);
                probabilitiesMap.replace(key, probabilitiesMap.get(key) - tmp);
            }
        }
    }

}
