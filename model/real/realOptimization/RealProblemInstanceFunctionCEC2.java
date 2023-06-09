package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC2 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Rastrigin’s Function"; }

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
        return x * x - 10 * Math.cos(2 * Math.PI * x) + 10 + y * y - 10 * Math.cos(2 * Math.PI * y) + 10;
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
