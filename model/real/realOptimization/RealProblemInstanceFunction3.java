package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction3 extends RealProblemInstance {

    @Override
    public String getName(){ return "Rastrigin function"; }

    @Override
    public double getLeftBound() { return -16; }

    @Override
    public double getRightBound() {
        return 16;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        return 0.1 * x * x + 0.1 * y * y - 4 * Math.cos(0.8 * x) - 4 * Math.cos(0.8 * y) + 8;
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
