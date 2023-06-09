package scheduling.optimization.combinatorial.ACO.standard;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.AntBase;

import java.util.ArrayList;

public class Ant<R extends Number> extends AntBase<R> {

    public Ant(Problem<Integer, R> problem, ACOParameters parameters) {
        super(problem);

        alpha = parameters.alpha;
        beta = parameters.beta;
    }

    public Ant(Problem<Integer, R> problem, ACOParameters parameters, Solution<Integer, R> solution){
        super(problem, solution);

        alpha = parameters.alpha;
        beta = parameters.beta;
    }

    public void generateAnyPath(){
        path = new ArrayList<>();
        for (int i = 0; i < problem.getDimension(); i++){
            path.add(i);
        }
        solution = new Solution<>(path, problem.getWorst());
    }

    @Override
    public String toString(){
        return solution.toString();
    }
}
