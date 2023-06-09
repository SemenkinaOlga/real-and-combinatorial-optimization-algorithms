package scheduling.optimization.combinatorial.IWDs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.AntBase;
import scheduling.optimization.util.RandomUtils;

public class WaterDrop<R extends Number> implements Comparable<WaterDrop<R>>{
    private Solution<Integer, R> solution;
    private List<Integer> path;
    private Problem<Integer, R> problem;
    private IWDsParameters parameters;
    private R value;
    double soil;
    double velocity;

    public Solution<Integer, R> getSolution() {
        return solution;
    }

    public R getValue() {
        return value;
    }

    public void setValue(R value) {
        this.value = value;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
        this.solution = new Solution<>(path, value);
    }

    public WaterDrop(Problem<Integer, R> problem, IWDsParameters parameters) {
        this.parameters = parameters;
        this.problem = problem;
        clear();
    }

    public WaterDrop(Problem<Integer, R> problem, IWDsParameters parameters, Solution<Integer, R> solution) {
        this.parameters = parameters;
        this.problem = problem;

        path = new ArrayList<>(solution.getData());
        value = solution.getValue();

        this.solution = solution;

        velocity = parameters.getInitVel();
        soil = 0.0;
    }

    public WaterDrop(WaterDrop<R> drop) {
        this.parameters = drop.parameters;
        this.problem = drop.problem;

        path = new ArrayList<>(drop.path);
        value = drop.value;
        solution = drop.solution;
        velocity = drop.velocity;
        soil = drop.soil;
    }

    void clear() {
        path = new ArrayList<>();
        Random random = new Random();
        path.add(random.nextInt(problem.getDimension()));

        value = problem.getWorst();

        solution = null;

        velocity = parameters.getInitVel();
        soil = 0.0;
    }

    public double getSoil() {
        return soil;
    }

    public double getVelocity() {
        return velocity;
    }

    public R calculateObjectiveFunction() {
        solution = problem.createSolution(path);
        value = problem.calculateObjectiveFunction(solution);
        return value;
    }

    @Override
    public int compareTo(WaterDrop<R> other) {
        return problem.compare(solution, other.solution);
    }

    public List<Integer> getPath() {
        return path;
    }

    public boolean containEdge(int first, int second) {
        int i = path.indexOf(first);

        if (i == path.size() - 1) {
            if (path.get(0) == second || path.get(i - 1) == second) {
                return true;
            }
        } else if (i == 0) {
            if (path.get(path.size() - 1) == second || path.get(i + 1) == second) {
                return true;
            }
        } else if (path.get(i + 1) == second || path.get(i - 1) == second) {
            return true;
        }
        return false;
    }

    public void chooseNext(SoilMatrix soilMatrix) {
        List<Double> probabilities = getProbabilities(soilMatrix);

        int i = RandomUtils.chooseFromProbabilities(probabilities);
        path.add(i);
    }

    public List<Double> getProbabilities(SoilMatrix soil) {
        List<Double> probabilities = new ArrayList<>();
        int i = path.get(path.size() - 1);
        double sum = 0;
        for (int j = 0; j < problem.getDimension(); j++) {
            if (!path.contains(j)) {
                double tmp = fsoil(i, j, soil);
                probabilities.add(tmp);
                sum += tmp;
            } else {
                probabilities.add(0.);
            }
        }

        for (int j = 0; j < problem.getDimension(); j++) {
            double val = probabilities.get(j) / sum;
            probabilities.set(j, val);
        }
        return probabilities;
    }

    double fsoil(int i, int j, SoilMatrix soilMatrix) {
        return 1.0 / (parameters.getEs() + gsoil(i, j, soilMatrix));
    }

    double gsoil(int i, int j, SoilMatrix soilMatrix) {
        double minSoil = 999999999999.9;
        for (int l = 0; l < problem.getDimension(); l++) {
            if (!path.contains(l)) {
                if (minSoil > soilMatrix.get(i, l)) {
                    minSoil = soilMatrix.get(i, l);
                }
            }
        }
        if (minSoil >= 0) {
            return soilMatrix.get(i, j);
        } else {
            return soilMatrix.get(i, j) - minSoil;
        }

    }

    public void updateVelocity(SoilMatrix soilMatrix) {
        int k1 = path.get(path.size() - 2);
        int k2 = path.get(path.size() - 1);

        double s = soilMatrix.get(k1, k2);

        velocity = velocity + parameters.getAv() / (parameters.getBv() + parameters.getCv() * Math.pow(s, 2));
    }

    public void updateSoil(SoilMatrix soilMatrix, List<List<Double>> visibility) {
        int k1 = path.get(path.size() - 2);
        int k2 = path.get(path.size() - 1);

        double ds = deltaSoil(k1, k2, visibility);
        soilMatrix.updateByDrop(k1, k2, ds);
        soil += ds;
    }

    public double deltaSoil(int i, int j, List<List<Double>> visibility) {
        double t = time(i, j, visibility);
        return parameters.getAs() / (parameters.getBs() + parameters.getCs() * Math.pow(t, 2));
    }

    double time(int i, int j, List<List<Double>> visibility) {
        return HUD(i, j, visibility) / velocity;
    }

    double HUD(int i, int j, List<List<Double>> visibility) {
        return visibility.get(i).get(j);
    }

    @Override
    public String toString(){
        return solution.toString();
    }
}

