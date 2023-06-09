package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction7 extends RealProblemInstance {

    @Override
    public String getName(){ return "Sombrero function"; }

    @Override
    public double getLeftBound() { return -10; }

    @Override
    public double getRightBound() {
        return 10;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        double tmp = x * x + y * y;
        return  (1 - Math.pow(Math.sin(Math.sqrt(tmp)), 2)) / (1 + 0.001 * tmp);
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
