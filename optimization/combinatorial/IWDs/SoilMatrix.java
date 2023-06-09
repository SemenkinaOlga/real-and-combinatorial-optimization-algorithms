package scheduling.optimization.combinatorial.IWDs;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class SoilMatrix {
    private List<List<Double>> matrix;
    private IWDsParameters parameters;

    public SoilMatrix(int dimension, IWDsParameters parameters){
        this.parameters = parameters;

        matrix = new ArrayList<>(dimension);
        for(int i=0; i<dimension; i++) {
            matrix.add(new ArrayList<Double>());
            matrix.get(i).addAll(Collections.nCopies(dimension, parameters.getInitSoil()));
        }
    }

    public double get(int i, int j){
        return matrix.get(i).get(j);
    }

    public void restartByBest(WaterDrop best){
        for(int i = 0; i < matrix.size(); i++){
            for(int j = 0; j < matrix.get(i).size(); j++){
                matrix.get(i).set(j, parameters.getInitSoil());
            }
        }

        int k1, k2;
        Random random = new Random();
        double gamma= random.nextDouble();

        List<Integer> path = best.getPath();

        for (int i = 0; i < path.size() - 1; i++) {
            k1 = path.get(i);
            k2 = path.get(i + 1);
            double tmp = matrix.get(k1).get(k2);
            matrix.get(k1).set(k2, tmp * parameters.getAlpha() * gamma);
        }
    }

    public void updateByDrop(int i, int j, double deltaSoil){
        double s = matrix.get(i).get(j);
        double res = (1 - parameters.getPn()) * s - parameters.getPn() * deltaSoil;
        matrix.get(i).set(j, res);
    }

    public void updateByBest(WaterDrop drop){
        List<Integer> path = drop.getPath();
        double size = (double) (path.size() - 1);

        for (int i = 0; i < path.size() - 1; i++) {
            Integer first = path.get(i);
            Integer second = path.get(i + 1);

            double s = matrix.get(first).get(second);

            double tmp = (1 + parameters.getpIWD()) * s - parameters.getpIWD() * drop.getSoil() / size;

            matrix.get(first).set(second, tmp);
            matrix.get(second).set(first, tmp);
        }
    }
}