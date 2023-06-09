package scheduling.optimization.combinatorial.GA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Problem;
import scheduling.optimization.combinatorial.GA.settings.OperatorSettings;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.util.RandomUtils;
import scheduling.optimization.Solution;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Individual<R extends Number> implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(Individual.class);

    private List<Integer> phenotype;
    private R objectiveFunctionValue;
    private Double fitness;
    private int dimension;
    private Map<OperatorType, OperatorSettings> operatorsSettings = new HashMap<>();

    Solution<Integer, R> solution;

    public Individual(Problem<Integer, R> problem) {
        this.dimension = problem.getDimension();

        phenotype = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            phenotype.add(i);
        }
        // Таcование Фишера-Йетса
        for (int i = dimension - 1; i >= 0; i--) {
            int j = RandomUtils.random.nextInt(i + 1);
            Collections.swap(phenotype, i, j);
        }

        objectiveFunctionValue = problem.getWorst();
        this.solution = new Solution<>(phenotype, objectiveFunctionValue);

        fitness = 10000d / (1 + objectiveFunctionValue.doubleValue());
    }

    public Individual(int dimension, Solution<Integer, R> solution) {
        this.dimension = dimension;

        phenotype = new ArrayList<>(solution.getData());
        this.solution = new Solution<>(solution);
        objectiveFunctionValue = solution.getValue();
        fitness = 10000d / (1 + objectiveFunctionValue.doubleValue());
    }

    public Individual(Individual<R> individual) {
        this.dimension = individual.dimension;

        phenotype = new ArrayList<>(individual.phenotype);
        this.solution = new Solution<>(individual.solution);
        objectiveFunctionValue = individual.objectiveFunctionValue;
        fitness = 10000d / (1 + objectiveFunctionValue.doubleValue());
    }

    public Solution<Integer, R> getSolution() {
        return solution;
    }

    public void calcFitness(Problem<Integer, R> problem) {
        solution = problem.createSolution(phenotype);
        objectiveFunctionValue = problem.calculateObjectiveFunction(solution);
        solution.setValue(objectiveFunctionValue);
        fitness = 10000d / (1 + objectiveFunctionValue.doubleValue());
    }

    public Integer getElementByIndex(int index) {
        return phenotype.get(index);
    }

    public String toString() {
        return "(" + phenotype.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + ")"
                + " val = " + objectiveFunctionValue;
    }

    public void setFitness(Double fitness){ this.fitness = fitness; }

    public List<Integer> getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(List<Integer> phenotype) {
        this.phenotype = phenotype;
    }

    public Double getFitness() {
        return fitness;
    }

    public int getDimension() {
        return dimension;
    }

    public R getObjectiveFunctionValue() {
        return objectiveFunctionValue;
    }

    public void setObjectiveFunctionValue(R objectiveFunctionValue) {
        this.objectiveFunctionValue = objectiveFunctionValue;
    }

    public Map<OperatorType, OperatorSettings> getOperatorsSettings() {
        return operatorsSettings;
    }

}
