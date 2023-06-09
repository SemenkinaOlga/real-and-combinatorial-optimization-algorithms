package scheduling.optimization.combinatorial.ACO;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AntBase<R extends Number> implements Comparable<AntBase<R>>{
    protected Solution<Integer, R> solution;
    protected List<Integer> path;
    protected Problem<Integer, R> problem;
    protected R value;
    protected double fitness;

    protected double alpha;
    protected double beta;

    public Solution<Integer, R> getSolution() {
        return solution;
    }

    public R getValue() {
        return value;
    }

    public void setValue(R value) {

        this.value = value;
        fitness = 1. / (1. + value.doubleValue());
    }

    public double getFitness() {
        return fitness;
    }

    public AntBase(Problem<Integer, R> problem) {
        this.problem = problem;
        path = new ArrayList<>();
        path.add(RandomUtils.random.nextInt(problem.getDimension()));
        value = problem.getWorst();
        solution = new Solution<>(new ArrayList<>(), value);
    }

    public AntBase(Problem<Integer, R> problem, Solution<Integer, R> solution) {
        this.problem = problem;

        this.solution = new Solution<>(solution);
        path = new ArrayList<>(this.solution.getData());
        value = solution.getValue();
        fitness = 1. / (1. + value.doubleValue());
    }

    public R calculateObjectiveFunction() {
        solution = problem.createSolution(path);
        value = problem.calculateObjectiveFunction(solution);
        solution.setValue(value);
        fitness = 1. / (1. + value.doubleValue());
        return value;
    }

    public List<Integer> getPath() {
        return path;
    }

    public boolean containEdge(int first, int second) {
        int i = path.indexOf(first);

        if (i == path.size() - 1) {
            if (path.get(0) == second || path.get(i - 1) == second) {
                return true;
            }
        } else if (i == 0) {
            if (path.get(path.size() - 1) == second || path.get(i + 1) == second) {
                return true;
            }
        } else if (path.get(i + 1) == second || path.get(i - 1) == second) {
            return true;
        }
        return false;
    }

    public void buildPath(int n, PheromoneMatrix pheromone, List<List<Double>> visibility) {
        int currentCity = path.get(0);

        for (int j = 1; j < n; j++) {
            List<Double> probabilities = getProbabilities(n, pheromone, currentCity, visibility.get(currentCity));
            int i = RandomUtils.chooseFromProbabilities(probabilities);
            path.add(i);
            currentCity = i;
        }
        int zeroes = 0;
        for (int i : path) {
            if (i == 0)
                zeroes++;
        }
        if (zeroes > 1) {
            //Too many zeros in ant path!
            int a = 40;
        }
    }

    public List<Double> getProbabilities(int n, PheromoneMatrix pheromone, int currentCity, List<Double> visibility) {
        List<Double> probabilities = new ArrayList<>();

        double sum = 0.0;
        for (int k = 0; k < n; k++) {
            if (!path.contains(k)) {
                double val = Math.pow(pheromone.Get(currentCity, k), alpha) *
                        Math.pow(visibility.get(k), beta);
                sum += val;
                probabilities.add(val);
            } else {
                probabilities.add(0.0);
            }
        }

        for (int k = 0; k < n; k++) {
            double val = probabilities.get(k) / sum;
            probabilities.set(k, val);
        }

        return probabilities;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    @Override
    public int compareTo(AntBase<R> ant) {
        return problem.compare(this.value, ant.value);
    }
}
