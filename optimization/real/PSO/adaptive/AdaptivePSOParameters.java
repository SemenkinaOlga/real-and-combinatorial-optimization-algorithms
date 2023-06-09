package scheduling.optimization.real.PSO.adaptive;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.ParameterVariants;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaptivePSOParameters implements IAlgorithmParameters {
    public ParameterVariants<Double> cognitiveParameter;
    public ParameterVariants<Double> socialParameter;
    protected double minProbability;
    public ParameterVariants<Double> w;

    public AdaptivePSOParameters() {
        socialParameter = new ParameterVariants<>(new ArrayList<>(Arrays.asList(0.25,0.5,0.75,1.0,1.25,1.5,1.8,2.0)));
        cognitiveParameter = new ParameterVariants<>(new ArrayList<>(Arrays.asList(0.25,0.5,0.75,1.0,1.25,1.5,1.8,2.0)));
        w = new ParameterVariants<>(new ArrayList<>(Arrays.asList(0.5,0.6,0.7,0.8,0.9,1.0)));
        minProbability = 0.05;
    }

    public double chooseCognitiveParameter() {
        return cognitiveParameter.chooseParameter();
    }

    public double chooseSocialParameter() { return socialParameter.chooseParameter(); }

    public double chooseW() { return w.chooseParameter(); }

    @Override
    public String toString() {
        return " адаптивный";
    }

    @Override
    public String getParametersString() {
        return "\t";
    }
}
