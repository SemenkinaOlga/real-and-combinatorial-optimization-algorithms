package scheduling.optimization.combinatorial.GA.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Problem;
import scheduling.optimization.combinatorial.GA.settings.SelectionSettings;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.util.RandomUtils;
import scheduling.optimization.combinatorial.GA.util.RankingSelectionType;
import scheduling.optimization.combinatorial.GA.util.IndividualComparator;

import scheduling.optimization.combinatorial.GA.Individual;

import java.util.*;

public class Selection<R extends Number> extends Operator<R> {

    private static int initialWeight = 1;

    private static Logger logger = LoggerFactory.getLogger(Selection.class);

    private SelectionSettings settings;

    public Selection(SelectionSettings settings,
                     Problem<Integer, R> problem) {
        this.settings = settings;
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "settings=" + settings +
                '}';
    }

    private void proportionalSelection(List<Individual<R>> individuals, List<Individual<R>> parents,
                                       int parentsAmount) {
        // считаем суммарную пригодность
        double summaryFitness = 0;
        for (Individual<R> individual : individuals) {
            summaryFitness += individual.getFitness();
        }
        // набираем родителей
        for (int i = 0; i < parentsAmount; i++) {
            double rollDice = summaryFitness * RandomUtils.random.nextDouble();
            double localSummary = 0;
            for (Individual<R> individual : individuals) {
                // если суммарная пригодность меньше выброшенного числа - индивид становится родителем
                localSummary += individual.getFitness();
                if (localSummary >= rollDice) {
                    individual.getOperatorsSettings().put(OperatorType.SELECTION, settings);
                    parents.add(individual);
                    break;
                }
            }
        }
    }

    class IndividualWrapper {

        Individual<R> individual;
        double rank;

        IndividualWrapper(Individual<R> individual) {
            this.individual = individual;
        }

    }

    private void linearSelectionRanking(List<IndividualWrapper> individualsSorted) {
        // проставляем ранги
        int i = 0;
        while (i < individualsSorted.size()) {
            double fitness = individualsSorted.get(i).individual.getFitness();
            int j = i + 1;
            int count = 1;
            // считаем количество индивидов с такой же пригодностью
            while (j < individualsSorted.size() && fitness == individualsSorted.get(j).individual.getFitness()) {
                count += 1;
                j += 1;
            }
            // если индивидов несколько, рангом будет среднее арифметическое
            double rank = 0;
            for (int k = 1; k <= count; k++) {
                rank += i + k;
            }
            rank /= count;
            individualsSorted.get(i).rank = rank;
            if (count > 1) {
                for (int k = 1; k < count; k++) {
                    individualsSorted.get(i + k).rank = rank;
                }
            }
            i += count;
        }
    }

    private void exponentialSelectionRanking(List<IndividualWrapper> individualsSorted) {
        // проставляем ранги
        int size = individualsSorted.size();
        int i = size - 1;
        double fitness = -1;
        double rank = initialWeight;
        while (i >= 0) {
            if (fitness == individualsSorted.get(i).individual.getFitness()) {
                individualsSorted.get(i).rank = rank;
            } else {
                rank *= settings.getWeight();
                fitness = individualsSorted.get(i).individual.getFitness();
                individualsSorted.get(i).rank = rank;
            }
            i -= 1;
        }
    }

    private List<IndividualWrapper> getSortAndRankedIndividuals(List<Individual<R>> individuals) {
        // оборачиваем инивида, добавляя ранг
        List<IndividualWrapper> individualsSorted = new ArrayList<>(individuals.size());
        for (Individual<R> individual : individuals) {
            individualsSorted.add(new IndividualWrapper(individual));
        }
        // упорядочиваем индивидов по возрастанию пригодности
        individualsSorted.sort((o1, o2) -> {
            double difference = o1.individual.getFitness() - o2.individual.getFitness();
            return IndividualComparator.calcReturnValue(difference);
        });
        if (settings.getRankingSelectionType() == RankingSelectionType.EXPONENTIAL) {
            exponentialSelectionRanking(individualsSorted);
        } else if (settings.getRankingSelectionType() == RankingSelectionType.LINEAR) {
            linearSelectionRanking(individualsSorted);
        }
        return individualsSorted;
    }

    private void rankingSelection(List<Individual<R>> individuals, List<Individual<R>> parents, int parentsAmount) {
        List<IndividualWrapper> individualsSorted = getSortAndRankedIndividuals(individuals);
        // считаем суммарный ранг
        double summaryRank = 0;
        for (IndividualWrapper individualWrapper : individualsSorted) {
            summaryRank += individualWrapper.rank;
        }
        // набираем родителей
        for (int i = 0; i < parentsAmount; i++) {
            double rollDice = summaryRank * RandomUtils.random.nextDouble();
            double localSummary = 0;
            for (IndividualWrapper individualWrapper : individualsSorted) {
                // если суммарная пригодность меньше выброшенного числа - индивид становится родителем
                localSummary += individualWrapper.rank;
                if (localSummary >= rollDice) {
                    individualWrapper.individual.getOperatorsSettings().put(OperatorType.SELECTION, settings);
                    parents.add(individualWrapper.individual);
                    break;
                }
            }
        }
    }

    /**
     * турнирная селекция, выбор K индивидов из N и взятие лучшего из K
     *
     * @param individuals   - популяция индивидов
     * @param parentsAmount - необходимое кол-во родителей
     */
    private void tournamentSelection(List<Individual<R>> individuals, List<Individual<R>> parents, int parentsAmount) {
        for (int i = 0; i < parentsAmount; i++) {
            int maxIndex = 0;
            Double maxFitness = 0d;
            List<Integer> indexes = new ArrayList<>();
            for (int j = 0; j < settings.getTournamentSize(); j++) {
                int index = RandomUtils.getRandomIndexExclude(indexes, individuals.size());
                indexes.add(index);
                // выбираем лучшего
                Double fitness = individuals.get(index).getFitness();
                if (fitness > maxFitness) {
                    maxIndex = index;
                    maxFitness = fitness;
                }
            }
            Individual<R> chosen = individuals.get(maxIndex);
            chosen.getOperatorsSettings().put(OperatorType.SELECTION, settings);
            parents.add(chosen);
        }
    }

    public SelectionSettings getSettings() {
        return settings;
    }

    @Override
    public void apply(Individual<R> individual) {
        throw new IllegalArgumentException("Селекция не может быть применена с таким набором аргументов");
    }

    @Override
    public void apply(List<Individual<R>> individuals, List<Individual<R>> parents, int parentsAmount) {
        switch (settings.getSelectionType()) {
            case PROPORTIONAL:
                proportionalSelection(individuals, parents, parentsAmount);
                break;
            case RANKING:
                rankingSelection(individuals, parents, parentsAmount);
                break;
            case TOURNAMENT:
                tournamentSelection(individuals, parents, parentsAmount);
                break;
        }
    }

}
