package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction4 extends RealProblemInstance {

    @Override
    public String getName(){ return "Rastrigin ravine function with rotation of the axes"; }

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
        double Kx = 1.5, Ky = 0.8;
        double A = -y, B = x;
        return Math.pow(0.1 * Kx * A, 2) + Math.pow(0.1 * Ky * B, 2)
                - 4 * Math.cos(0.8 * Kx * A) - 4 * Math.cos(0.8 * Ky * B) + 8;
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
