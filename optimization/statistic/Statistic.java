package scheduling.optimization.statistic;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Statistic<T, R extends Number> {
    Problem<T, R> problem;
    List<Solution<T, R>> solutions;
    List<R> values;
    Solution<T, R> bestSolution;
    R bestValue;
    private double mean;
    private double median;
    private double std;
    private boolean isChanged;
    private double reliability;
    private boolean isOptimumKnown;

    public Statistic() { }

    public Statistic(Problem<T, R> problem) {
        this.problem = problem;
        solutions = new ArrayList<>();
        values = new ArrayList<>();
        mean = 0;
        std = 0;
        isChanged = false;
        bestValue = problem.getWorst();
        bestSolution = new Solution<T, R>(new ArrayList<T>(), bestValue);
        reliability = 0;
        isOptimumKnown = problem.isOptimumKnown();
    }

    public void addSolution(Solution<T, R> solution) {
        solutions.add(solution);
        values.add(solution.getValue());
        isChanged = true;
        if (problem.compare(solution, bestSolution) > 0) {
            bestSolution = solution;
            bestValue = bestSolution.getValue();
        }
    }

    public Solution<T, R> getBestSolution() {
        return bestSolution;
    }

    public List<Solution<T, R>> getSolutions() {
        return solutions;
    }

    private void recalculate() {
        values.sort(Comparator.comparingDouble(Number::doubleValue));
        double size = values.size();

        mean = 0;
        reliability = 0;
        for (Solution<T, R> s : solutions) {
            R value = s.getValue();
            mean += value.doubleValue();
            if (isOptimumKnown){
                if (problem.checkProximity(s, problem.getOptimumSolution(), problem.getSolutionAccuracy())
                        || Math.abs(value.doubleValue() - (double)problem.getOptimum()) < (double)problem.getAccuracy()){
                    reliability += 1;
                }
            }
        }
        mean = mean / size;
        reliability = reliability / size;

        std = 0;
        for (int i = 0; i < size; i++) {
            std += Math.pow(solutions.get(i).getValue().doubleValue() - mean, 2);
        }
        std /= (size - 1);
        std = Math.sqrt(std);

        if (values.size() % 2 == 0)
            median = ((values.get(values.size() / 2).doubleValue()) + (values.get(values.size() / 2 - 1).doubleValue())) / 2.0;
        else
            median = values.get(values.size() / 2).doubleValue();

        isChanged = false;
    }

    public R getBestValue() {
        return bestValue;
    }

    public double getMean() {
        if (isChanged)
            recalculate();
        return mean;
    }

    public double getMedian() {
        if (isChanged)
            recalculate();
        return median;
    }

    public double getStd() {
        if (isChanged) {
            recalculate();
        }
        return std;
    }

    public double getReliability() {
        if (isChanged) {
            recalculate();
        }
        return reliability;
    }

    public boolean isOptimumKnown(){
        return isOptimumKnown;
    }

    @Override
    public String toString() {
        if (isChanged)
            recalculate();
        StringBuilder stringBuilder = new StringBuilder("Statistics:");
        if (isOptimumKnown){
            stringBuilder.append(" reliability = ");
            stringBuilder.append(reliability);
        }
        stringBuilder.append(" mean = ");
        stringBuilder.append(mean);
        stringBuilder.append(" std = ");
        stringBuilder.append(std);
        stringBuilder.append(" bestValue = ");
        stringBuilder.append(bestValue);
        stringBuilder.append(" median = ");
        stringBuilder.append(median);
        stringBuilder.append(" Solution: ");
        stringBuilder.append(bestSolution.toString());
        return stringBuilder.toString();
    }
}
