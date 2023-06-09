package scheduling.optimization.combinatorial.GA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.GA.settings.OperatorSettings;
import scheduling.optimization.combinatorial.GA.util.OperatorFactory;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.combinatorial.GA.operators.*;
import scheduling.optimization.combinatorial.GA.util.IndividualComparator;
import scheduling.optimization.util.RandomUtils;

import java.util.*;

import static scheduling.optimization.util.RandomUtils.getRandomIndexExclude;

public class Population<R extends Number> {

    private static Logger logger = LoggerFactory.getLogger(Population.class);
    protected LoggerWrapper mainLogger;

    private OperatorFactory operatorFactory = new OperatorFactory();

    private List<Individual<R>> parents = new ArrayList<>();
    private List<Individual<R>> oldGeneration;
    private List<Individual<R>> newGeneration;
    private Individual<R> totalBestIndividual;
    private Individual<R> currentBestIndividual;
    private Integer individualsAmount;

    Problem<Integer, R> problem;

    public Population(Problem<Integer, R> problem, int individualsAmount, LoggerWrapper mainLogger) {
        this.problem = problem;
        this.individualsAmount = individualsAmount;
        this.mainLogger = mainLogger;
        mainLogger.debug("Population creation");

        newGeneration = new ArrayList<>();
        oldGeneration = new ArrayList<>();
        for (int i = 0; i < individualsAmount; i++) {
            newGeneration.add(new Individual<>(problem));
            mainLogger.debug(newGeneration.get(newGeneration.size() - 1).solution.toString());
        }
        calcFitness();
        findBest();
    }

    public void resizePopulation(int size) {
        mainLogger.debug("resizePopulation " + size);
        if (size >= individualsAmount) {
            for (int i = 0; i < size - individualsAmount; i++) {
                Individual<R> ind = new Individual<>(problem);
                newGeneration.add(ind);
            }
        } else {
            newGeneration.sort(new IndividualComparator().reversed());
            newGeneration.subList(size, newGeneration.size()).clear();
        }
        individualsAmount = size;
    }

    public void calcFitness() {
        for (Individual<R> individual : newGeneration) {
            individual.calcFitness(problem);
        }
    }

    private Individual<R> getBest(List<Individual<R>> individuals) {
        int i = 0;
        int index = 0;
        Double bestFitness = 0d;
        for (Individual<R> individual : individuals) {
            if (individual.getFitness() > bestFitness) {
                bestFitness = individual.getFitness();
                index = i;
            }
            i++;
        }
        return individuals.get(index);
    }

    public void findBest() {
        currentBestIndividual = getBest(newGeneration);
        // первое поколение - просто берём лучшего
        if (totalBestIndividual == null) {
            totalBestIndividual = new Individual<>(currentBestIndividual);
        } else {
            // для всех остальных сравниваем пригодность
            if (problem.compare(currentBestIndividual.getSolution(), totalBestIndividual.getSolution()) > 0) {
                totalBestIndividual = new Individual<>(currentBestIndividual);
            } else {
            }
        }
    }

    public void rememberGeneration(int amount) {
        newGeneration.sort(new IndividualComparator());
        oldGeneration.clear();
        // округляем в большую сторону
        int size = newGeneration.size();
        // забираем индивидов с лучшей пригодностью
        while (amount > 0) {
            oldGeneration.add(new Individual<>(newGeneration.get(size - amount)));
            amount -= 1;
        }
    }

    public void replacement() {
        if (oldGeneration.size() > 0) {
            newGeneration.sort(new IndividualComparator());
            newGeneration.subList(0, oldGeneration.size()).clear();
            for (Individual<R> individual : oldGeneration) {
                newGeneration.add(new Individual<>(individual));
            }
        }
    }

    public void applyOperator(OperatorType operatorType, Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators) {
        switch (operatorType) {
            case SELECTION:
                parents.clear();
                mainLogger.debug("SELECTION ");
                for (int i = 0; i < individualsAmount; i++) {
                    Operator<R> operator = operatorFactory.selectOperator(operators.get(operatorType));
                    operator.apply(newGeneration, parents, 2);
                }
                break;
            case RECOMBINATION:
                newGeneration.clear();
                mainLogger.debug("RECOMBINATION ");
                for (int i = 0; i < parents.size(); i += 2) {
                    Operator<R> operator = operatorFactory.selectOperator(operators.get(operatorType));
                    operator.apply(newGeneration, parents, i);
                }
                break;
            case MUTATION:
                mainLogger.debug("MUTATION ");
                for (Individual<R> individual : newGeneration) {
                    Operator<R> operator = operatorFactory.selectOperator(operators.get(operatorType));
                    operator.apply(individual);
                }
                break;
        }

    }

    public void applyOperatorWithRelated(Map<OperatorSettings, Operator<R>> operators,
                                         OperatorType mainOperatorType) {
        for (Individual<R> individual : newGeneration) {
            Operator<R> operator = operators.get(individual.getOperatorsSettings().get(mainOperatorType));
            Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getRelatedOperators();
            if (relatedOperators.size() > 0) {
                for (OperatorType relatedOperatorType : relatedOperators.keySet()) {
                    Operator<R> relatedOperator = operatorFactory.selectOperator(operator.getRelatedOperators().get(relatedOperatorType));
                    relatedOperator.apply(individual);
                }
            }
        }
    }

    public void calcOperatorsFitness(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators) {
        calcOperatorFitness(newGeneration, operators);
    }

    public void calcOperatorFitness(List<Individual<R>> individuals, Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators) {
        // обнуляем пригодность и кол-во индивидов
        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            for (Map.Entry<OperatorSettings, Operator<R>> operator : entry.getValue().entrySet()) {
                operator.getValue().setFitness(0.0);
                operator.getValue().setAmountOfIndividuals(0);
                Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getValue().getRelatedOperators();
                if (relatedOperators.size() > 0) {
                    for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> relEntry : relatedOperators.entrySet()) {
                        for (Map.Entry<OperatorSettings, Operator<R>> relOperator : relEntry.getValue().entrySet()) {
                            relOperator.getValue().setFitness(0.0);
                            relOperator.getValue().setAmountOfIndividuals(0);
                        }
                    }
                }
            }
        }
        // считаем кол-во индивидов по определённому оператору и сумму их пригодностей
        for (Individual<R> individual : individuals) {
            for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
                // в общей мапе ключ - тип оператора, в мапе индвида - такой же
                OperatorSettings operatorSettings = individual.getOperatorsSettings().get(entry.getKey());
                Operator<R> operator = entry.getValue().get(operatorSettings);
                operator.setAmountOfIndividuals(operator.getAmountOfIndividuals() + 1);
                operator.setFitness(operator.getFitness() + individual.getFitness());
                Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getRelatedOperators();
                if (relatedOperators.size() > 0) {
                    for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> relEntry : relatedOperators.entrySet()) {
                        OperatorSettings relOperatorSettings = individual.getOperatorsSettings().get(relEntry.getKey());
                        Operator<R> relatedOperator = relEntry.getValue().get(relOperatorSettings);
                        relatedOperator.setFitness(relatedOperator.getFitness() + individual.getFitness());
                        relatedOperator.setFitness(relatedOperator.getFitness() + individual.getFitness());
                    }
                }
            }
        }
        // считаем среднюю пригодность
        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            for (Map.Entry<OperatorSettings, Operator<R>> operator : entry.getValue().entrySet()) {
                int amountOfIndividuals = operator.getValue().getAmountOfIndividuals();
                if (amountOfIndividuals != 0) {
                    operator.getValue().setFitness(operator.getValue().getFitness() / amountOfIndividuals);
                }
                Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getValue().getRelatedOperators();
                if (relatedOperators.size() > 0) {
                    for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> relEntry : relatedOperators.entrySet()) {
                        for (Map.Entry<OperatorSettings, Operator<R>> relOperator : relEntry.getValue().entrySet()) {
                            int relAmountOfIndividuals = relOperator.getValue().getAmountOfIndividuals();
                            if (relAmountOfIndividuals != 0) {
                                relOperator.getValue().setFitness(relOperator.getValue().getFitness() / relAmountOfIndividuals);
                            }
                        }
                    }
                }
            }
        }
    }

    public void configureOperators(Map<OperatorType, Map<OperatorSettings, Operator<R>>> operators, int generationsAmount) {
        for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> entry : operators.entrySet()) {
            setOperatorsProbabilities(entry.getValue(), generationsAmount);
            setOperatorsDistribution(entry.getValue());
            // а теперь для связанных операторов
            for (Map.Entry<OperatorSettings, Operator<R>> operator : entry.getValue().entrySet()) {
                Map<OperatorType, Map<OperatorSettings, Operator<R>>> relatedOperators = operator.getValue().getRelatedOperators();
                if (relatedOperators.size() > 0) {
                    for (Map.Entry<OperatorType, Map<OperatorSettings, Operator<R>>> relEntry : relatedOperators.entrySet()) {
                        setOperatorsProbabilities(relEntry.getValue(), generationsAmount);
                        setOperatorsDistribution(relEntry.getValue());
                    }
                }
            }
        }
    }

    public void setOperatorsDistribution(Map<OperatorSettings, Operator<R>> operators) {
        Double distribution = 0.0;
        for (Map.Entry<OperatorSettings, Operator<R>> entry : operators.entrySet()) {
            distribution += entry.getValue().getProbability();
            entry.getValue().setDistribution(distribution);
        }
    }

    public void setOperatorsProbabilities(Map<OperatorSettings, Operator<R>> operators, int generationsAmount) {
        double maxFitness = 0;
        List<Operator<R>> bestOperators = new ArrayList<>();
        for (Map.Entry<OperatorSettings, Operator<R>> entry : operators.entrySet()) {
            Operator<R> operator = entry.getValue();
            if (operator.getFitness() > maxFitness) {
                maxFitness = operator.getFitness();
                bestOperators.clear();
                bestOperators.add(operator);
            } else if (operator.getFitness() == maxFitness) {
                bestOperators.add(operator);
            }
        }
        Operator<R> bestOperator;
        if (bestOperators.size() == 1) {
            bestOperator = bestOperators.get(0);
        } else {
            int index = getRandomIndexExclude(null, bestOperators.size());
            bestOperator = bestOperators.get(index);
        }

        // вероятности всех остальных операторов уменьшаются на K /(z * N)),
        // где N – число поколений, K – константа, обычно равная 2
        double reduceProbability = 2d / (operators.size() * generationsAmount);
        double increaseProbability = 0;
        for (Map.Entry<OperatorSettings, Operator<R>> entry : operators.entrySet()) {
            Operator<R> operator = entry.getValue();
            if (!operator.equals(bestOperator)) {
                // необходимо установить порог вероятности т.к. ни у одного варианта оператора не может быть нулевой вероятности
                // при достижении минимально возможного значения вероятности вариант оператора перестает отдавать свою часть в пользу лучшего
                if (operator.getProbability() - reduceProbability <= Operator.MIN_OPERATOR_PROBABILITY) {
                    increaseProbability += operator.getProbability() - Operator.MIN_OPERATOR_PROBABILITY;
                    operator.setProbability(Operator.MIN_OPERATOR_PROBABILITY);
                } else {
                    increaseProbability += reduceProbability;
                    operator.setProbability(operator.getProbability() - reduceProbability);
                }
            }
        }
        bestOperator.setProbability(bestOperator.getProbability() + increaseProbability);
    }

    public Individual<R> getBestIndividual() {
        return currentBestIndividual;
    }

    public Individual<R> getRandomIndividual() {
        return newGeneration.get(RandomUtils.random.nextInt(newGeneration.size()));
    }

    public Individual<R> getTotalBestIndividual() {
        return totalBestIndividual;
    }

    public int getIndividualsAmount() {
        return this.individualsAmount;
    }

    public void setIndividualsAmount(Integer individualsAmount) {
        this.individualsAmount = individualsAmount;
    }

    public void replaceRandom(List<Solution<Integer, R>> solutions) {
        List<Integer> indexes = RandomUtils.getRandomIndexes(solutions.size(), individualsAmount);
        List<Individual<R>> toRemove = new ArrayList<>();

        for (int index : indexes) {
            toRemove.add(newGeneration.get(index));
        }
        newGeneration.removeAll(toRemove);

        for (Solution<Integer, R> solution : solutions) {
            newGeneration.add(new Individual<>(problem.getDimension(), solution));
        }
        findBest();
    }

    public void replaceWorst(List<Solution<Integer, R>> solutions) {
        newGeneration.sort(new IndividualComparator().reversed());
        newGeneration.subList(newGeneration.size() - solutions.size(), newGeneration.size()).clear();
        for (Solution<Integer, R> solution : solutions) {
            newGeneration.add(new Individual<>(problem.getDimension(), solution));
        }
        findBest();
    }

    public List<Solution<Integer, R>> getPopulation() {
        List<Solution<Integer, R>> solutions = new ArrayList<>();
        for (Individual<R> ind : newGeneration) {
            solutions.add(ind.getSolution());
        }
        return solutions;
    }

    public void setPopulation(List<Solution<Integer, R>> solutions) {
        newGeneration = new ArrayList<>();
        for (Solution<Integer, R> solution : solutions) {
            newGeneration.add(new Individual<>(problem.getDimension(), solution));
        }
        findBest();
    }
}
