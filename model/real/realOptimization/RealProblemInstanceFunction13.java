package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction13 extends RealProblemInstance {

    @Override
    public String getName(){ return "Additive potential function"; }

    @Override
    public double getLeftBound() { return 0; }

    @Override
    public double getRightBound() {
        return 4;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    private double z(double x) {
        return - 1. / (Math.pow(x - 1., 2.) + 0.2)
                - 1. / (2. * Math.pow(x - 2., 2.) + 0.15)
                - 1. / (3. * Math.pow(x - 3., 2.) + 0.3);
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        return z(x) + z(y);
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        res.add(2.);
        res.add(2.);
        return res;
    }

    @Override
    public double getOptimalValue() {
        return -15.6;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
