package scheduling.optimization;

import scheduling.optimization.util.RandomUtils;

import java.util.List;
import java.util.*;

public class ParameterVariants <T>{
    private Map<T, Double> probabilitiesMap;
    private List<T> names;

    public ParameterVariants(List<T> names) {
        this.names = names;
        this.probabilitiesMap = new HashMap<>();
        initialize();
    }

    public int size(){
        return probabilitiesMap.size();
    }

    public List<T> getNames(){
        return names;
    }

    public List<Double> getProbabilities(){
        return new ArrayList<Double>(probabilitiesMap.values());
    }

    public void initialize(){
        for (T name : names) {
            probabilitiesMap.put(name, 1.0 / ((double) names.size()));
        }
    }

//    public double getProbability(double name){
//        return probabilitiesMap.get(name);
//    }

    public Map<T, Double> getProbabilitiesMap(){
        return probabilitiesMap;
    }

    public T chooseParameter(){
        return RandomUtils.chooseFromProbabilities(probabilitiesMap);
    }

    @Override
    public String toString() {
        return "ParameterVariants{" +
                "probabilitiesMap=" + probabilitiesMap +
                '}';
    }
}
