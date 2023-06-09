package scheduling.optimization.combinatorial.GA;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.combinatorial.GA.util.SelfConfiguringType;

public abstract class GAParametersBase implements IAlgorithmParameters {

    protected SelfConfiguringType selfConfiguringType;
    protected boolean isUseElitism;
    protected Integer elitismPercent = 0;

    public GAParametersBase() {
    }

    public SelfConfiguringType getSelfConfiguringType() {
        return selfConfiguringType;
    }

    public void setSelfConfiguringType(SelfConfiguringType selfConfiguringType) {
        this.selfConfiguringType = selfConfiguringType;
    }

    public boolean isUseElitism() {
        return isUseElitism;
    }

    public void setUseElitism(boolean useElitism) {
        isUseElitism = useElitism;
    }

    public Integer getElitismPercent() {
        return elitismPercent;
    }

    public void setElitismPercent(Integer elitismPercent) {
        this.elitismPercent = elitismPercent;
    }

}
