package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction6 extends RealProblemInstance {

    public static double SQRT2 = Math.sqrt(2);

    @Override
    public String getName(){ return "Griewank function"; }

    @Override
    public double getLeftBound() { return -16; }

    @Override
    public double getRightBound() {
        return 16;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        return 10 - 10 / (0.005 * (x * x + y * y) - Math.cos(x) * Math.cos(y / SQRT2) + 2);
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
