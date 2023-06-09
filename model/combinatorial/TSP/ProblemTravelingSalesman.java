package scheduling.business.logic.model.combinatorial.TSP;

import scheduling.business.logic.model.ProblemType;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ProblemCombinatorial;
import scheduling.optimization.real.SolutionReal;

import java.util.List;

public class ProblemTravelingSalesman implements ProblemCombinatorial<Integer, Double> {
    protected int dimension;
    protected int count;
    protected TSPInstance tspInstance;

    public ProblemTravelingSalesman(int count) {
        this.count = count;
        tspInstance = TSPInstanceFactory.getTSPInstanceInstance(count);
        dimension = tspInstance.getDimension();
    }

    public Double getWorst(){
        return Double.MAX_VALUE;
    }

    public ProblemType getProblemType(){
        return ProblemType.TRAVELLING_SALESMAN_PROBLEM;
    }

    public String getName(){
        return tspInstance.getName();
    }

    @Override
    public boolean isOptimumKnown() {
        return true;
    }

    @Override
    public Double getOptimum() { return tspInstance.getOptimalValue(); }

    @Override
    public Solution<Integer,Double> getOptimumSolution(){
        return new SolutionReal<>(tspInstance.getOptimalSolution(), tspInstance.getOptimalValue());
    }

    @Override
    public Double getAccuracy(){ return 0.01; }

    public ProblemTravelingSalesman copy(){
        return new ProblemTravelingSalesman(count);
    }

    public int getDimension(){
        return dimension;
    }

    @Override
    public int compare(Solution<Integer, Double> first, Solution<Integer, Double> second){
        return compare(first.getValue(), second.getValue());
    }

    @Override
    public int compare(Double first, Double second) {
        if (first < second){
            return 1;
        } else if (first.equals(second)){
            return 0;
        }
        return -1;
    }

    public Solution<Integer, Double> createSolution(List<Integer> data){
        return new SolutionReal<>(data, Double.MAX_VALUE);
    }

    public Double calculateObjectiveFunction(Solution<Integer, Double> solution) {
        List<Integer> data = solution.getData();
        double res = tspInstance.calculateObjectiveFunction(data);
        solution.setValue(res);
        return res;
    }

    @Override
    public Integer getSolutionAccuracy(){ return 0; }

    @Override
    public boolean checkProximity(Solution<Integer, Double> first, Solution<Integer, Double> second, Integer accuracy){
        boolean check = true;
        for (int i = 0; i < first.getData().size(); i++){
            if (Math.abs(first.getData().get(i) - second.getData().get(i)) > accuracy){
                check = false;
                break;
            }
        }
        return check;
    }

    @Override
    public boolean hasDistances() {
        return true;
    }

    @Override
    public double getDistance(Integer first, Integer second) {
        return tspInstance.getDistance(first, second);
    }
}
