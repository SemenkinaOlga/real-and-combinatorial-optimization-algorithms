package scheduling.optimization.combinatorial.ACO.standard;

import scheduling.optimization.combinatorial.ACO.ACOParametersBase;

public class ACOParameters extends ACOParametersBase {

    protected double alpha;
    protected double beta;


    public ACOParameters() {
        super();
        alpha = 2;
        beta = 5;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getRo() {
        return ro;
    }

    public void setRo(double ro) {
        this.ro = ro;
    }

    public double getQ() {
        return Q;
    }

    public void setQ(double q) {
        Q = q;
    }

    public double getPheromoneMinimum() {
        return pheromoneMinimum;
    }

    public void setPheromoneMinimum(double pheromoneMinimum) {
        this.pheromoneMinimum = pheromoneMinimum;
    }

    public double getPheromoneMaximum() {
        return pheromoneMaximum;
    }

    public void setPheromoneMaximum(double pheromoneMaximum) {
        this.pheromoneMaximum = pheromoneMaximum;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "α: " + alpha +
                ", β: " + beta +
                ", ρ: " + ro +
                ", Q: " + Q +
                ", минимальное кол-во феромона: " + pheromoneMinimum +
                ", максимальное кол-во феромона: " + pheromoneMaximum +
                '}';
    }

    @Override
    public String getParametersString() {
        return alpha +
                "\t" +
                beta +
                "\t" +
                ro +
                "\t" +
                Q +
                "\t" +
                pheromoneMinimum +
                "\t" +
                pheromoneMaximum +
                "\t";
    }
}
