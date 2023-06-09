package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC5 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Rosenbrock’s Function"; }

    @Override
    public double getLeftBound() { return -100; }

    @Override
    public double getRightBound() {
        return 100;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        double tmp = x * x - y;
        return 100 * tmp * tmp + (x - 1) * (x - 1);
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        res.add(0.);
        res.add(0.);
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
