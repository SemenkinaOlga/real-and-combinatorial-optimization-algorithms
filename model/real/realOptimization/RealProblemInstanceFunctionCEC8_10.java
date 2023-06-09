package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC8_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Discus Function 10"; }

    @Override
    public double getLeftBound() { return -100; }

    @Override
    public double getRightBound() {
        return 100;
    }

    @Override
    public int getDimension() {
        return 10;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double sum = 0;
        double x = data.get(0);
        for (int i = 1; i < getDimension(); i++){
            sum +=  data.get(i) *  data.get(i);
        }
        return 1000000 * x * x + sum;
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        for (int i = 0; i < getDimension(); i++){
            res.add(0.);
        }
        return res;
    }

    @Override
    public double getOptimalValue() {
        return 0;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
