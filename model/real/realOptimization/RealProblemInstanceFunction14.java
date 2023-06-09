package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunction14 extends RealProblemInstance {

    @Override
    public String getName(){ return "Shekel's \"fox holes\""; }

    @Override
    public double getLeftBound() { return -65; }

    @Override
    public double getRightBound() {
        return 65;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    private List<Double> a1 = new ArrayList<>();
    private List<Double> a2 = new ArrayList<>();
    private static double coef = 1. / 500.;

    public RealProblemInstanceFunction14(){
        for (int i = 0; i < 5; i++){
            a1.add(-32.);
            a1.add(-16.);
            a1.add(0.);
            a1.add(16.);
            a1.add(32.);
        }
        for (int i = 0; i < 5; i++) a2.add(-32.);
        for (int i = 0; i < 5; i++) a2.add(-16.);
        for (int i = 0; i < 5; i++) a2.add(0.);
        for (int i = 0; i < 5; i++) a2.add(16.);
        for (int i = 0; i < 5; i++) a2.add(32.);
    }

    private double calc(double x, double y){
        double res = coef;
        for (int j = 1; j <= 25; j++){
            res = res + 1 / (j + Math.pow(x - a1.get(j - 1), 6) + Math.pow(y - a2.get(j - 1), 6));
        }
        return res;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double x = data.get(0);
        double y = data.get(1);
        return 1 / calc(x, y);
    }

    @Override
    public List<Double> getOptimalSolution() {
        List<Double> res = new ArrayList<Double>();
        res.add(-32.);
        res.add(-32.);
        return res;
    }

    @Override
    public double getOptimalValue() {
        return 1;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
