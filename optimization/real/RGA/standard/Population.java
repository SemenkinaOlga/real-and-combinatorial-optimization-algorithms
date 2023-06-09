package scheduling.optimization.real.RGA.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.RGA.PopulationBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Population<R extends Number> extends PopulationBase<R, Individual<R>, RGAParameters> {

    public Population(ProblemReal<Double, R> problem, int individualsAmount, RGAParameters parameters,
                      LoggerWrapper logger) {
        super(problem, individualsAmount, parameters, logger);
    }

    public void mutation() {
        logger.debug("Mutation");
        for (int i = 0; i < children.size(); i++)
            children.get(i).mutation(parameters.getMutationSettings()
                    .getFinalProbability(parameters.getBitNumberPerVariable(), problem.getDimension()));
    }

    public void selection() {
        logger.debug("Selection");
        switch (parameters.getSelectionSettings().getSelectionType()) {
            case PROPORTIONAL:
                proportionalSelection();
                break;
            case RANKING:
                rankingSelection();
                break;
            case TOURNAMENT:
                tournamentSelection();
                break;
        }
    }

    protected void tournamentSelection() {
        logger.debug("Tournament selection");
        logger.debug("Population:");
        logger.debug(toString());
        parents = new ArrayList<>();
        for (int i = 0; i < generation.size(); i++) {
            parents.add(tournamentSelectionStep(parameters.getSelectionSettings()));
            parents.add(tournamentSelectionStep(parameters.getSelectionSettings()));
        }
    }

    protected void rankingSelection() {
        logger.debug("Ranking selection");
        logger.debug("Population:");
        logger.debug(toString());
        parents = new ArrayList<>();

        List<Double> probabilities = getRankProbabilities(parameters.getSelectionSettings());

        for (int j = 0; j < generation.size(); j++) {
            parents.add(rankingSelectionStep(probabilities));
            parents.add(rankingSelectionStep(probabilities));
        }
    }

    protected void proportionalSelection() {
        logger.debug("Proportional selection");
        parents = new ArrayList<>();

        double objectiveFunctionValueSum = 0;
        for (Individual<R> individual : generation) {
            objectiveFunctionValueSum += (double)individual.getObjectiveFunctionValue();
        }
        List<Double> probabilities = new ArrayList<>();
        probabilities.add(0.);
        for (int j = 0; j < generation.size(); j++) {
            probabilities.add(probabilities.get(j) +
                    (double)generation.get(j).getObjectiveFunctionValue() / objectiveFunctionValueSum);
        }
        probabilities.set(probabilities.size() - 1, 1.);
        logger.debug("Probabilities:");
        logger.debug(probabilities.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        for (int j = 0; j < generation.size(); j++) {
            parents.add(proportionalSelectionStep(probabilities));
            parents.add(proportionalSelectionStep(probabilities));
        }
    }

    protected void recombination() {
        logger.debug("Recombination");
        children = new ArrayList<>();
        switch (parameters.getRecombinationSettings().getRecombinationType()) {
            case UNIFORM:
                for (int h = 0; h < parents.size(); h += 2) {
                    children.add(uniformRecombination(parents.get(h), parents.get(h+1)));
                }
                break;
            case ONEPOINT:
                for (int h = 0; h < parents.size(); h += 2) {
                    children.add(onePointRecombination(parents.get(h), parents.get(h+1)));
                }
                break;
            case TWOPOINT:
                for (int h = 0; h < parents.size(); h += 2) {
                    children.add(twoPointRecombination(parents.get(h), parents.get(h+1)));
                }
                break;
        }
    }

    @Override
    protected Individual<R> getNewIndividual(ProblemReal<Double, R> problem, int bitNumberPerVariable, List<Integer> genotype, LoggerWrapper logger) {
        return new Individual<>(problem, bitNumberPerVariable, genotype, logger);
    }

    @Override
    public void addToGenerationNewIndividual() {
        Individual<R> individual = new Individual<>(problem, parameters.getBitNumberPerVariable(), logger);
        individual.calculateObjectiveFunction(problem);
        generation.add(individual);
    }

    @Override
    public void addToGenerationNewIndividual(Solution<Double, R> solution) {
        generation.add(new Individual<>(problem, parameters.getBitNumberPerVariable(), solution, logger));
    }
}
