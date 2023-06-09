package scheduling.optimization.real.RGA.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.RGA.PopulationBase;
import scheduling.optimization.real.RGA.operators.SelectionType;
import scheduling.optimization.real.RGA.standard.operators.MutationSettings;
import scheduling.optimization.real.RGA.standard.operators.RecombinationSettings;
import scheduling.optimization.real.RGA.standard.operators.SelectionSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdaptivePopulation<R extends Number> extends PopulationBase<R, AdaptiveIndividual<R>, AdaptiveRGAParameters> {

    public AdaptivePopulation(ProblemReal<Double, R> problem, int individualsAmount,
                              AdaptiveRGAParameters parameters, LoggerWrapper logger) {
        super(problem, individualsAmount, parameters, logger);
    }

    public void updateOperatorProbability(int iterationMax){
        Map<SelectionSettings, Double> averageFitnessSelectionSettings = new HashMap<>();
        Map<SelectionSettings, Integer> countSelectionSettings = new HashMap<>();
        Map<RecombinationSettings, Double> averageFitnessRecombinationSettings = new HashMap<>();
        Map<RecombinationSettings, Integer> countRecombinationSettings= new HashMap<>();
        Map<MutationSettings, Double> averageFitnessMutationSettings = new HashMap<>();
        Map<MutationSettings, Integer> countMutationSettings = new HashMap<>();

        for (SelectionSettings a : parameters.selectionSettings.getNames()) {
            averageFitnessSelectionSettings.put(a, 0.);
            countSelectionSettings.put(a, 0);
        }
        for (RecombinationSettings b : parameters.recombinationSettings.getNames()) {
            averageFitnessRecombinationSettings.put(b, 0.);
            countRecombinationSettings.put(b, 0);
        }
        for (MutationSettings c : parameters.mutationSettings.getNames()) {
            averageFitnessMutationSettings.put(c, 0.);
            countMutationSettings.put(c, 0);
        }

        for (AdaptiveIndividual<R> individual: generation) {
            SelectionSettings selectionSettings = individual.selectionSettings;
            RecombinationSettings recombinationSettings = individual.recombinationSettings;
            MutationSettings mutationSettings = individual.mutationSettings;

            double val = averageFitnessSelectionSettings.get(selectionSettings);
            averageFitnessSelectionSettings.replace(selectionSettings, val + individual.getObjectiveFunctionValue().doubleValue());
            countSelectionSettings.replace(selectionSettings, countSelectionSettings.get(selectionSettings) + 1);

            val = averageFitnessRecombinationSettings.get(recombinationSettings);
            averageFitnessRecombinationSettings.replace(recombinationSettings, val + individual.getObjectiveFunctionValue().doubleValue());
            countRecombinationSettings.replace(recombinationSettings, countRecombinationSettings.get(recombinationSettings) + 1);

            val = averageFitnessMutationSettings.get(mutationSettings);
            averageFitnessMutationSettings.replace(mutationSettings, val + individual.getObjectiveFunctionValue().doubleValue());
            countMutationSettings.replace(mutationSettings, countMutationSettings.get(mutationSettings) + 1);
        }

        updateOperatorProbability(iterationMax, parameters.selectionSettings,
                averageFitnessSelectionSettings, countSelectionSettings);
        updateOperatorProbability(iterationMax, parameters.recombinationSettings,
                averageFitnessRecombinationSettings, countRecombinationSettings);
        updateOperatorProbability(iterationMax, parameters.mutationSettings,
                averageFitnessMutationSettings, countMutationSettings);
    }

    public <T> void updateOperatorProbability(int iterationMax, ParameterVariants<T> parameterVariants,
                                              Map<T, Double> averageFitnessMap, Map<T, Integer> countMap) {
        int n = parameterVariants.size();

        double minFit = Double.MAX_VALUE;
        T minFitKey = averageFitnessMap.keySet().stream().findFirst().get();

        for (Map.Entry<T, Double> entry : averageFitnessMap.entrySet()) {
            if (countMap.get(entry.getKey()) > 0) {
                double val = entry.getValue() / (double) countMap.get(entry.getKey());
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

    public void mutation() {
        logger.debug("Mutation");
        for (int i = 0; i < generation.size(); i++) {
            MutationSettings mutationSettings = parameters.chooseMutationSettings();
            AdaptiveIndividual<R> child = children.get(i);
            child.mutationSettings = mutationSettings;
            child.mutation(mutationSettings
                    .getFinalProbability(parameters.getBitNumberPerVariable(), problem.getDimension()));
        }
    }

    public void selectionAndRecombination() {
        logger.debug("selectionAndRecombination");
        List<Double> probabilitiesForProportionalSelection = createProbabilitiesForProportionalSelection();
        Map<SelectionSettings, List<Double>> probabilitiesForRankingSelection = new HashMap<>();
        for (SelectionSettings settings: parameters.selectionSettings.getNames()) {
            if (settings.getSelectionType() == SelectionType.RANKING){
                if (!probabilitiesForRankingSelection.containsKey(settings)){
                    probabilitiesForRankingSelection.put(settings, getRankProbabilities(settings));
                }
            }
        }
        children = new ArrayList<>();
        for (int i = 0; i < generation.size(); i++) {
            SelectionSettings selectionSettings = parameters.chooseSelectionSettings();
            AdaptiveIndividual<R> parent1 = selection(selectionSettings, probabilitiesForProportionalSelection,
                    probabilitiesForRankingSelection.get(selectionSettings));
            AdaptiveIndividual<R> parent2 = selection(selectionSettings, probabilitiesForProportionalSelection,
                    probabilitiesForRankingSelection.get(selectionSettings));
            RecombinationSettings recombinationSettings = parameters.chooseRecombinationSettings();
            AdaptiveIndividual<R> child = recombination(recombinationSettings, parent1, parent2);
            child.selectionSettings = selectionSettings;
            child.recombinationSettings = recombinationSettings;
            children.add(child);
        }
    }

    public AdaptiveIndividual<R> selection(SelectionSettings selectionSettings,
                                        List<Double> probabilitiesForProportionalSelection,
                                        List<Double> probabilitiesForRankingSelection) {
        AdaptiveIndividual<R> individual = null;
        switch (selectionSettings.getSelectionType()) {
            case PROPORTIONAL:
                individual = proportionalSelectionStep(probabilitiesForProportionalSelection);
                break;
            case RANKING:
                individual = rankingSelectionStep(probabilitiesForRankingSelection);
                break;
            case TOURNAMENT:
                individual = tournamentSelectionStep(selectionSettings);
                break;
        }
        return individual;
    }

    private List<Double> createProbabilitiesForProportionalSelection(){
        double objectiveFunctionValueSum = 0;
        for (AdaptiveIndividual<R> individual : generation) {
            objectiveFunctionValueSum += individual.getObjectiveFunctionValue().doubleValue();
        }
        List<Double> probabilities = new ArrayList<>();
        probabilities.add(0.);
        for (int j = 0; j < generation.size(); j++) {
            probabilities.add(probabilities.get(j) +
                    generation.get(j).getObjectiveFunctionValue().doubleValue() / objectiveFunctionValueSum);
        }
        probabilities.set(probabilities.size() - 1, 1.);
        logger.debug("Probabilities:");
        logger.debug(probabilities.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return probabilities;
    }

    protected AdaptiveIndividual<R> recombination(RecombinationSettings recombinationSettings,
                                                  AdaptiveIndividual<R> parent1, AdaptiveIndividual<R> parent2) {
        logger.debug("Recombination");
        AdaptiveIndividual<R> child = null;
        switch (recombinationSettings.getRecombinationType()) {
            case UNIFORM:
                child = uniformRecombination(parent1, parent2);
                break;
            case ONEPOINT:
                child = onePointRecombination(parent1, parent2);
                break;
            case TWOPOINT:
                child = twoPointRecombination(parent1, parent2);
                break;
        }
        return child;
    }

    @Override
    protected AdaptiveIndividual<R> getNewIndividual(ProblemReal<Double, R> problem, int bitNumberPerVariable,
                                                  List<Integer> genotype, LoggerWrapper logger) {
        return new AdaptiveIndividual<R>(problem, bitNumberPerVariable, genotype, logger);
    }

    @Override
    public void addToGenerationNewIndividual(Solution<Double, R> solution) {
        generation.add(new AdaptiveIndividual<R>(problem, parameters.getBitNumberPerVariable(), solution, logger));
    }

    @Override
    public void addToGenerationNewIndividual() {
        AdaptiveIndividual<R> individual = new AdaptiveIndividual<>(problem, parameters.getBitNumberPerVariable(), logger);
        individual.calculateObjectiveFunction(problem);
        generation.add(individual);
    }
}
