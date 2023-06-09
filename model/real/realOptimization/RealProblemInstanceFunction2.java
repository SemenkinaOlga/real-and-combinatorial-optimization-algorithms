package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction2 extends RealProblemInstance {
    private static double SQRT5 = Math.sqrt(5);

    @Override
    public String getName(){ return "Function 2"; }

    @Override
    public double getLeftBound() {
        return -1;
    }

    @Override
    public double getRightBound() {
        return 1;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        return 1 - 0.5 * Math.cos(1.5 * (10 * x - 0.3)) * Math.cos(31.4 * x) +
                0.5 * Math.cos(SQRT5 * 10 * x) * Math.cos(35 * x);
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        res.add(-0.8113);
        return res;
    }

    @Override
    public double getOptimalValue() {
        return 0.1527;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
