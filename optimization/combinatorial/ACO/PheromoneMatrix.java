package scheduling.optimization.combinatorial.ACO;

import scheduling.bean.algorithm.util.LoggerWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class PheromoneMatrix <R extends Number, T extends AntBase<R>>{
    private List<List<Double>> matrix;
    private ACOParametersBase parameters;


    public PheromoneMatrix(int dimension, ACOParametersBase parameters) {
        this.parameters = parameters;

        matrix = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            matrix.add(new ArrayList<>());
            matrix.get(i).addAll(Collections.nCopies(dimension, parameters.getPheromoneMaximum()));
            matrix.get(i).set(i, 0.);
        }
    }

    public PheromoneMatrix(PheromoneMatrix<R, T> pheromoneMatrix) {
        this.parameters = pheromoneMatrix.parameters;
        int dimension = pheromoneMatrix.matrix.size();

        matrix = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            matrix.add(new ArrayList<>());
            matrix.get(i).addAll(new ArrayList<>(pheromoneMatrix.matrix.get(i)));
        }
    }

    List<List<Double>> Get() {
        return matrix;
    }

    public double Get(int i, int j) {
        return matrix.get(i).get(j);
    }

    public void update(ColonyBase<R, T> current) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = i + 1; j < matrix.size(); j++) {
                double tmp = matrix.get(i).get(j);
                tmp = checkPheromone(tmp * parameters.getRo());
                matrix.get(i).set(j, tmp);
                matrix.get(j).set(i, tmp);
            }
        }

        for (int h = 0; h < current.size(); h++) {
            updateByAnt(current.getAnt(h));
        }
    }

    public void updateByAnt(AntBase<R> ant) {
        List<Integer> path = ant.getPath();
        double delta = parameters.getQ() / ant.getValue().doubleValue();

        Integer first, second;
        for (int i = 0; i < path.size() - 1; i++) {
            first = path.get(i);
            second = path.get(i + 1);

            double tmp = matrix.get(first).get(second);
            tmp = checkPheromone(tmp + delta);

            matrix.get(first).set(second, tmp);
            matrix.get(second).set(first, tmp);
        }
        first = path.get(path.size() - 1);
        second = path.get(0);
        double tmp = matrix.get(first).get(second);
        tmp = checkPheromone(tmp + delta);
        matrix.get(first).set(second, tmp);
        matrix.get(second).set(first, tmp);
    }

    double checkPheromone(double amount) {
        if (amount < parameters.getPheromoneMinimum())
            return parameters.getPheromoneMinimum();
        if (amount > parameters.getPheromoneMaximum())
            return parameters.getPheromoneMaximum();
        return amount;
    }

    public void updateByBest(AntBase<R> ant) {
        updateByAnt(ant);
    }

    public void print(LoggerWrapper mainLogger){
        mainLogger.debug("PheromoneMatrix");
        for (int i = 0; i < matrix.size(); i++){
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < matrix.get(i).size(); j++){
                builder.append(matrix.get(i).get(j)).append("\t");
            }
            mainLogger.debug(builder.toString());
        }
    }
}
