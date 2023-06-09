package scheduling.optimization.combinatorial.GA.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Problem;
import scheduling.optimization.combinatorial.GA.settings.RecombinationSettings;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.util.RandomUtils;

import scheduling.optimization.combinatorial.GA.Individual;

import java.util.ArrayList;
import java.util.List;

public class Recombination<R extends Number> extends Operator<R> {

    private static Logger logger = LoggerFactory.getLogger(Recombination.class);

    private RecombinationSettings settings;

    public Recombination(RecombinationSettings settings,
                         Problem<Integer, R> problem) {
        this.settings = settings;
        this.problem = problem;
    }

    @Override
    public String toString() {
        return "Recombination{" +
                "settings=" + settings +
                '}';
    }

    private void typicalRecombination(List<Individual<R>> individuals, List<Individual<R>> parents, int index) {
        Individual<R> child;
        // случайным образом выбираем порядок родителей
        if (RandomUtils.random.nextBoolean()) {
            child = generateChild(parents.get(index), parents.get(index + 1));
        } else {
            child = generateChild(parents.get(index + 1), parents.get(index));
        }
        individuals.add(child);
    }

    private Individual<R> generateChild(Individual<R> parent0, Individual<R> parent1) {
        Individual<R> child = new Individual<>(problem);
        List<Integer> phenotype = new ArrayList<>();
        int size = parent0.getDimension();
        int index0 = RandomUtils.getRandomIndexExclude(null, size);
        int index1 = RandomUtils.getRandomIndexExclude(index0, size);
        int first = Math.min(index0, index1);
        int last = Math.max(index0, index1);

        List<Integer> part = new ArrayList<>();
        // копируем всё, что в промежутке между index0 и index1
        for (int i = first; i <= last; i++) {
            part.add(parent0.getElementByIndex(i));
        }

        for (int i = 0; i < first; i++) {
            int k = parent1.getElementByIndex(i);
            if (!part.contains(k)) {
                phenotype.add(k);
            }
        }
        phenotype.addAll(part);

        for (int i = first; i < size; i++) {
            int k = parent1.getElementByIndex(i);
            if (!phenotype.contains(k)) {
                phenotype.add(k);
            }
        }

        child.setPhenotype(phenotype);
        for (OperatorType operatorType : parent0.getOperatorsSettings().keySet()) {
            child.getOperatorsSettings().put(operatorType, parent1.getOperatorsSettings().get(operatorType));
        }
        child.getOperatorsSettings().put(OperatorType.RECOMBINATION, settings);
        return child;
    }

    public RecombinationSettings getSettings() {
        return settings;
    }

    @Override
    public void apply(Individual<R> individual) {
        throw new IllegalArgumentException("Рекомендация не может быть применена с таким набором аргументов");
    }

    @Override
    public void apply(List<Individual<R>> individuals, List<Individual<R>> parents, int index) {
        switch (settings.getRecombinationType()) {
            case TYPICAL:
                typicalRecombination(individuals, parents, index);
                break;
        }
    }

    public void setSettings(RecombinationSettings settings) {
        this.settings = settings;
    }

}
