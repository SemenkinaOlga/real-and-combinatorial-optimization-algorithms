package scheduling.optimization.combinatorial.ACO.adaptive;

import scheduling.optimization.ParameterVariants;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.ColonyBase;
import scheduling.optimization.combinatorial.ACO.standard.Ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdaptiveColony<R extends Number> extends ColonyBase<R, AdaptiveAnt<R>> {

    private AdaptiveACOParameters parameters;

    public AdaptiveColony(int antAmount, Problem<Integer, R> problem, AdaptiveACOParameters parameters) {
        super(antAmount, problem);
        this.parameters = parameters;
    }

    @Override
    public void initialization() {
        ants = new ArrayList<>();
        for (int i = 0; i < antAmount; i++) {
            ants.add(new AdaptiveAnt<>(problem, parameters));
        }
    }

    public void updateOperatorProbability(int iterationMax) {
        Map<Double, Double> averageFitnessAlpha = new HashMap<>();
        Map<Double, Double> averageFitnessBeta = new HashMap<>();
        Map<Double, Integer> countAlpha = new HashMap<>();
        Map<Double, Integer> countBeta = new HashMap<>();

        for (double a : parameters.alpha.getNames()) {
            averageFitnessAlpha.put(a, 0.);
            countAlpha.put(a, 0);
        }
        for (double b : parameters.beta.getNames()) {
            averageFitnessBeta.put(b, 0.);
            countBeta.put(b, 0);
        }

        for (AdaptiveAnt<R> ant : ants) {
            double alpha = ant.getAlpha();
            double beta = ant.getBeta();

            double val = averageFitnessAlpha.get(alpha);
            averageFitnessAlpha.replace(alpha, val + ant.getValue().doubleValue());
            countAlpha.replace(alpha, countAlpha.get(alpha) + 1);

            val = averageFitnessBeta.get(beta);
            averageFitnessBeta.replace(beta, val + ant.getValue().doubleValue());
            countBeta.replace(beta, countBeta.get(beta) + 1);
        }

        updateOperatorProbability(iterationMax, parameters.alpha,
                averageFitnessAlpha, countAlpha);
        updateOperatorProbability(iterationMax, parameters.beta,
                averageFitnessBeta, countBeta);

    }

    public void updateOperatorProbability(int iterationMax, ParameterVariants<Double> parameterVariants,
                                          Map<Double, Double> averageFitnessMap, Map<Double, Integer> countMap) {
        int n = parameterVariants.size();

        double minFit = Double.MAX_VALUE;
        double minFitKey = averageFitnessMap.keySet().stream().findFirst().get();

        for (Map.Entry<Double, Double> entry : averageFitnessMap.entrySet()) {
            if (countMap.get(entry.getKey()) > 0) {
                double val = (double) entry.getValue() / (double) countMap.get(entry.getKey());
                entry.setValue(val);
                if (val < minFit) {
                    minFit = val;
                    minFitKey = entry.getKey();
                }
            }
        }

        int K = 2;
        Map<Double, Double> probabilitiesMap = parameterVariants.getProbabilitiesMap();

        for (Map.Entry<Double, Double> entry : averageFitnessMap.entrySet()) {
            double key = entry.getKey();
            if (key != minFitKey) {
                double tmp = Math.min(probabilitiesMap.get(key) - parameters.minProbability, (double) ((n - 1) * K) /
                        (double) (n * iterationMax));
                probabilitiesMap.replace(minFitKey, probabilitiesMap.get(minFitKey) + tmp);
                probabilitiesMap.replace(key, probabilitiesMap.get(key) - tmp);
            }
        }
    }

    public void addAnts(int size) {
        antAmount += size;
        for (int i = 0; i < size; i++) {
            AdaptiveAnt<R> ant = new AdaptiveAnt<>(problem, parameters);
            ant.generateAnyPath();
            ants.add(ant);
        }
    }

    public void addAnt(Solution<Integer, R> solution){
        ants.add(new AdaptiveAnt<>(problem, parameters, solution));
    }
}
