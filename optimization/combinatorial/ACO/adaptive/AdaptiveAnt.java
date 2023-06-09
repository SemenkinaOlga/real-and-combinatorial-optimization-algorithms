package scheduling.optimization.combinatorial.ACO.adaptive;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.AntBase;

import java.util.ArrayList;

public class AdaptiveAnt<R extends Number> extends AntBase<R> {

    public AdaptiveAnt(Problem<Integer, R> problem, AdaptiveACOParameters parameters) {
        super(problem);

        alpha = parameters.chooseAlpha();
        beta = parameters.chooseBeta();
    }

    public AdaptiveAnt(AdaptiveAnt<R> ant) {
        super(ant.problem);
        path = new ArrayList<>(ant.path);
        value = ant.value;
        fitness = ant.fitness;
        solution = new Solution<>(ant.solution);
        alpha = ant.alpha;
        beta = ant.beta;
    }

    public AdaptiveAnt(Problem<Integer, R> problem,
                       AdaptiveACOParameters parameters,
                       Solution<Integer, R> solution){
        super(problem, solution);

        alpha = parameters.chooseAlpha();
        beta = parameters.chooseBeta();
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
