package scheduling.optimization.real.PSO.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.DEType;
import scheduling.optimization.real.DE.adaptive.AdaptiveIndividual;
import scheduling.optimization.real.PSO.PopulationBase;
import scheduling.optimization.real.ProblemReal;

import java.util.HashMap;
import java.util.Map;

public class AdaptivePopulation<R extends Number> extends PopulationBase<R, AdaptiveParticle<R>, AdaptivePSOParameters> {

    public AdaptivePopulation(ProblemReal<Double, R> problem, int individualsAmount,
                              AdaptivePSOParameters psoParameters, LoggerWrapper logger) {
        super(problem, individualsAmount, psoParameters, logger);
    }

    @Override
    public void addToGenerationNewIndividual() {
        AdaptiveParticle<R> particle = new AdaptiveParticle<>(problem, psoParameters, logger);
        particle.calculateObjectiveFunction(problem);
        logger.debug(particle.toString());
        generation.add(particle);
    }

    @Override
    public void addToGenerationNewIndividual(Solution<Double, R>  solution){
        generation.add(new AdaptiveParticle<>(problem, solution, psoParameters, logger));
    }

    public void
    updateOperatorProbability(int iterationMax, LoggerWrapper socialLogger, LoggerWrapper cognitiveLogger){
        Map<Double, Double> averageFitnessCognitiveParameter = new HashMap<>();
        Map<Double, Integer> countCognitiveParameter= new HashMap<>();
        Map<Double, Double> averageFitnessSocialParameter = new HashMap<>();
        Map<Double, Integer> countSocialParameter = new HashMap<>();
        Map<Double, Double> averageW = new HashMap<>();
        Map<Double, Integer> countW = new HashMap<>();

        for (double a : psoParameters.cognitiveParameter.getNames()) {
            averageFitnessCognitiveParameter.put(a, 0.);
            countCognitiveParameter.put(a, 0);
        }
        for (double b : psoParameters.socialParameter.getNames()) {
            averageFitnessSocialParameter.put(b, 0.);
            countSocialParameter.put(b, 0);
        }
        for (double c : psoParameters.w.getNames()) {
            averageW.put(c, 0.);
            countW.put(c, 0);
        }

        for (AdaptiveParticle<R> particle: generation) {
            double cognitiveParameter = particle.getCognitiveParameter();
            double socialParameter = particle.getSocialParameter();
            double w = particle.getW();

            double val = averageFitnessCognitiveParameter.get(cognitiveParameter);
            averageFitnessCognitiveParameter.replace(cognitiveParameter, val + particle.getObjectiveFunctionValue().doubleValue());
            countCognitiveParameter.replace(cognitiveParameter, countCognitiveParameter.get(cognitiveParameter) + 1);

            val = averageFitnessSocialParameter.get(socialParameter);
            averageFitnessSocialParameter.replace(socialParameter, val + particle.getObjectiveFunctionValue().doubleValue());
            countSocialParameter.replace(socialParameter, countSocialParameter.get(socialParameter) + 1);

            val = averageW.get(w);
            averageW.replace(w, val + particle.getObjectiveFunctionValue().doubleValue());
            countW.replace(w, countW.get(w) + 1);
        }

        updateOperatorProbability(iterationMax, psoParameters.cognitiveParameter,
                averageFitnessCognitiveParameter, countCognitiveParameter);
        //printParamsProbabilities(psoParameters.cognitiveParameter, cognitiveLogger);

        updateOperatorProbability(iterationMax, psoParameters.socialParameter,
                averageFitnessSocialParameter, countSocialParameter);
        //printParamsProbabilities(psoParameters.socialParameter, socialLogger);

        updateOperatorProbability(iterationMax, psoParameters.w,
                averageW, countW);
        //printParamsProbabilities(psoParameters.w, socialLogger);
    }

    private void printParamsProbabilities(ParameterVariants<Double> param, LoggerWrapper logger){
        StringBuilder sb = new StringBuilder("");
        for (Double name: param.getNames()) {
            sb.append(param.getProbabilitiesMap().get(name));
            sb.append("\t");
        }
        logger.info(sb.toString());
    }

    public <T> void updateOperatorProbability(int iterationMax, ParameterVariants<T> parameterVariants,
                                              Map<T, Double> averageFitnessMap, Map<T, Integer> countMap) {
        int n = parameterVariants.size();

        double minFit = Double.MAX_VALUE;
        T minFitKey = averageFitnessMap.keySet().stream().findFirst().get();

        for (Map.Entry<T, Double> entry : averageFitnessMap.entrySet()) {
            if (countMap.get(entry.getKey()) > 0) {
                double val = (double) entry.getValue() / (double) countMap.get(entry.getKey());
                entry.setValue(val);
                if (val < minFit) {
                    minFit = val;
                    minFitKey = entry.getKey();
                }
            }
        }

        int K = 5;
        Map<T, Double> probabilitiesMap = parameterVariants.getProbabilitiesMap();

        for (Map.Entry<T, Double> entry : averageFitnessMap.entrySet()) {
            T key = entry.getKey();
            if (key != minFitKey) {
                double tmp = Math.min(probabilitiesMap.get(key) - psoParameters.minProbability, (double) ((n - 1) * K) /
                        (double) (n * iterationMax));
                probabilitiesMap.replace(minFitKey, probabilitiesMap.get(minFitKey) + tmp);
                probabilitiesMap.replace(key, probabilitiesMap.get(key) - tmp);
            }
        }
    }

}
