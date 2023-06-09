package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC4 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 HGBat Function"; }

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
        double x_square = x * x;
        double y_square = y * y;
        double x2_plus_y2 = x_square + y_square;
        return Math.sqrt(Math.abs(x2_plus_y2 * x2_plus_y2 - (x + y) * (x + y))) + (0.5 * x2_plus_y2 + x + y) / 2 + 0.5;
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
