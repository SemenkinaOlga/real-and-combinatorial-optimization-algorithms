package scheduling.optimization.real.DE.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.DEType;
import scheduling.optimization.real.DE.IndividualBase;
import scheduling.optimization.real.ProblemReal;

public class AdaptiveIndividual<R extends Number> extends IndividualBase<R> {
    private double CR = 0.5;
    // it is actually F, but xhtml bind doesn't work with this letter, lol
    private double FF = 0.5;
    private DEType deType = DEType.BY_RANDOM;
    private double lymbda = 0.5;

    public double getCR() {
        return CR;
    }

    public double getFF() {
        return FF;
    }

    public DEType getDeType() {
        return deType;
    }

    public double getLymbda() {
        return lymbda;
    }

    public void setCR(double CR) {
        this.CR = CR;
    }

    public void setFF(double FF) {
        this.FF = FF;
    }

    public void setDeType(DEType deType) {
        this.deType = deType;
    }

    public void setLymbda(double lymbda) {
        this.lymbda = lymbda;
    }

    public AdaptiveIndividual(ProblemReal<Double, R> problem, LoggerWrapper logger) {
        super(problem, logger);
    }

    public AdaptiveIndividual(AdaptiveIndividual<R> individual) {
        super(individual);
        this.CR = individual.CR;
        this.FF = individual.FF;
        this.deType = individual.deType;
        this.lymbda = individual.lymbda;
    }

    public AdaptiveIndividual(ProblemReal<Double, R> problem, Solution<Double, R> solution, LoggerWrapper logger) {
        super(problem, solution, logger);
    }

}
