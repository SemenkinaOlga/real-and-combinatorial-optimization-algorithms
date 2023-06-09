package scheduling.business.logic.model.real.realOptimization;

import java.util.List;

public abstract class RealProblemInstance {
    public abstract double getLeftBound();
    public abstract double getRightBound();
    public abstract int getDimension();
    public abstract Double calculateObjectiveFunction(List<Double> data);
    public abstract List<Double> getOptimalSolution();
    public abstract double getOptimalValue();
    public abstract boolean isOptimumKnown();
    public abstract String getName();
}
