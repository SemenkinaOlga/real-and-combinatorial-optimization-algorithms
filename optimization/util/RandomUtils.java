package scheduling.optimization.util;

import java.util.*;

public class RandomUtils {

    public static Random random = new Random();

    /**
     * получение случайного индекса
     * @param indexes - уже имеющиеся индексы
     * @param bound - общее кол-во (ограничение для random)
     * @return нужный индекс
     */
    public static int getRandomIndexExclude(List<Integer> indexes, Integer bound) {
        int index;
        do {
            index = random.nextInt(bound);
        } while (indexes.indexOf(index) != -1);
        return index;
    }

    /**
     * получение случайного индекса (используется для 2х индексов, чтобы не создавать List)
     * @param excludeIndex - индекс для исключения
     * @param bound - общее кол-во (ограничение для random)
     * @return нужный индекс
     */
    public static int getRandomIndexExclude(Integer excludeIndex, int bound) {
        Integer index;
        do {
            index = random.nextInt(bound);
        } while (index.equals(excludeIndex));
        return index;
    }

    private static List<Double> createDistribution(Collection<Double> probabilities){
        List<Double> distribution = new ArrayList<>();
        double current = 0.0;
        distribution.add(current);

        for (double d: probabilities) {
            current +=  d;
            distribution.add(current);
        }
        distribution.set(distribution.size() - 1, 1.0);

        return distribution;
    }

    public static int chooseFromProbabilities(List<Double> probabilities){
        List<Double> distribution = createDistribution(probabilities);

        double r = random.nextDouble();

        int i = 0;
        if(r == 0){
            for(i = 0; i < probabilities.size(); i++){
                if(probabilities.get(i) > 0) break;
            }
        }
        else{
            for(i = 0; i < distribution.size();){
                if(distribution.get(i + 1) > 0.99999999) break;
                if(r > distribution.get(i + 1)){
                    i++;
                }
                else{
                    break;
                }
            }
        }
        return i;
    }

    private static <T> Map<T, Double> createDistribution(Map<T, Double> probabilitiesMap, List<T> keySet) {
        Map<T, Double> distribution = new LinkedHashMap<>();
        double current = 0.0;
        for (int i = 0; i < keySet.size(); i++) {
            current += probabilitiesMap.get(keySet.get(i));
            if (i < keySet.size() - 1){
                distribution.put(keySet.get(i), current);
            }
            else{
                distribution.put(keySet.get(i), 1.0);
            }
        }
        return distribution;
    }

    public static <T> T chooseFromProbabilities(Map<T, Double> probabilitiesMap){
        List<T> keySet = new ArrayList<T>(probabilitiesMap.keySet());
        Map<T, Double> distributionMap = createDistribution(probabilitiesMap, keySet);

        double r = random.nextDouble();

        if (r == 0) {
//            key = probabilitiesMap.entrySet().stream()
//                    .filter(doubleDoubleEntry -> doubleDoubleEntry.getValue() > 0)
//                    .findFirst().get().getValue();
            for (Map.Entry<T, Double> entry : probabilitiesMap.entrySet()) {
                if (entry.getValue() > 0) {
                    return entry.getKey();
                }
            }
        }

        for (int i = 0; i < keySet.size(); i++) {
            if (r <= distributionMap.get(keySet.get(i))) {
                return keySet.get(i);
            }
        }

        return keySet.get(keySet.size() - 1);
    }

    public static List<Integer> getRandomIndexes(int amount, int bound){
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            int current = getRandomIndexExclude(indexes, bound);
            indexes.add(current);
        }
        return indexes;
    }
}
