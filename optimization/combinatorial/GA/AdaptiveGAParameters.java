package scheduling.optimization.combinatorial.GA;

import scheduling.optimization.combinatorial.GA.settings.MutationSettings;
import scheduling.optimization.combinatorial.GA.settings.RecombinationSettings;
import scheduling.optimization.combinatorial.GA.settings.SelectionSettings;
import scheduling.optimization.combinatorial.GA.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdaptiveGAParameters extends GAParametersBase {

    private List<SelectionSettings> selections = new ArrayList<>();
    private List<RecombinationSettings> recombinations = new ArrayList<>();
    private List<MutationSettings> mutations = new ArrayList<>();

    public AdaptiveGAParameters() {
        super();
        initSelections();
        initMutations();
        recombinations.add( new RecombinationSettings(RecombinationType.TYPICAL));
        selfConfiguringType = SelfConfiguringType.TYPICAL;
    }

    private void initSelections() {
        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.TOURNAMENT)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.LINEAR)
                .weight(0.8));
        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.TOURNAMENT)
                .tournamentSize(4)
                .rankingSelectionType(RankingSelectionType.LINEAR)
                .weight(0.8));
        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.TOURNAMENT)
                .tournamentSize(8)
                .rankingSelectionType(RankingSelectionType.LINEAR)
                .weight(0.8));

        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.PROPORTIONAL)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.LINEAR)
                .weight(0.8));

        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.RANKING)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.LINEAR)
                .weight(0.8));

        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.RANKING)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.EXPONENTIAL)
                .weight(0.5));
        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.RANKING)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.EXPONENTIAL)
                .weight(0.8));
        selections.add(new SelectionSettings()
                .withSelectionType(SelectionType.RANKING)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.EXPONENTIAL)
                .weight(0.9));

    }

    private void initMutations() {
        List<MutationType> mutationTypes = new ArrayList<>(4);
        mutationTypes.addAll(Arrays.asList(MutationType.values()));

        List<MutationProbabilityType> mutationProbabilityTypes = new ArrayList<>(5);
        mutationProbabilityTypes.addAll(Arrays.asList(MutationProbabilityType.values()));

        for (MutationType type: mutationTypes)
            for (MutationProbabilityType probabilityType: mutationProbabilityTypes)
                mutations.add(new MutationSettings(type, probabilityType));
    }

    public List<SelectionSettings> getSelections() {
        return selections;
    }

    public List<RecombinationSettings> getRecombinations() {
        return recombinations;
    }

    public List<MutationSettings> getMutations() {
        return mutations;
    }

    @Override
    public String toString() {
        return "адаптивный {использовать элитизм:" + (isUseElitism ? "да, процент элиты: " + elitismPercent : "нет") + "}";
    }

    @Override
    public String getParametersString() {
        return isUseElitism +
                "\t" +
                elitismPercent +
                "\t";
    }
}
