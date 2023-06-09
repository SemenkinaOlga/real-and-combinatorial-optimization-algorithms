package scheduling.optimization.combinatorial.ACO;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.standard.Ant;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ColonyBase<R extends Number, T extends AntBase<R>> {
    protected List<T> ants;
    protected Problem<Integer, R> problem;
    protected int dimension;
    protected int antAmount;

    public ColonyBase(int antAmount, Problem<Integer, R> problem) {
        this.problem = problem;
        dimension = problem.getDimension();
        this.antAmount = antAmount;
    }

    abstract public void initialization();

    public int size() {
        return antAmount;
    }

    public void calculateObjectiveFunction() {
        for (T ant : ants) {
            ant.calculateObjectiveFunction();
        }
    }

    public T findBest() {
        T ant = ants.get(0);

        for (int i = 1; i < ants.size(); i++) {
            T current = ants.get(i);
            if (current.compareTo(ant) > 0) {
                ant = current;
            }
        }

        return ant;
    }

    public void buildPath(PheromoneMatrix pheromone, List<List<Double>> visibility) {
        for (T ant : ants) {
            ant.buildPath(dimension, pheromone, visibility);
        }
    }

    public T getAnt(int i) {
        return ants.get(i);
    }

    public void removeAnts(int size) {
        if (size < antAmount) {
            antAmount -= size;
            ants.sort(Collections.reverseOrder());
            ants.subList(ants.size() - size, ants.size()).clear();
        }
    }

    public int chooseRandomAntIndex(){
        return RandomUtils.random.nextInt(ants.size());
    }

    public T chooseRandomAnt(){
        return ants.get(chooseRandomAntIndex());
    }

    public void replaceRandom(List<Solution<Integer, R>> solutions){
        List<Integer> antsIndexes = RandomUtils.getRandomIndexes(solutions.size(), ants.size());
        List<T> antsToRemove = new ArrayList<>();
        for (int index: antsIndexes) {
            antsToRemove.add(ants.get(index));
        }
        ants.removeAll(antsToRemove);

        for (Solution<Integer, R> solution: solutions) {
            addAnt(solution);
        }
    }

    public abstract void addAnt(Solution<Integer, R> solution);

    public void replaceWorst(List<Solution<Integer, R>> solutions){
        ants.sort(Collections.reverseOrder());
        ants.subList(ants.size() - solutions.size(), ants.size()).clear();
        for (Solution<Integer, R> solution: solutions) {
            addAnt(solution);
        }
    }

    public List<Solution<Integer, R>> getPopulation(){
        List<Solution<Integer, R>> solutions = new ArrayList<>();
        for (T ant: ants) {
            solutions.add(ant.getSolution());
        }
        return solutions;
    }

    public void setPopulation(List<Solution<Integer, R>> solutions){
        ants = new ArrayList<>();
        for (Solution<Integer, R> solution: solutions) {
            addAnt(solution);
        }
    }
}
