package scheduling.optimization.real.DE;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IndividualRoot;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IndividualBase<R extends Number> extends IndividualRoot<Double, R, ProblemReal<Double, R>> {

    protected List<Double> vector;

    public IndividualBase(ProblemReal<Double, R> problem, LoggerWrapper logger) {
        super(problem, logger);
        logger.debug("Создание индивида");
        vector = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            double r = problem.getLeftBound() + (problem.getRightBound()  - problem.getLeftBound() )
                    * RandomUtils.random.nextDouble();
            vector.add(r);
            sb.append(r);
            sb.append(" ");
        }
        logger.debug(sb.toString());
    }

    public IndividualBase(IndividualBase<R> individual) {
        super(individual.problem, individual.logger);
        logger.debug("Создание индивида копированием");
        this.objectiveFunctionValue = individual.objectiveFunctionValue;
        solution = individual.solution;
        vector = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            vector.add(individual.vector.get(i));
        }
    }

    public IndividualBase(ProblemReal<Double, R> problem, Solution<Double, R> solution,
                          LoggerWrapper logger) {
        super(problem, solution, logger);

        vector = new ArrayList<>(solution.getData());
        objectiveFunctionValue = solution.getValue();
    }

    @Override
    public R calculateObjectiveFunction(ProblemReal<Double, R> problem) {
        logger.debug("Подсчёт значения целевой функции");
        solution = problem.createSolution(vector);
        objectiveFunctionValue = problem.calculateObjectiveFunction(solution);
        logger.debug("Значение оптимизируемой функции: " + objectiveFunctionValue);
        return objectiveFunctionValue;
    }

    public String toString() {
        return "(" + vector.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + ")"
                + " val = " + objectiveFunctionValue;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }

}
