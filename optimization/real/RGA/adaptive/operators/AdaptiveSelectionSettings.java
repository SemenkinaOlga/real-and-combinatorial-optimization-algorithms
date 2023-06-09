package scheduling.optimization.real.RGA.adaptive.operators;

import scheduling.optimization.ParameterVariants;
import scheduling.optimization.real.RGA.operators.RankingSelectionType;
import scheduling.optimization.real.RGA.operators.SelectionType;
import scheduling.optimization.real.RGA.standard.operators.SelectionSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class AdaptiveSelectionSettings {

    public ParameterVariants<SelectionSettings> settings;
    protected double minProbability;

    public AdaptiveSelectionSettings() {
        settings = new ParameterVariants<>(new ArrayList<>(Arrays.asList(
                new SelectionSettings(SelectionType.TOURNAMENT, 2, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.TOURNAMENT, 4, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.TOURNAMENT, 6, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.PROPORTIONAL, 1, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.LINEAR, 1),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.EXPONENTIAL, 0.95),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.EXPONENTIAL, 0.9),
                new SelectionSettings(SelectionType.RANKING, 1, RankingSelectionType.EXPONENTIAL, 0.8))));
        minProbability = 0.05;
    }

    public SelectionSettings chooseSelectionSettings() {
        return settings.chooseParameter();
    }

    public String toString() {
        return "адаптивная селекция";
    }

    public String getParametersString() {
        return "\t";
    }
}
