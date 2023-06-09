package scheduling.optimization.combinatorial.LKH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionHistory<R> {
    private Map<String, List<Integer>> solutions;
    private Map<String, R> values;

    public SolutionHistory(){
        solutions = new HashMap<>();
        values = new HashMap<>();
    }

    public void add(List<Integer> solution, R value){
        String hash = solution.toString();
        solutions.put(hash, solution);
        values.put(hash, value);
    }

    public R get(List<Integer> solution){
        return values.get(solution.toString());
    }

    public boolean contains(List<Integer> solution){
        return solutions.containsKey(solution.toString());
    }
}
