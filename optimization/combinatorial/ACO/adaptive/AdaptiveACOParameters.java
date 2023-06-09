package scheduling.optimization.combinatorial.ACO.adaptive;

import scheduling.optimization.ParameterVariants;
import scheduling.optimization.combinatorial.ACO.ACOParametersBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdaptiveACOParameters extends ACOParametersBase {

    protected ParameterVariants<Double> alpha;
    protected ParameterVariants<Double> beta;
    protected double minProbability;

    public AdaptiveACOParameters() {
        super();
        alpha = new ParameterVariants<>(new ArrayList<>(Arrays.asList(1.,2.,5.,7.,10.)));
        beta = new ParameterVariants<>(new ArrayList<>(Arrays.asList(1.,2.,5.,7.,10.)));
        minProbability = 0.05;
    }

    public double chooseAlpha() {
        return alpha.chooseParameter();
    }

    public double chooseBeta() {
        return beta.chooseParameter();
    }

    @Override
    public String toString() {
        return "адаптивный {" +
                "α: " + alpha +
                ", β: " + beta +
                ", ρ: " + ro +
                ", Q: " + Q +
                ", минимальное кол-во феромона: " + pheromoneMinimum +
                ", максимальное кол-во феромона: " + pheromoneMaximum +
                ", минимальная вероятность: " + minProbability +
                '}';
    }

    @Override
    public String getParametersString() {
        return ro +
                "\t" +
                Q +
                "\t" +
                pheromoneMinimum +
                "\t" +
                pheromoneMaximum +
                "\t";
    }
}
