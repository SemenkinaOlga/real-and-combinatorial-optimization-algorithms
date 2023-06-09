package scheduling.optimization.real.RGA.adaptive.operators;

import scheduling.optimization.ParameterVariants;
import scheduling.optimization.real.RGA.operators.RecombinationType;
import scheduling.optimization.real.RGA.standard.operators.RecombinationSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaptiveRecombinationSettings {
    public ParameterVariants<RecombinationSettings> settings;
    protected double minProbability;

    public AdaptiveRecombinationSettings(){
        settings = new ParameterVariants<>(new ArrayList<>(Arrays.asList(
                new RecombinationSettings(RecombinationType.UNIFORM),
                new RecombinationSettings(RecombinationType.ONEPOINT),
                new RecombinationSettings(RecombinationType.TWOPOINT))));
        minProbability = 0.05;
    }

    public RecombinationSettings chooseRecombinationSettings() {
        return settings.chooseParameter();
    }

    public String toString() {
        return "адаптивная рекомбинация";
    }

    public String getParametersString() {
        return "\t";
    }
}
