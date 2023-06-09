package scheduling.business.logic.model.real.realOptimization;

import java.util.ArrayList;
import java.util.List;

public class RealProblemInstanceFunctionCEC10_10 extends RealProblemInstance {

    List<Double> alpha;
    List<Double> beta;
    int kmax = 20;
    double weierstrassCoefficient;

    @Override
    public String getName(){ return "CEC2021 Weierstrass Function"; }

    @Override
    public double getLeftBound() { return -100; }

    @Override
    public double getRightBound() {
        return 100;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    public RealProblemInstanceFunctionCEC10_10(){
        alpha = new ArrayList<>();
        beta = new ArrayList<>();
        weierstrassCoefficient = 0;
        for (int k = 0; k <= kmax; k++){
            alpha.add(Math.pow(0.5, k));
            beta.add(2 * Math.PI * Math.pow(3, k));
            weierstrassCoefficient = weierstrassCoefficient + alpha.get(k) * Math.cos(0.5 * beta.get(k));
        }
        weierstrassCoefficient = weierstrassCoefficient * getDimension();

    }

    private double weierstrassPart(double x) {
        double res = 0;
        for (int k = 0; k <= kmax; k++) {
            res = res + alpha.get(k) * Math.cos(beta.get(k) * (x + 0.5));
        }
        return res;
    }

    @Override
    public Double calculateObjectiveFunction(List<Double> data) {
        double res = 0;
        for (int i = 0; i < getDimension(); i++){
            res += weierstrassPart(data.get(i));
        }
        return res - weierstrassCoefficient;
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
