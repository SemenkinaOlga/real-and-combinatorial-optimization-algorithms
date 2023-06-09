package scheduling.optimization.combinatorial.ACO;

import scheduling.optimization.IAlgorithmParameters;

public abstract class ACOParametersBase implements IAlgorithmParameters {

    protected double ro;
    protected double Q;
    protected double pheromoneMinimum;
    protected double pheromoneMaximum;

    public ACOParametersBase() {
        ro = 0.8;
        Q = 10000;
        pheromoneMinimum = 0;
        pheromoneMaximum = 1000000;
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

}
