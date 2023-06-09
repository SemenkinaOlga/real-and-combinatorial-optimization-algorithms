package scheduling.optimization.real.RGA;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.PopulationRoot;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.RGA.operators.RankingSelectionType;
import scheduling.optimization.real.RGA.standard.operators.SelectionSettings;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PopulationBase<R extends Number, Ind extends IndividualBase<R>, Params extends RGAParametersBase>
        extends PopulationRoot<Double, R, ProblemReal<Double, R>, Ind> {

    protected List<Ind> children;
    protected List<Ind> parents;
    protected Params parameters;

    public PopulationBase(ProblemReal<Double, R> problem, int individualsAmount,
                          Params parameters, LoggerWrapper logger) {
        super(problem, individualsAmount, logger);
        this.parameters = parameters;

        logger.debug("Ининициализация популяции");
        generation = new ArrayList<>();
        for (int i = 0; i < individualsAmount; i++) {
            addToGenerationNewIndividual();
        }
        children = new ArrayList<>();
        calculateObjectiveFunction();
        findBest();
        logger.debug("Ининициализация популяции. Окончание");
    }

    public abstract void mutation();

    protected abstract Ind getNewIndividual(ProblemReal<Double, R> problem, int bitNumberPerVariable,
                                            List<Integer> genotype, LoggerWrapper logger);

    public void setupPopulation() {
        logger.debug("setupPopulation");
        if (children != null) {
            generation = children;
            children = new ArrayList<>();
        } else {
            logger.error("newGeneration is empty for DE");
        }
    }

    public void addIndividualToNewGeneration(Ind individual) {
        children.add(individual);
    }

    public Ind getIndividual(int i) {
        return generation.get(i);
    }

    public void createNewPopulation() {
        logger.debug("createNewPopulation");
        for (Ind individual : children) {
            individual.calculateObjectiveFunction(problem);
        }
        // Reverse order
        children.sort((o1, o2) -> o2.compareTo(o1));
        if (parameters.isUseElitism()) {
            logger.debug("UseElitism");
            double k = parameters.getElitismPercentage();
            int amount = (int) Math.floor(generation.size() * k / 100.);
            logger.debug("amount = " + amount);
            List<Ind> newGeneration = new ArrayList<>();
            for (int i = 0; i < amount; i++)
                newGeneration.add(generation.get(i));
            for (int i = 0; i < generation.size() - amount; i++)
                newGeneration.add(children.get(i));
            generation = newGeneration;
        } else {
            generation = children;
        }
    }

    protected Ind tournamentSelectionStep(SelectionSettings selectionSettings) {
        logger.debug("Tournament selection step");
        int tournamentSize = selectionSettings.getTournamentSize();
        if (selectionSettings.getTournamentSize() > generation.size()){
            logger.info("Tournament selection: tournament size is BIGGER than generation size!!!");
            logger.debug("TournamentSize = " + selectionSettings.getTournamentSize());
            logger.debug("generation.size = " + generation.size());
            tournamentSize = generation.size();
        }
        List<Integer> indexes = RandomUtils.getRandomIndexes(tournamentSize,
                generation.size());
        logger.debug("indexes:");
        logger.debug(indexes.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        List<Ind> tournament = new ArrayList<>();
        for (Integer index : indexes) {
            tournament.add(generation.get(index));
        }
        tournament.sort((o1, o2) -> o2.compareTo(o1));
        logger.debug("Tournament:");
        logger.debug(tournament.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return tournament.get(0);
    }

    protected List<Double> getRankProbabilities(SelectionSettings selectionSettings) {
        List<Double> ranks = new ArrayList<>();
        for (int i = 0; i < generation.size(); i++) {
            ranks.add(0.);
        }
        // линейное ранжирование
        if (selectionSettings.getRankingSelectionType() == RankingSelectionType.LINEAR) {
            double equalNum;
            for (int i = 0; i < generation.size(); i++) {
                equalNum = 0.;
                for (int j = i + 1; j < generation.size(); j++) {
                    if (generation.get(i).getObjectiveFunctionValue() ==
                            generation.get(j).getObjectiveFunctionValue())
                        equalNum++;
                    else break;
                }
                double r = 0.;
                for (int j = i; j < i + equalNum + 1; j++)
                    r += ranks.size() - j;
                r /= equalNum + 1.;
                for (int j = i; j < i + equalNum + 1; j++)
                    ranks.set(j, r);
                i += equalNum;
            }
        } else {    // экспоненциальное ранжирование
            ranks.set(0, 1.);
            for (int i = 1; i < generation.size(); i++) {
                if (generation.get(i).getObjectiveFunctionValue() ==
                        generation.get(i - 1).getObjectiveFunctionValue())
                    ranks.set(i, ranks.get(i - 1));
                else
                    ranks.set(i, ranks.get(i - 1) * selectionSettings.getWeight());
            }
        }
        logger.debug("Ranks:");
        logger.debug(ranks.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        double rankSum = 0;
        for (Double rank : ranks) rankSum += rank;
        List<Double> probabilities = new ArrayList<>();
        probabilities.add(0.);
        for (int j = 0; j < ranks.size(); j++)
            probabilities.add(probabilities.get(j) + ranks.get(j) / rankSum);
        probabilities.set(probabilities.size() - 1, 1.);

        logger.debug("Probabilities:");
        logger.debug(probabilities.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return probabilities;
    }

    protected Ind rankingSelectionStep(List<Double> probabilities) {
        logger.debug("rankingSelectionStep:");
        double r = RandomUtils.random.nextDouble();
        logger.debug("r = " + r);
        int k = 0;
        if (r == 1) k = generation.size() - 1;
        else {
            for (k = 0; k < probabilities.size() - 1; ) {
                if (r > probabilities.get(k + 1))
                    k++;
                else break;
            }
        }
        logger.debug("take individual " + k);
        return generation.get(k);
    }

    protected Ind proportionalSelectionStep(List<Double> probabilities) {
        logger.debug("Proportional selection step");
        double r = RandomUtils.random.nextDouble();
        logger.debug("r = " + r);
        int k = 0;
        if (r == 1) k = generation.size() - 1;
        else {
            for (k = 0; k < probabilities.size() - 1; ) {
                if (r > probabilities.get(k + 1))
                    k++;
                else break;
            }
        }
        logger.debug("take individual " + k);
        return generation.get(k);
    }

    protected Ind onePointRecombination(Ind parent1, Ind parent2) {
        logger.debug("onePointRecombination");
        int n = parent1.getGenotypeSize();
        boolean num = RandomUtils.random.nextBoolean();
        int r = RandomUtils.getRandomIndexExclude(new ArrayList<>(), n);
        logger.debug("r = " + r);
        logger.debug("parents");
        logger.debug(parent1.toString());
        logger.debug(parent2.toString());
        List<Integer> childGenotype = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            if (num) childGenotype.add(parent1.getGen(i));
            else childGenotype.add(parent2.getGen(i));
        }
        for (int i = r; i < n; i++) {
            if (num) childGenotype.add(parent2.getGen(i));
            else childGenotype.add(parent1.getGen(i));
        }
        logger.debug("child");
        logger.debug(childGenotype.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return getNewIndividual(problem, parameters.getBitNumberPerVariable(), childGenotype, logger);
    }

    protected Ind twoPointRecombination(Ind parent1, Ind parent2) {
        logger.debug("twoPointRecombination");
        int n = parent1.getGenotypeSize();
        boolean num = RandomUtils.random.nextBoolean();
        int r1 = RandomUtils.getRandomIndexExclude(new ArrayList<>(), n);
        int r2 = RandomUtils.getRandomIndexExclude(new ArrayList<>(), n);
        int r = Math.min(r1, r2);
        int R = Math.max(r1, r2);
        logger.debug("r = " + r);
        logger.debug("R = " + R);
        logger.debug("parents");
        logger.debug(parent1.toString());
        logger.debug(parent2.toString());
        List<Integer> childGenotype = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            if (num) childGenotype.add(parent1.getGen(i));
            else childGenotype.add(parent2.getGen(i));
        }
        for (int i = r; i < R; i++) {
            if (num) childGenotype.add(parent2.getGen(i));
            else childGenotype.add(parent1.getGen(i));
        }
        for (int i = R; i < n; i++) {
            if (num) childGenotype.add(parent1.getGen(i));
            else childGenotype.add(parent2.getGen(i));
        }
        logger.debug("child");
        logger.debug(childGenotype.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return getNewIndividual(problem, parameters.getBitNumberPerVariable(), childGenotype, logger);
    }

    protected Ind uniformRecombination(Ind parent1, Ind parent2) {
        logger.debug("uniformRecombination");
        int n = parent1.getGenotypeSize();
        List<Integer> childGenotype = new ArrayList<>();
        logger.debug("parents");
        logger.debug(parent1.toString());
        logger.debug(parent2.toString());
        for (int i = 0; i < n; i++) {
            if (RandomUtils.random.nextBoolean()) childGenotype.add(parent1.getGen(i));
            else childGenotype.add(parent2.getGen(i));
        }
        logger.debug("child");
        logger.debug(childGenotype.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return getNewIndividual(problem, parameters.getBitNumberPerVariable(), childGenotype, logger);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("");
        for (Ind individual : generation) {
            builder.append(individual.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

}
