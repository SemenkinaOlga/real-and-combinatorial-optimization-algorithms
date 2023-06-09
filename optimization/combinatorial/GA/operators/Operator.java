package scheduling.optimization.combinatorial.GA.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.Problem;
import scheduling.optimization.combinatorial.GA.AdaptiveGAParameters;
import scheduling.optimization.combinatorial.GA.GAParameters;
import scheduling.optimization.combinatorial.GA.GAParametersBase;
import scheduling.optimization.combinatorial.GA.settings.MutationSettings;
import scheduling.optimization.combinatorial.GA.settings.OperatorSettings;
import scheduling.optimization.combinatorial.GA.settings.RecombinationSettings;
import scheduling.optimization.combinatorial.GA.settings.SelectionSettings;
import scheduling.optimization.combinatorial.GA.util.OperatorFactory;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.util.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduling.optimization.combinatorial.GA.Individual;
import scheduling.optimization.combinatorial.GA.util.SelfConfiguringType;

import static scheduling.optimization.combinatorial.GA.util.OperatorType.*;
import static scheduling.optimization.util.RandomUtils.getRandomIndexExclude;

public abstract class Operator<R extends Number> {

    public static final double MIN_OPERATOR_PROBABILITY = 0.02d;

    private static Logger logger = LoggerFactory.getLogger(Operator.class);

    private OperatorFactory operatorFactory = new OperatorFactory();

    private Double probability;
    private Double distribution;
    private Double fitness;
    private int amountOfIndividuals;

    protected Problem<Integer, R> problem;

    private Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = new HashMap<>();

    public Double getProbability() { return probability; }
    public void setProbability(Double probability) {
        this.probability = probability;
    }
    public Double getFitness() { return fitness; }
    public void setFitness(Double fitness) { this.fitness = fitness; }
    public int getAmountOfIndividuals() { return amountOfIndividuals; }
    public void setAmountOfIndividuals(int amountOfIndividuals) { this.amountOfIndividuals = amountOfIndividuals; }

    @Override
    public abstract String toString();


    public Double getDistribution() {
        return distribution;
    }

    public void setDistribution(Double distribution) {
        this.distribution = distribution;
    }

    public abstract OperatorSettings getSettings();

    public Map<OperatorType, Map<OperatorSettings, Operator<R>>> getRelatedOperators() {
        return relatedOperators;
    }

    public abstract void apply(Individual<R> individual);

    public abstract void apply(List<Individual<R>> individuals, List<Individual<R>> parents, int param);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operator<R> operator = (Operator<R>) o;

        return getSettings().equals(operator.getSettings());
    }

    public static List<List<String>> getOperatorsSettings(OperatorType needOperatorType, String operatorsFolder) throws IOException {
        List<List<String>> result = new ArrayList<>();

        int operatorsCount = 0;
        File operatorFile = new File(operatorsFolder + "\\operator" + operatorsCount + ".txt");
        while (operatorFile.exists()) {
            List<String> content = Files.readAllLines(Paths.get(operatorFile.getAbsolutePath()));
            OperatorType operatorType = OperatorType.valueOf(content.get(0));
            if (operatorType == needOperatorType) {
                result.add(content);
            }
            operatorsCount++;
            operatorFile = new File(operatorsFolder + "\\operator" + operatorsCount + ".txt");
        }

        return result;
    }

    public void writeHeadProbabilitiesInFiles(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators,
                                                     int problemNumber, OperatorType relatedOperatorType) throws IOException {
        StringBuilder sbRecombinations = new StringBuilder();
        StringBuilder sbSelections = new StringBuilder();
        StringBuilder sbMutations = new StringBuilder();

        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            for (Map.Entry<OperatorSettings, Operator<R>> operator : entry.getValue().entrySet()) {
                OperatorType operatorType = operator.getKey().getOperatorType();
                if (operatorType != relatedOperatorType) {
                    StringBuilder sb = null;
                    if (operatorType == RECOMBINATION) {
                        sb = sbRecombinations;
                    } else if (operatorType == SELECTION) {
                        sb = sbSelections;
                    } else if (operatorType == MUTATION) {
                        sb = sbMutations;
                    }
                    if (sb.length() > 0) {
                        sb.append("\t");
                    }
                    Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getValue().getRelatedOperators();
                    if (relatedOperators.size() == 0) {
                        sb.append(operator.getKey().toString());
                    } else {
                        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> relEntry : relatedOperators.entrySet()) {
                            for (Map.Entry<OperatorSettings, Operator<R>> relatedOperator : relEntry.getValue().entrySet()) {
                                sb.append(operator.getKey().toString()).append(" ").
                                        append(relatedOperator.getKey().toString()).append("\t");
                            }
                        }
                    }
                }
            }
        }

        List<String> recombinations = new ArrayList<>();
        List<String> selections = new ArrayList<>();
        List<String> mutations = new ArrayList<>();
        recombinations.add(sbRecombinations.toString());
        selections.add(sbSelections.toString());
        mutations.add(sbMutations.toString());

        Files.write(Paths.get("probabilities\\problem" + problemNumber + "-recombinations.txt"), recombinations);
        Files.write(Paths.get("probabilities\\problem" + problemNumber + "-selections.txt"), selections);
        Files.write(Paths.get("probabilities\\problem" + problemNumber + "-mutations.txt"), mutations);
    }

    public void writeProbabilitiesInFiles(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators,
                                                 int countOfGenerations, int problemNumber) throws IOException {
        writeProbabilitiesInFiles(operators, countOfGenerations, problemNumber, null);
    }

    public void writeProbabilitiesInFiles(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators,
                                                 int countOfGenerations, int problemNumber,
                                                 OperatorType relatedOperatorType) throws IOException {

        StringBuilder sbRecombinations = new StringBuilder();
        StringBuilder sbSelections = new StringBuilder();
        StringBuilder sbMutations = new StringBuilder();

        if (relatedOperatorType != RECOMBINATION) sbRecombinations.append(countOfGenerations).append("\t");
        if (relatedOperatorType != SELECTION) sbSelections.append(countOfGenerations).append("\t");
        if (relatedOperatorType != MUTATION) sbMutations.append(countOfGenerations).append("\t");

        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            for (Map.Entry<OperatorSettings, Operator<R>> operator : entry.getValue().entrySet()) {
                OperatorType operatorType = operator.getKey().getOperatorType();
                if (operatorType != relatedOperatorType) {
                    StringBuilder sb = null;
                    if (operatorType == RECOMBINATION) {
                        sb = sbRecombinations;
                    } else if (operatorType == SELECTION) {
                        sb = sbSelections;
                    } else if (operatorType == MUTATION) {
                        sb = sbMutations;
                    }
                    if (sb.length() > 0) {
                        sb.append("\t");
                    }
                    Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getValue().getRelatedOperators();
                    if (relatedOperators.size() == 0) {
                        sb.append(operator.getValue().probability);
                    } else {
                        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> relEntry : relatedOperators.entrySet()) {
                            for (Map.Entry<OperatorSettings, Operator<R>> relatedOperator : relEntry.getValue().entrySet()) {
                                sb.append(operator.getValue().probability * relatedOperator.getValue().probability).append("\t");
                            }
                        }
                    }
                }
            }
        }

        List<String> recombinations = new ArrayList<>();
        List<String> selections = new ArrayList<>();
        List<String> mutations = new ArrayList<>();
        recombinations.add(sbRecombinations.toString());
        selections.add(sbSelections.toString());
        mutations.add(sbMutations.toString());

        Files.write(Paths.get("probabilities\\problem" + problemNumber + "-recombinations.txt"),
                recombinations, StandardOpenOption.APPEND);
        Files.write(Paths.get("probabilities\\problem" + problemNumber + "-selections.txt"), selections,
                StandardOpenOption.APPEND);
        Files.write(Paths.get("probabilities\\problem" + problemNumber + "-mutations.txt"), mutations,
                StandardOpenOption.APPEND);
    }

}
