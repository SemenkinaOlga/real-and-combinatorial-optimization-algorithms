package scheduling.optimization.combinatorial.IWDs;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.standard.Ant;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flow<R extends Number> {
    private List<WaterDrop<R>> drops;
    private IWDsParameters parameters;
    private Problem<Integer, R> problem;
    private int dimension;
    private int dropAmount;

    public Flow(int dropAmount, Problem<Integer, R> problem, IWDsParameters parameters){
        this.problem = problem;
        this.parameters = parameters;
        dimension = problem.getDimension();
        this.dropAmount = dropAmount;

        drops = new ArrayList<>();
        for (int i = 0; i < dropAmount; i++){
            drops.add(new WaterDrop<>(problem, parameters));
        }
    }

    void clear() {
        for (WaterDrop<R> drop: drops) {
            drop.clear();
        }
    }

    public int size(){
        return drops.size();
    }

    public void calculateObjectiveFunction() {
        for (WaterDrop<R> drop : drops) {
            drop.calculateObjectiveFunction();
        }
    }

    public WaterDrop<R> findBest(){
        WaterDrop<R> drop = drops.get(0);

        for(int i = 1; i < drops.size(); i++){
            WaterDrop<R> current = drops.get(i);
            if(current.compareTo(drop) > 0){
                drop = current;
            }
        }

        return drop;
    }

    public void chooseNext(SoilMatrix soil){
        for (WaterDrop<R> drop : drops) {
            drop.chooseNext(soil);
        }
    }

    public WaterDrop<R> getDrop(int i){
        return drops.get(i);
    }

    public void updateVelocity(SoilMatrix soilMatrix){
        for (WaterDrop<R> drop : drops) {
            drop.updateVelocity(soilMatrix);
        }
    }

    public void updateSoil(SoilMatrix soilMatrix, List<List<Double>> visibility){
        for (WaterDrop<R> drop : drops) {
            drop.updateSoil(soilMatrix, visibility);
        }
    }

    public void resizePopulation(int size){
        if (size > drops.size()) {
            int dropsSize = drops.size();
            for (int i = 0; i < size - dropsSize; i++){
                WaterDrop<R> waterDrop = new WaterDrop<>(problem, parameters);
                List<Integer> path = new ArrayList<>();
                for (int j = 0; j < problem.getDimension(); j++){
                    path.add(j);
                }
                waterDrop.setPath(path);
                drops.add(waterDrop);
            }
        } else {
            drops.sort(Collections.reverseOrder());
            drops.subList(size, drops.size()).clear();
        }
    }

    public void replaceRandom(List<Solution<Integer, R>> solutions, SoilMatrix soilMatrix, List<List<Double>> visibility){
        List<Integer> antsIndexes = RandomUtils.getRandomIndexes(solutions.size(), drops.size());
        List<WaterDrop<R>> antsToRemove = new ArrayList<>();
        for (int index: antsIndexes) {
            antsToRemove.add(drops.get(index));
        }
        drops.removeAll(antsToRemove);

        for (Solution<Integer, R> solution: solutions) {
            addDrop(solution, soilMatrix, visibility);
        }
    }

    public void addDrop(Solution<Integer, R> solution, SoilMatrix soilMatrix, List<List<Double>> visibility){
        WaterDrop<R> waterDrop = new WaterDrop<R>(problem, parameters, solution);

        for (int i = 0; i < waterDrop.getPath().size() - 1; i++){
            int k1 = waterDrop.getPath().get(i);
            int k2 = waterDrop.getPath().get(i + 1);
            double ds = waterDrop.deltaSoil(k1, k2, visibility);
            soilMatrix.updateByDrop(k1, k2, ds);
        }

        int k1 = waterDrop.getPath().get(waterDrop.getPath().size() - 1);
        int k2 = waterDrop.getPath().get(0);
        double ds = waterDrop.deltaSoil(k1, k2, visibility);
        soilMatrix.updateByDrop(k1, k2, ds);

        drops.add(waterDrop);
    }

    public void replaceWorst(List<Solution<Integer, R>> solutions, SoilMatrix soilMatrix, List<List<Double>> visibility){
        drops.sort(Collections.reverseOrder());
        drops.subList(drops.size() - solutions.size(), drops.size()).clear();
        for (Solution<Integer, R> solution: solutions) {
            addDrop(solution, soilMatrix, visibility);
        }
    }

    public int chooseRandomAntIndex(){
        return RandomUtils.random.nextInt(drops.size());
    }

    public WaterDrop<R> chooseRandom(){
        return drops.get(chooseRandomAntIndex());
    }

    public List<Solution<Integer, R>> getPopulation(){
        List<Solution<Integer, R>> solutions = new ArrayList<>();
        for (WaterDrop<R> drop: drops) {
            solutions.add(drop.getSolution());
        }
        return solutions;
    }

    public void setPopulation(List<Solution<Integer, R>> solutions, SoilMatrix soilMatrix, List<List<Double>> visibility){
        drops = new ArrayList<>();
        for (Solution<Integer, R> solution: solutions) {
            addDrop(solution, soilMatrix, visibility);
        }
    }
}
