package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC3_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 High Conditioned Elliptic Function 10"; }

    @Override
    public double getLeftBound() { return -100; }

    @Override
    public double getRightBound() {
        return 100;
    }

    @Override
    public int getDimension() { return 10; }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x;
        double res = 0;
        for (int i = 0; i < getDimension(); i++){
            x = data.get(i);
            res += Math.pow(1000000, ((double)(i - 1)) / (double)(getDimension() - 1)) * x * x;
        }
        return res;
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
