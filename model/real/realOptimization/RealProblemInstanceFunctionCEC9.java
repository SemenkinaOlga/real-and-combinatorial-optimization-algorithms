package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC9 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Expanded Schaffer’s Function"; }

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

    private double schafferFunction(double x, double y) {
        double x_2_y_2 = x * x + y * y;
        return 0.5 + (Math.pow(Math.sin(Math.sqrt(x_2_y_2)), 2) - 0.5) / (Math.pow(1 + 0.001 * x_2_y_2, 2));
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        return schafferFunction(x, y) + schafferFunction(y, x);
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
