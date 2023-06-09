package scheduling.optimization.real.RGA;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IndividualRoot;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IndividualBase<R extends Number> extends IndividualRoot<Double, R, ProblemReal<Double, R>> {

    private List<Double> vector;
    private List<Integer> genotype;
    private int bitNumberPerVariable;
    private int genotypeSize;

    public IndividualBase(ProblemReal<Double, R> problem, int bitNumberPerVariable, LoggerWrapper logger) {
        super(problem, logger);
        // TODO: check that it is ok if solution without initialization
        //solution = new Solution<Double, R>(new ArrayList<>(), 0.);
        this.bitNumberPerVariable = bitNumberPerVariable;
        genotypeSize = dimension * bitNumberPerVariable;
        vector = new ArrayList<>(dimension);
        genotype = new ArrayList<>();
        for (int i = 0; i < genotypeSize; i++) {
            genotype.add(RandomUtils.random.nextInt(2));
        }
        refreshCoordinates();
    }

    public IndividualBase(ProblemReal<Double, R> problem, int bitNumberPerVariable, List<Integer> genotype, LoggerWrapper logger) {
        super(problem, logger);
        // TODO: check that it is ok if solution without initialization
        //solution = new Solution<Double, R>(new ArrayList<>(), 0);
        this.bitNumberPerVariable = bitNumberPerVariable;
        genotypeSize = dimension * bitNumberPerVariable;
        this.genotype = genotype;
        vector = new ArrayList<>(dimension);
        // TODO: Exception? Log?
        // if (genotypeSize != genotype.size()){}
        refreshCoordinates();
    }

    public IndividualBase(IndividualBase<R> individual) {
        super(individual.problem, individual.solution, individual.logger);
        this.objectiveFunctionValue = individual.objectiveFunctionValue;
        this.bitNumberPerVariable = individual.bitNumberPerVariable;
        genotypeSize = dimension * bitNumberPerVariable;
        genotype = new ArrayList<>(individual.genotype);
        vector = new ArrayList<>(individual.vector);
    }

    public IndividualBase(ProblemReal<Double, R> problem, int bitNumberPerVariable, Solution<Double, R> solution, LoggerWrapper logger) {
        super(problem, solution, logger);
        this.bitNumberPerVariable = bitNumberPerVariable;
        genotypeSize = dimension * bitNumberPerVariable;
        vector = new ArrayList<>(solution.getData());
        genotype = new ArrayList<>(genotypeSize);
        createGenotypeByVector();
        this.solution = new Solution<>(solution);
        objectiveFunctionValue = solution.getValue();
        refreshCoordinates();
    }

    private void createGenotypeByVector(){
        double step = (problem.getRightBound() - problem.getLeftBound()) / (Math.pow(2, bitNumberPerVariable) - 1);
        for (Double v: vector) toBinary(v, step);
    }

    private void toBinary(double number, double step){
        double rb =  problem.getRightBound();
        double lb =  problem.getLeftBound();
        double a = (number - lb);
        a = a / step;

        for (int i = bitNumberPerVariable - 1; i >= 0; i--){
            double current =  Math.pow(2, i);
            if (a > current){
                genotype.add(1);
                a = a - current;
            }
            else {
                genotype.add(0);
            }
        }
    }

    void refreshCoordinates() {
        logger.debug("refreshCoordinates");
        logger.debug(genotype.stream().map(Object::toString)
                .collect(Collectors.joining(" ")));
        vector = new ArrayList<>();
        for (int j = 0; j < dimension; j++) {
            double r = 0, R;
            for (int i = bitNumberPerVariable - 1; i >= 0; i--) {
                R = Math.pow(2.0, i);
                r += (double) (genotype.get(j * bitNumberPerVariable + bitNumberPerVariable - 1 - i)) * R;
            }
            vector.add(problem.getLeftBound() +
                    (problem.getRightBound() - problem.getLeftBound()) * r /
                            (Math.pow(2.0, bitNumberPerVariable) - 1));
        }
        logger.debug(vector.stream().map(Object::toString)
                .collect(Collectors.joining(" ")));
    }

    public int getGenotypeSize() {
        return genotypeSize;
    }

    public int getGen(int i) {
        return genotype.get(i);
    }

    public void mutation(double mutationProbability) {
        logger.debug("mutation");
        logger.debug("old");
        logger.debug(genotype.stream().map(Object::toString)
                .collect(Collectors.joining(" ")));
        for (int i = 0; i < genotype.size(); i++) {
            if (RandomUtils.random.nextDouble() < mutationProbability) {
                if (genotype.get(i) == 1) genotype.set(i, 0);
                else genotype.set(i, 1);
            }
        }
        logger.debug("new");
        refreshCoordinates();
    }

    public String toString() {
        return "(" + vector.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + ")" +
                "(" + genotype.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + ")"
                + " val = " + objectiveFunctionValue;
    }

    public R calculateObjectiveFunction(ProblemReal<Double, R> problem) {
        logger.debug("Подсчёт значения целевой функции");
        refreshCoordinates();
        solution = problem.createSolution(vector);
        objectiveFunctionValue = problem.calculateObjectiveFunction(solution);
        logger.debug("Значение оптимизируемой функции: " + objectiveFunctionValue);
        return objectiveFunctionValue;
    }

}
