package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC9_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Expanded Schaffer’s Function 10"; }

    @Override
    public double getLeftBound() { return -100; }

    @Override
    public double getRightBound() {
        return 100;
    }

    @Override
    public int getDimension() {
        return 10;
    }

    private double schafferFunction(double x, double y) {
        double x_2_y_2 = x * x + y * y;
        return 0.5 + (Math.pow(Math.sin(Math.sqrt(x_2_y_2)), 2) - 0.5) / (Math.pow(1 + 0.001 * x_2_y_2, 2));
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double res = 0;
        for (int i = 0; i < getDimension() - 1; i++){
            res += schafferFunction(data.get(i), data.get(i + 1));
        }
        return res + schafferFunction(data.get(getDimension() -1), data.get(0));
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        for (int i = 0; i < getDimension(); i++){
            res.add(0.);
        }
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
