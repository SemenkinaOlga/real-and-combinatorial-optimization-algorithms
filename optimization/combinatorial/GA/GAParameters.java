package scheduling.optimization.combinatorial.GA;

import scheduling.optimization.combinatorial.GA.settings.MutationSettings;
import scheduling.optimization.combinatorial.GA.settings.RecombinationSettings;
import scheduling.optimization.combinatorial.GA.settings.SelectionSettings;
import scheduling.optimization.combinatorial.GA.util.*;

public class GAParameters extends GAParametersBase {

    private MutationSettings mutationSettings;
    private RecombinationSettings recombinationSettings;
    private SelectionSettings selectionSettings;

    public GAParameters() {
        super();
        mutationSettings = new MutationSettings(MutationType.BY_2_EXCHANGE, MutationProbabilityType.VERY_LOW);
        recombinationSettings = new RecombinationSettings(RecombinationType.TYPICAL);
        selectionSettings = new SelectionSettings()
                .withSelectionType(SelectionType.TOURNAMENT)
                .tournamentSize(2)
                .rankingSelectionType(RankingSelectionType.LINEAR)
                .weight(0.8);
    }

    public MutationSettings getMutationSettings() {
        return mutationSettings;
    }

    public RecombinationSettings getRecombinationSettings() {
        return recombinationSettings;
    }

    public SelectionSettings getSelectionSettings() {
        return selectionSettings;
    }

    public boolean getIsUseElitism() {
        return isUseElitism;
    }

    public void setMutationSettings(MutationSettings mutationSettings) {
        this.mutationSettings = mutationSettings;
    }

    public void setRecombinationSettings(RecombinationSettings recombinationSettings) {
        this.recombinationSettings = recombinationSettings;
    }

    public void setSelectionSettings(SelectionSettings selectionSettings) {
        this.selectionSettings = selectionSettings;
    }

    public boolean isUseElitism() {
        return isUseElitism;
    }

    public void setUseElitism(boolean useElitism) {
        isUseElitism = useElitism;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "настройки мутации: " + mutationSettings.toString() +
                ", настройки рекомбинации: " + recombinationSettings.toString() +
                ", настройки селекции:" + selectionSettings.toString() +
                ", использовать элитизм:" + (isUseElitism ? "да" : "нет") +
                '}';
    }

    @Override
    public String getParametersString() {
        return isUseElitism +
                "\t" +
                elitismPercent +
                "\t" +
                selectionSettings.getSelectionType().getDisplayName() +
                "\t" +
                selectionSettings.getTournamentSize() +
                "\t" +
                selectionSettings.getRankingSelectionType().getDisplayName() +
                "\t" +
                selectionSettings.getRankingSelectionType() +
                "\t" +
                selectionSettings.getWeight() +
                "\t" +
                recombinationSettings.getRecombinationType() +
                "\t" +
                mutationSettings.getMutationType() +
                "\t" +
                mutationSettings.getMutationProbabilityType()+
                "\t";
    }
}
