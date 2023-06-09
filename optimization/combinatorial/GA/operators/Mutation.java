package scheduling.optimization.combinatorial.GA.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Problem;
import scheduling.optimization.combinatorial.GA.settings.MutationSettings;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.combinatorial.GA.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static scheduling.optimization.util.RandomUtils.*;

public class Mutation<R extends Number> extends Operator<R> {

    private static Logger logger = LoggerFactory.getLogger(Mutation.class);

    private MutationSettings settings;

    public Mutation(MutationSettings settings,
                    Problem<Integer, R> problem) {
        this.settings = settings;
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "Mutation{" +
                "settings=" + settings +
                '}';
    }

    public void innerMutation(Individual<R> individual, int size) {
        switch (settings.getMutationType()) {
            case BY_2_EXCHANGE:
                mutationBy2Exchange(individual, size);
                break;
            case BY_INSERTION:
                mutationByInsertion(individual, size);
                break;
            case BY_INVERSION:
                mutationByInversion(individual, size);
                break;
            case BY_SHIFTING:
                mutationByShifting(individual, size);
                break;
        }
    }

    public void mutationBy2Exchange(Individual<R> individual, int size) {
        int index0 = getRandomIndexExclude(null, size);
        int index1 = getRandomIndexExclude(null, size);
        if (index0 != index1) {
            Collections.swap(individual.getPhenotype(), index0, index1);
        }
    }

    public void mutationByInversion(Individual<R> individual, int size) {
        int index0 = getRandomIndexExclude(null, size);
        int index1 = getRandomIndexExclude(null, size);
        int first = Math.min(index0, index1);
        int last = Math.max(index0, index1);
        if (first != last) {
            int numberOfSwaps = Math.abs(last - first) / 2;
            for (int i = 0; i <= numberOfSwaps; i++) {
                Collections.swap(individual.getPhenotype(), first + i, last - i);
            }
        }
    }

    public void insert(Individual<R> individual, int index0, int index1) {
        if (index0 != index1) {
            if (index0 < index1) {
                int numberOfSwaps = index1 - index0;
                for (int i = 0; i < numberOfSwaps; i++) {
                    Collections.swap(individual.getPhenotype(), index0 + i, index0 + i + 1);
                }
            } else {
                int numberOfSwaps = index0 - index1;
                for (int i = 0; i < numberOfSwaps; i++) {
                    Collections.swap(individual.getPhenotype(), index0 - i, index0 - i - 1);
                }
            }
        }
    }

    public void mutationByInsertion(Individual<R> individual, int size) {
        int index0 = getRandomIndexExclude(null, size);
        int index1 = getRandomIndexExclude(null, size);
        insert(individual, index0, index1);
    }

    public void mutationByShifting(Individual<R> individual, int size) {
        int index0 = getRandomIndexExclude(null, size);
        int index1 = getRandomIndexExclude(null, size);
        int first = Math.min(index0, index1);
        int last = Math.max(index0, index1);
        if (first == last) {
            int index2 = getRandomIndexExclude(first, size);
            insert(individual, first, index2);
        } else {
            if (!(first == 0 && last == size - 1)) {
                List<Integer> newPhenotype = new ArrayList<>();
                List<Integer> part = new ArrayList<>();
                // вырезаем нужную часть где-то в середине
                for (int i = first; i <= last; i++) {
                    part.add(individual.getElementByIndex(i));
                }
                individual.getPhenotype().removeAll(part);
                int index2 = getRandomIndexExclude(null, individual.getPhenotype().size());
                // добавляем остаток в новый фенотип
                newPhenotype.addAll(individual.getPhenotype().subList(0, index2));
                newPhenotype.addAll(part);
                newPhenotype.addAll(individual.getPhenotype().subList(index2, individual.getPhenotype().size()));
                individual.setPhenotype(newPhenotype);
            }
        }
    }

    public boolean isRunMutation(Double mutationProbability) {
        return mutationProbability >= random.nextDouble() ? true : false;
    }

    public MutationSettings getSettings() {
        return settings;
    }

    @Override
    public void apply(List<Individual<R>> individuals, List<Individual<R>> parents, int parentsAmountl) {
        throw new IllegalArgumentException("Мутация не может быть применена с таким набором аргументов");
    }

    @Override
    public void apply(Individual<R> individual) {
        individual.getOperatorsSettings().put(OperatorType.MUTATION, settings);
        int size = individual.getDimension();
        if (isRunMutation(settings.getMutationProbability())) {
            innerMutation(individual, size);
        }
        // при вероятности мутации 1.5 индивид мутирует второй раз с вероятностью 50%,
        // при вероятности мутации 2 индивид всегда мутирует дважды
        if (settings.getMutationProbability() > 1) {
            if (isRunMutation(settings.getMutationProbability() - 1)) {
                innerMutation(individual, size);
            }
        }
    }

}