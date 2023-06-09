package scheduling.optimization.real.PSO.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.PSO.ParticleBase;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.util.RandomUtils;

import java.util.stream.Collectors;

public class AdaptiveParticle<R extends Number> extends ParticleBase<R> {

    protected AdaptivePSOParameters adaptivePSOParameters;

    public double getCognitiveParameter() {
        return cognitiveParameter;
    }

    public double getSocialParameter() {
        return socialParameter;
    }

    public double getW() {
        return w;
    }

    double cognitiveParameter;
    double socialParameter;
    double w;

    public AdaptiveParticle(ProblemReal<Double, R> problem, AdaptivePSOParameters adaptivePSOParameters,
                            LoggerWrapper logger) {
        super(problem, logger);
        this.adaptivePSOParameters = adaptivePSOParameters;
        cognitiveParameter = 0.5;
        socialParameter = 0.5;
        w = 1.0;
    }

    public AdaptiveParticle(AdaptiveParticle<R> particle, AdaptivePSOParameters adaptivePSOParameters) {
        super(particle);
        this.adaptivePSOParameters = adaptivePSOParameters;
        cognitiveParameter = 0.5;
        socialParameter = 0.5;
        w = 1.0;
    }

    public AdaptiveParticle(ProblemReal<Double, R> problem, Solution<Double, R> solution,
                            AdaptivePSOParameters adaptivePSOParameters, LoggerWrapper logger) {
        super(problem, solution, logger);
        this.adaptivePSOParameters = adaptivePSOParameters;
        cognitiveParameter = 0.5;
        socialParameter = 0.5;
        w = 1.0;
    }

    @Override
    public void computeVelocity(ParticleBase<R> globalBest) {
        cognitiveParameter = adaptivePSOParameters.chooseCognitiveParameter();
        socialParameter = adaptivePSOParameters.chooseSocialParameter();
        w = adaptivePSOParameters.chooseW();
        double gamma1, gamma2, newVelocity;
        for(int i = 0; i < velocity.size(); i++){
            gamma1 = RandomUtils.random.nextDouble();
            gamma2 = RandomUtils.random.nextDouble();
            newVelocity = w * velocity.get(i) +
                    gamma1 * cognitiveParameter * (localBestCoordinates.get(i) - vector.get(i))
                    + gamma2 * socialParameter * (globalBest.getVector().get(i) - vector.get(i));
            velocity.set(i, newVelocity);
        }
    }
}
