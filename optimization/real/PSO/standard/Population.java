package scheduling.optimization.real.PSO.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PSO.PopulationBase;
import scheduling.optimization.real.ProblemReal;

public class Population<R extends Number> extends PopulationBase<R, Particle<R>, PSOParameters> {

    public Population(ProblemReal<Double, R> problem, int individualsAmount, PSOParameters psoParameters,
                      LoggerWrapper logger) {
        super(problem, individualsAmount, psoParameters, logger);
    }

    @Override
    public void addToGenerationNewIndividual(Solution<Double, R> solution) {
        generation.add(new Particle<R>(problem, solution, psoParameters, logger));
    }

    @Override
    public void addToGenerationNewIndividual() {
        Particle<R> particle = new Particle<R>(problem, psoParameters, logger);
        particle.calculateObjectiveFunction(problem);
        generation.add(particle);
    }
}
