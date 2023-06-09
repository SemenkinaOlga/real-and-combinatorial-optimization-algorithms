package scheduling.optimization.real.PSO;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.PopulationRoot;
import scheduling.optimization.real.ProblemReal;

public abstract class PopulationBase<R extends Number, Ind extends ParticleBase<R>, ParamsType>
        extends PopulationRoot<Double, R, ProblemReal<Double, R>, Ind> {

    protected ParamsType psoParameters;

    public PopulationBase(ProblemReal<Double, R> problem, int individualsAmount, ParamsType psoParameters,
                          LoggerWrapper logger) {
        super(problem, individualsAmount, logger);
        this.psoParameters = psoParameters;

        for (int i = 0; i < individualsAmount; i++) {
            addToGenerationNewIndividual();
        }
    }

    public void calculateObjectiveFunction() {
        for (Ind particle : generation) {
            particle.calculateObjectiveFunction(problem);
        }
    }

}
