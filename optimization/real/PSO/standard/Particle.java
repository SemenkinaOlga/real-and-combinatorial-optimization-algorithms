package scheduling.optimization.real.PSO.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PSO.ParticleBase;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.util.RandomUtils;

public class Particle<R extends Number> extends ParticleBase<R> {

    protected PSOParameters psoParameters;

    public Particle(ProblemReal<Double, R> problem, PSOParameters psoParameters, LoggerWrapper logger) {
        super(problem, logger);
        this.psoParameters = psoParameters;
    }

    public Particle(Particle<R> particle, PSOParameters psoParameters) {
        super(particle);
        this.psoParameters = psoParameters;
    }

    public Particle(ProblemReal<Double, R> problem, Solution<Double, R> solution,
                    PSOParameters psoParameters, LoggerWrapper logger) {
        super(problem, solution, logger);
        this.psoParameters = psoParameters;
    }

    @Override
    public void computeVelocity(ParticleBase<R> globalBest) {
        double gamma1, gamma2, newVelocity;
        for(int i = 0; i < velocity.size(); i++){
            gamma1 = RandomUtils.random.nextDouble();
            gamma2 = RandomUtils.random.nextDouble();
            newVelocity = psoParameters.getW() * velocity.get(i) +
                    gamma1 * psoParameters.getCognitiveParameter() * (localBestCoordinates.get(i) - vector.get(i))
                    + gamma2 * psoParameters.getSocialParameter() * (globalBest.getVector().get(i) - vector.get(i));
            velocity.set(i, newVelocity);
        }
    }
}
