package scheduling.optimization.real.RGA.standard;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.real.RGA.*;
import scheduling.optimization.real.RGA.operators.MutationType;
import scheduling.optimization.real.RGA.operators.RankingSelectionType;
import scheduling.optimization.real.RGA.operators.RecombinationType;
import scheduling.optimization.real.RGA.operators.SelectionType;
import scheduling.optimization.real.RGA.standard.operators.*;

public class RGAParameters extends RGAParametersBase implements IAlgorithmParameters {

    private SelectionSettings selectionSettings;
    private RecombinationSettings recombinationSettings;
    private MutationSettings mutationSettings;

    public RecombinationSettings getRecombinationSettings() {
        return recombinationSettings;
    }

    public void setRecombinationSettings(RecombinationSettings recombinationSettings) {
        this.recombinationSettings = recombinationSettings;
    }

    public MutationSettings getMutationSettings() {
        return mutationSettings;
    }

    public void setMutationSettings(MutationSettings mutationSettings) {
        this.mutationSettings = mutationSettings;
    }

    public SelectionSettings getSelectionSettings() {
        return selectionSettings;
    }

    public void setSelectionSettings(SelectionSettings selectionSettings) {
        this.selectionSettings = selectionSettings;
    }

    public RGAParameters() {
        super();
        selectionSettings = new SelectionSettings(SelectionType.TOURNAMENT, 3, RankingSelectionType.LINEAR, 0.9);
        recombinationSettings = new RecombinationSettings(RecombinationType.UNIFORM);
        mutationSettings = new MutationSettings(MutationType.AVERAGE);
    }

}
