package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction10 extends RealProblemInstance {

    @Override
    public String getName(){ return "Function 11"; }

    @Override
    public double getLeftBound() { return -4; }

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
        return x_square * Math.abs(Math.sin(2 * x)) + y_square * Math.abs(Math.sin(2 * y))
                - 1 / (5 * x_square + 5 * y_square + 0.2) + 5;
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
