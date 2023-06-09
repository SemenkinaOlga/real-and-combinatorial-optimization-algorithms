package scheduling.optimization.real.RGA;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.real.RGA.standard.operators.MutationSettings;
import scheduling.optimization.real.RGA.standard.operators.RecombinationSettings;
import scheduling.optimization.real.RGA.standard.operators.SelectionSettings;

public abstract class RGAParametersBase implements IAlgorithmParameters {

    private int bitNumberPerVariable;
    private double elitismPercentage;
    private boolean isUseElitism;

    public int getBitNumberPerVariable() {
        return bitNumberPerVariable;
    }

    public void setBitNumberPerVariable(int bitNumberPerVariable) {
        this.bitNumberPerVariable = bitNumberPerVariable;
    }

    public void setUseElitism(boolean useElitism) {
        isUseElitism = useElitism;
    }

    public void setElitismPercentage(double elitismPercentage) {
        this.elitismPercentage = elitismPercentage;
    }

    public boolean isUseElitism() {
        return isUseElitism;
    }

    public double getElitismPercentage() {
        return elitismPercentage;
    }

    public RGAParametersBase() {
        bitNumberPerVariable = 10;
        isUseElitism = true;
        elitismPercentage = 1;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "bitNumberPerVariable: " + bitNumberPerVariable +
                "isUseElitism: " + isUseElitism +
                "elitismPercentage: " + elitismPercentage +
                '}';
    }

    @Override
    public String getParametersString() {
        return "bit:"+bitNumberPerVariable + "\t" +
                isUseElitism + "\t" +
                "%" + elitismPercentage + "\t";
    }
}
