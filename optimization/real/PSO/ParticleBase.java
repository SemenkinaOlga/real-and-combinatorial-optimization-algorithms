package scheduling.optimization.real.PSO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.IndividualRoot;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.SolutionReal;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParticleBase<R extends Number> extends IndividualRoot<Double, R, ProblemReal<Double, R>> {

    protected List<Double> vector;
    protected List<Double> velocity;
    protected List<Double> localBestCoordinates;
    protected R localBestObjectiveFunctionValue;

    public ParticleBase(ProblemReal<Double, R> problem, LoggerWrapper logger) {
        super(problem, logger);

        vector = new ArrayList<>();
        velocity = new ArrayList<>();
        localBestCoordinates = new ArrayList<>();
        localBestObjectiveFunctionValue = problem.getWorst();

        for (int i = 0; i < dimension; i++) {
            double v = problem.getLeftBound() + (problem.getRightBound()  - problem.getLeftBound() ) * RandomUtils.random.nextDouble();
            vector.add(v);
            localBestCoordinates.add(v);
            velocity.add(0.);
        }
    }

    public ParticleBase(ParticleBase<R> particle) {
        super(particle.problem, particle.logger);
        this.objectiveFunctionValue = particle.objectiveFunctionValue;
        this.localBestObjectiveFunctionValue = particle.localBestObjectiveFunctionValue;
        solution = new SolutionReal<>(particle.solution);
        vector = new ArrayList<>();
        velocity = new ArrayList<>();
        localBestCoordinates = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            vector.add(particle.vector.get(i));
            velocity.add(particle.velocity.get(i));
            localBestCoordinates.add(particle.localBestCoordinates.get(i));
        }
    }

    public ParticleBase(ProblemReal<Double, R> problem, Solution<Double, R> solution, LoggerWrapper logger) {
        super(problem, solution, logger);

        velocity = new ArrayList<>(solution.getData().size());
        for (int i = 0; i < solution.getData().size(); i++){
            velocity.add(0.);
        }
        localBestCoordinates = new ArrayList<>(solution.getData());
        vector = new ArrayList<>(solution.getData());
        localBestObjectiveFunctionValue = objectiveFunctionValue;
    }

    public abstract void computeVelocity(ParticleBase<R> globalBest);

    public void computeNewCoordinates(){
        for(int i = 0; i < vector.size(); i++){
            vector.set(i, vector.get(i) + velocity.get(i));
            if (vector.get(i) > problem.getRightBound()) {
                vector.set(i, problem.getRightBound());
            }
            else if (vector.get(i) < problem.getLeftBound()) {
                vector.set(i, problem.getLeftBound());
            }
        }
    }

    public String toString() {
        return "(" + vector.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + ")"
                + " val = " + objectiveFunctionValue;
    }

    public R calculateObjectiveFunction(ProblemReal<Double, R> problem) {
        solution = problem.createSolution(vector);
        R newObjectiveFunctionValue = problem.calculateObjectiveFunction(solution);
        if (problem.compare(newObjectiveFunctionValue, localBestObjectiveFunctionValue) > 0){
            for (int i = 0; i < dimension; i++) {
                localBestCoordinates.set(i, vector.get(i));
            }
            localBestObjectiveFunctionValue = newObjectiveFunctionValue;
        }
        objectiveFunctionValue = newObjectiveFunctionValue;
        logger.debug("Значение оптимизируемой функции: " + objectiveFunctionValue);
        return objectiveFunctionValue;
    }

    public List<Double> getVector() {
        return vector;
    }

    public List<Double> getVelocity() {
        return velocity;
    }

}
