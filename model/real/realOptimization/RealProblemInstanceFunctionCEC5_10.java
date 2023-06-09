package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC5_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Rosenbrock’s Function 10"; }

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
        double x_1;
        double res = 0;
        for (int i = 0; i < getDimension() - 1; i++){
            x = data.get(i);
            x_1 = data.get(i + 1);
            res += 100 * Math.pow(x * x - x_1, 2) + Math.pow(x - 1, 2);
        }
        return res;
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
