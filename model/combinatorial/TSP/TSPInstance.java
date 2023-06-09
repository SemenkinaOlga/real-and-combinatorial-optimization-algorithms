package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;
import java.util.List;

public abstract class TSPInstance {
    protected List<List<Double>> coordinates;
    protected List<List<Double>> distances;

    public abstract double getOptimalValue();
    public abstract boolean isOptimumKnown();
    public abstract String getName();

    public double getDistance(Integer first, Integer second) {
        return distances.get(first).get(second);
    }

    protected void calculateDistances(){
        distances = new ArrayList<>();
        for(int i = 0; i < coordinates.size(); i++) {
            distances.add(new ArrayList<>());
            for (int j = 0; j < coordinates.size(); j++) {
                double res = Math.sqrt(Math.pow(coordinates.get(i).get(0) - coordinates.get(j).get(0), 2) +
                        Math.pow(coordinates.get(i).get(1) - coordinates.get(j).get(1), 2));
                distances.get(i).add(res);
            }
        }
    }

    protected void addCoordinates(double x, double y){
        List<Double> current = new ArrayList<>();
        current.add(x);
        current.add(y);
        coordinates.add(current);
    }

    public int getDimension() {
        return coordinates.size();
    }

    public List<Integer> getOptimalSolution() {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++)
            res.add(0);
        return res;
    }

    public Double calculateObjectiveFunction(List<Integer> data) {
        double value = 0.;
        int n = data.size();
        for(int i = 1; i < n; i++){
            value += distances.get(data.get(i - 1)).get(data.get(i));
        }
        value += distances.get(data.get(n - 1)).get(data.get(0));
        return value;
    }
}
