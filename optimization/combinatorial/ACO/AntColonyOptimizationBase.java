package scheduling.optimization.combinatorial.ACO;

import scheduling.optimization.Parameter;
import scheduling.optimization.combinatorial.PopulationBasedCombinatorialAlgorithm;

import java.util.ArrayList;
import java.util.List;

public abstract class AntColonyOptimizationBase<R extends Number, T extends AntBase<R>> extends PopulationBasedCombinatorialAlgorithm<R>  implements ICooperativeACO<R, T> {
    private ACOParametersBase parametersBase;

    protected List<List<Double>> visibility;
    protected PheromoneMatrix<R, T> pheromoneMatrix;

    public AntColonyOptimizationBase(int iterationMax, int antAmount, ACOParametersBase parametersBase, String name)
    {
        super(name);
        this.iterationMax = new Parameter<>(iterationMax, "iterationMax");
        this.individualAmount = new Parameter<>(antAmount, "antAmount");
        this.parametersBase = parametersBase;
    }

    public void setInitialIndividualAmount(int individualAmount){
        this.individualAmount = new Parameter<>(individualAmount, "antAmount");
    }

    protected List<List<Double>> createVisibility() {
        List<List<Double>> result = new ArrayList<>();
        if (problem.hasDistances()) {
            for (int i = 0; i < problem.getDimension(); i++) {
                List<Double> current = new ArrayList<>();
                for (int j = 0; j < problem.getDimension(); j++) {
                    current.add(1. / problem.getDistance(i, j));
                }
                result.add(current);
            }
        } else {
            for (int i = 0; i < problem.getDimension(); i++) {
                List<Double> current = new ArrayList<>();
                for (int j = 0; j < problem.getDimension(); j++) {
                    if (i == j) {
                        current.add(0.0);
                    } else {
                        current.add(1.0);
                    }
                }
                result.add(current);
            }
        }
        return result;
    }

    protected void createPheromoneMatrix() {
        pheromoneMatrix = new PheromoneMatrix<>(problem.getDimension(), parametersBase);
    }

    @Override
    public PheromoneMatrix<R, T> getPheromoneMatrix() {
        return pheromoneMatrix;
    }

    @Override
    public void setPheromoneMatrix(PheromoneMatrix<R, T> pheromoneMatrix) {
        this.pheromoneMatrix = new PheromoneMatrix<>(pheromoneMatrix);
    }
}
