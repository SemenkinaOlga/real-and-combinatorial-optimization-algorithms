package scheduling.optimization;

import scheduling.bean.algorithm.util.LoggerWrapper;

public abstract class IndividualRoot <Data, Value extends Number, Prob extends Problem<Data, Value>> {

    protected LoggerWrapper logger;
    protected Value objectiveFunctionValue;
    protected int dimension;
    protected Prob problem;
    protected Solution<Data, Value> solution;

    public IndividualRoot(Prob problem, LoggerWrapper logger) {
        this.logger = logger;
        this.problem = problem;
        this.dimension = problem.getDimension();
        this.objectiveFunctionValue = problem.getWorst();
    }

    public IndividualRoot(Prob problem, Solution<Data, Value> solution, LoggerWrapper logger) {
        this.logger = logger;
        this.problem = problem;
        this.dimension = problem.getDimension();
        this.solution = new Solution<>(solution);
        objectiveFunctionValue = solution.getValue();
    }

    public Prob getProblem(){ return problem; }

    public Solution<Data, Value> getSolution() {
        return solution;
    }

    public int getDimension() {
        return dimension;
    }

    public int compareTo(IndividualRoot<Data, Value, Prob> individual) {
        return problem.compare(objectiveFunctionValue, individual.getObjectiveFunctionValue());
    }

    public Value getObjectiveFunctionValue() {
        return objectiveFunctionValue;
    }

    public void setObjectiveFunctionValue(Value objectiveFunctionValue) {
        this.objectiveFunctionValue = objectiveFunctionValue;
    }

    public abstract Value calculateObjectiveFunction(Prob problem);
}
