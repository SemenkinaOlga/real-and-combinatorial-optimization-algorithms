package scheduling.optimization.combinatorial.ACO;

public interface ICooperativeACO<R extends Number, T extends AntBase<R>> {
    PheromoneMatrix<R, T> getPheromoneMatrix();
    void setPheromoneMatrix(PheromoneMatrix<R, T> pheromoneMatrix);
}
