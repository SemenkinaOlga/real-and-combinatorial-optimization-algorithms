package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC4_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 HGBat Function 10"; }

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

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x;
        double x_square_sum = 0;
        double x_sum = 0;
        for (int i = 0; i < getDimension(); i++){
            x = data.get(i);
            x_square_sum += x * x;
            x_sum += x;
        }
        return Math.sqrt(Math.abs(Math.pow(x_square_sum, 2) - Math.pow(x_sum, 2))) +
                (0.5 * x_square_sum + x_sum) / (double)getDimension() + 0.5;
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
