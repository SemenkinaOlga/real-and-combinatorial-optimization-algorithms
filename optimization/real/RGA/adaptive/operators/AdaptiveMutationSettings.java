package scheduling.optimization.real.RGA.adaptive.operators;

import scheduling.optimization.ParameterVariants;
import scheduling.optimization.real.RGA.operators.MutationType;
import scheduling.optimization.real.RGA.standard.operators.MutationSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaptiveMutationSettings {
    public ParameterVariants<MutationSettings> settings;
    protected double minProbability;

    public AdaptiveMutationSettings(){
        settings = new ParameterVariants<>(new ArrayList<>(Arrays.asList(
                new MutationSettings(MutationType.VERYLOW),
                new MutationSettings(MutationType.LOW),
                new MutationSettings(MutationType.AVERAGE),
                new MutationSettings(MutationType.HIGH),
                new MutationSettings(MutationType.VERYHIGH))));
        minProbability = 0.05;
    }

    public MutationSettings chooseMutationSettings() {
        return settings.chooseParameter();
    }

    public double getFinalProbability(int bitNumberPerVariable, int dimension, MutationSettings mutationSettings){
        double mutationProbability = 1 / (double)(dimension * bitNumberPerVariable);
        switch (mutationSettings.getMutationType()){
            case VERYLOW:
                return mutationProbability * 0.2;
            case LOW:
                return mutationProbability * 0.5;
            case AVERAGE:
                return mutationProbability;
            case HIGH:
                return mutationProbability * 2;
            case VERYHIGH:
                return mutationProbability * 5;
        }
        return mutationProbability;
    }

    public String toString() {
        return "адаптивная мутация";
    }

    public String getParametersString() {
        return "\t";
    }
}
