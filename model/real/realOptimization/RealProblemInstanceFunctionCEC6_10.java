package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC6_10 extends RealProblemInstance {

    @Override
    public String getName(){ return "CEC2021 Ackley’s Function 10"; }

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
            sum2 += Math.cos(2 * Math.PI * data.get(i));
        }
        sum1 = sum1 / (double)getDimension();
        sum2 = sum2 / (double)getDimension();
        return -20 * Math.exp(-0.2 * Math.sqrt(sum1)) - Math.exp(sum2) + 20 + Math.E;
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
