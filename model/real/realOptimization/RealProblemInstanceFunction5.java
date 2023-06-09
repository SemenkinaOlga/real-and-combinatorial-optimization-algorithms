package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction5 extends RealProblemInstance {

    @Override
    public String getName(){ return "Rosenbrock function"; }

    @Override
    public double getLeftBound() { return -2; }

    @Override
    public double getRightBound() {
        return 2;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        return 100 * Math.pow(y - x * x, 2) + Math.pow(1 - x, 2);
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        res.add(1.);
        res.add(1.);
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
