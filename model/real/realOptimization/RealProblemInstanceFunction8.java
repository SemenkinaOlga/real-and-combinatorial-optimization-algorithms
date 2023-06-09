package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction8 extends RealProblemInstance {

    public static double SQRT5 = Math.sqrt(5);

    @Override
    public String getName(){ return "Katkovnik function"; }

    @Override
    public double getLeftBound() { return -2.5; }

    @Override
    public double getRightBound() {
        return 2.5;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        double A = 0.8;
        return 0.5 * (x * x + y * y) * A * (2 + Math.cos(1.5 * x) * Math.cos(3.14 * y) +
                Math.cos(SQRT5 * x) * Math.cos(3.5 * y));
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
