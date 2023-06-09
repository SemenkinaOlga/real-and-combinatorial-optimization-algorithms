package scheduling.optimization.interaction.coevolution;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;

import java.util.List;

public interface ICoevolutionary<T, R> extends IICoevolutionary<R> {
    void setProblem(Problem<T, R> problem);
    Solution<T, R> getBest();
    Solution<T, R> getCurrentBest();
    Solution<T, R> getRandom();
    void replaceRandom(List<Solution<T, R>> solutions);
    void replaceWorst(List<Solution<T, R>> solutions);
    List<Solution<T, R>> getPopulation();
    void setPopulation(List<Solution<T, R>> solutions);
}
