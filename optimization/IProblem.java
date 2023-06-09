package scheduling.optimization;

import scheduling.business.logic.model.ProblemType;

public interface IProblem {
    int getDimension();
    ProblemType getProblemType();
    boolean isOptimumKnown();
    String getName();
}
