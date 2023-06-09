package scheduling.optimization.real.DE.adaptive;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.real.DE.DEType;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaptiveDEParameters implements IAlgorithmParameters {
    public ParameterVariants<Double> CR;
    // it is actually F, but xhtml bind doesn't work with this letter, lol
    public ParameterVariants<Double> FF;
    public ParameterVariants<DEType> deType;
    public ParameterVariants<Double> lymbda;
    protected double minProbability;

    public AdaptiveDEParameters() {
        super();
        CR = new ParameterVariants<>(new ArrayList<>(Arrays.asList(0.3,0.5,0.7,0.9,1.)));
        FF = new ParameterVariants<>(new ArrayList<>(Arrays.asList(0.2,0.5,0.7,1.,1.5)));
        lymbda = new ParameterVariants<>(new ArrayList<>(Arrays.asList(0.2,0.5,0.7,1.,1.5)));
        deType =  new ParameterVariants<>(new ArrayList<>(Arrays.asList(DEType.BY_RANDOM, DEType.BY_BEST)));
        minProbability = 0.05;
    }

    public double chooseCR() {
        return CR.chooseParameter();
    }

    public double chooseFF() {
        return FF.chooseParameter();
    }

    public DEType chooseDeType() {
        return deType.chooseParameter();
    }

    public double chooseLymbda() {
        return lymbda.chooseParameter();
    }

    @Override
    public String toString() {
        return "адаптивный ";
    }

    @Override
    public String getParametersString() {
        return "\t";
    }
}
