package scheduling.business.logic.model.real.realOptimization;

import scheduling.business.logic.model.ProblemType;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.SolutionReal;

import java.util.List;

public class ProblemRealOptimization implements ProblemReal<Double, Double> {
    protected int dimension;
    protected int count;
    protected RealProblemInstance realProblemInstance;

    public ProblemRealOptimization(int count) {
        this.count = count;
        realProblemInstance = RealProblemInstanceFactory.getRealProblemInstance(count);
        dimension = realProblemInstance.getDimension();
    }

    public Double getWorst(){
        return Double.MAX_VALUE;
    }

    public ProblemType getProblemType(){
        return ProblemType.REAL_OPTIMIZATION;
    }

    public String getName(){
        return realProblemInstance.getName();
    }

    @Override
    public boolean isOptimumKnown() {
        return true;
    }

    @Override
    public Double getOptimum() { return realProblemInstance.getOptimalValue(); }

    @Override
    public boolean checkProximity(Solution<Double, Double> first, Solution<Double, Double> second, Double accuracy){
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
    public Solution<Double,Double> getOptimumSolution(){
        return new SolutionReal<>(realProblemInstance.getOptimalSolution(), realProblemInstance.getOptimalValue());
    }

    @Override
    public double getLeftBound() { return realProblemInstance.getLeftBound(); }

    @Override
    public double getRightBound() {
        return realProblemInstance.getRightBound();
    }

    @Override
    public Double getAccuracy(){ return 0.01; }

    @Override
    public Double getSolutionAccuracy(){ return 0.01; }

    public ProblemRealOptimization copy(){
        return new ProblemRealOptimization(count);
    }

    public int getDimension(){
        return dimension;
    }

    @Override
    public int compare(Solution<Double, Double> first, Solution<Double, Double> second){
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

    public Solution<Double, Double> createSolution(List<Double> data){
        return new SolutionReal<>(data, Double.MAX_VALUE);
    }

    public Double calculateObjectiveFunction(Solution<Double, Double> solution) {
        List<Double> data = solution.getData();
        double res = realProblemInstance.calculateObjectiveFunction(data);
        solution.setValue(res);
        return res;
    }
}
