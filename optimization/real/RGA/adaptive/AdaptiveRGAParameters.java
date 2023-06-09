package scheduling.optimization.real.RGA.adaptive;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.ParameterVariants;
import scheduling.optimization.real.RGA.RGAParametersBase;
import scheduling.optimization.real.RGA.operators.MutationType;
import scheduling.optimization.real.RGA.operators.RankingSelectionType;
import scheduling.optimization.real.RGA.operators.RecombinationType;
import scheduling.optimization.real.RGA.operators.SelectionType;
import scheduling.optimization.real.RGA.standard.operators.MutationSettings;
import scheduling.optimization.real.RGA.standard.operators.RecombinationSettings;
import scheduling.optimization.real.RGA.standard.operators.SelectionSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaptiveRGAParameters extends RGAParametersBase implements IAlgorithmParameters {

    public ParameterVariants<SelectionSettings> selectionSettings;
    public ParameterVariants<RecombinationSettings> recombinationSettings;
    public ParameterVariants<MutationSettings> mutationSettings;
    public double minProbability;

    public AdaptiveRGAParameters() {
        super();
        selectionSettings = new ParameterVariants<>(new ArrayList<>(Arrays.asList(
                new SelectionSettings(SelectionType.TOURNAMENT, 2, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.TOURNAMENT, 4, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.TOURNAMENT, 6, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.PROPORTIONAL, 1, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.EXPONENTIAL, 0.95),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.EXPONENTIAL, 0.9),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.EXPONENTIAL, 0.8))));
        recombinationSettings = new ParameterVariants<>(new ArrayList<>(Arrays.asList(
                new RecombinationSettings(RecombinationType.UNIFORM),
                new RecombinationSettings(RecombinationType.ONEPOINT),
                new RecombinationSettings(RecombinationType.TWOPOINT))));
        mutationSettings = new ParameterVariants<>(new ArrayList<>(Arrays.asList(
                new MutationSettings(MutationType.VERYLOW),
                new MutationSettings(MutationType.LOW),
                new MutationSettings(MutationType.AVERAGE),
                new MutationSettings(MutationType.HIGH),
                new MutationSettings(MutationType.VERYHIGH))));
        minProbability = 0.05;
    }

    public SelectionSettings chooseSelectionSettings() {
        return selectionSettings.chooseParameter();
    }

    public RecombinationSettings chooseRecombinationSettings() {
        return recombinationSettings.chooseParameter();
    }

    public MutationSettings chooseMutationSettings() {
        return mutationSettings.chooseParameter();
    }

}
