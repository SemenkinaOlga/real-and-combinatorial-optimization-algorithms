package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC7_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Happycat Function 10"; }

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
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < getDimension(); i++){
            sum1 += Math.pow(data.get(i), 2);
            sum2 += data.get(i);
        }
        return Math.sqrt(Math.sqrt(Math.abs(sum1 - getDimension()))) +
                (0.5 * sum1 + sum2) / ((double)getDimension()) + 0.5;
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
