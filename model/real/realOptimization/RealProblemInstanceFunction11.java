package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction11 extends RealProblemInstance {

    @Override
    public String getName(){ return "Function 12"; }

    @Override
    public double getLeftBound() { return 0; }

    @Override
    public double getRightBound() {
        return 4;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        double x_square = x * x;
        double y_square = y * y;
        double xy = x * y;
        return 0.5 * (x_square + xy + y_square) *
                (1 + 0.5 * Math.cos(1.5 * x) * Math.cos(3.2 * xy) * Math.cos(3.14 * y) +
                        0.5 * Math.cos(2.2 * x) * Math.cos(4.8 * xy) * Math.cos(3.5 * y));
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
