package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction1 extends RealProblemInstance {

    @Override
    public String getName(){ return "Function 1"; }

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
        double s = -2.77257 * x * x;
        return 0.05 * (x - 1) * (x - 1) + (3 - 2.9 * Math.exp(s))
                * (1 - Math.cos(x * (4 - 50 * Math.exp(s))));
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        res.add(0.9544);
        return res;
    }

    @Override
    public double getOptimalValue() {
        return 0.00017;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
