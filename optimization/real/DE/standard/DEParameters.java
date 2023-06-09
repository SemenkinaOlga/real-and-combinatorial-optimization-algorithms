package scheduling.optimization.real.DE.standard;

import scheduling.optimization.IAlgorithmParameters;
import scheduling.optimization.real.DE.DEType;

public class DEParameters implements IAlgorithmParameters {

    private double CR;
    // it is actually F, but xhtml bind doesn't work with this letter, lol
    private double FF;
    private DEType DEType;
    private double lymbda;

    public double getCR() {
        return CR;
    }

    public void setCR(double CR) {
        this.CR = CR;
    }

    public double getFF() {
        return FF;
    }

    public void setFF(double FF) {
        this.FF = FF;
    }

    public DEType getDEType() { return DEType; }

    public void setDEType(DEType dEType) {
        this.DEType = dEType;
    }

    public double getLymbda() {
        return lymbda;
    }

    public void setLymbda(double lymbda) {
        this.lymbda = lymbda;
    }

    public DEParameters() {
       CR = 0.8;
       FF = 0.5;
       DEType = DEType.BY_BEST;
       lymbda = 0.5;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "DEType: " + DEType.getDisplayName() +
                "CR: " + CR +
                "F: " + FF +
                "lymbda: " + lymbda +
                '}';
    }

    @Override
    public String getParametersString() {
        return DEType.getDisplayName() + "\t" +
                CR + "\t" +
                FF + "\t" +
                lymbda + "\t";
    }
}
