package scheduling.optimization.combinatorial.LKH;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.real.SolutionReal;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LKHSolution<R extends Number> implements Comparable<LKHSolution<R>>{
    private Solution<Integer, R> solution;
    private List<Integer> path;
    private Problem<Integer, R> problem;
    private R value;
    protected LoggerWrapper logger;

    public Solution<Integer, R> getSolution() {
        return solution;
    }

    public LKHSolution(Problem<Integer, R> problem, LoggerWrapper logger) {
        this.problem = problem;
        this.logger = logger;

        path = new ArrayList<>();
        for (int i = 0; i < problem.getDimension(); i++) {
            path.add(i);
        }
        // Таcование Фишера-Йетса
        for (int i = problem.getDimension() - 1; i >= 0; i--) {
            int j = RandomUtils.random.nextInt(i + 1);
            Collections.swap(path, i, j);
        }
        logger.debug("init solution");
        logger.debug(CollectionToString(path));
    }

    public LKHSolution(Problem<Integer, R> problem, Solution<Integer, R> solution) {
        this.problem = problem;
        path = new ArrayList<>(solution.getData());
        value = solution.getValue();
        this.solution = new Solution<>(solution);;
    }

    public LKHSolution(LKHSolution<R> solution) {
        this.problem = solution.problem;
        path = new ArrayList<>(solution.path);
        value = solution.value;
        this.solution = new Solution<>(solution.solution);
    }

    public LKHSolution(Problem<Integer, R> problem, List<Integer> path, R value) {
        this.problem = problem;
        this.path = new ArrayList<>(path);
        this.value = value;
        this.solution = new Solution<>(path, value);
    }

    public LKHSolution(Problem<Integer, R> problem, List<Integer> path) {
        this.problem = problem;
        this.path = new ArrayList<>(path);
        this.value = problem.getWorst();
        this.solution = new Solution<>(path, value);
    }

    private String CollectionToString(Collection<Integer> list) {
        StringBuilder res = new StringBuilder();
        for (Integer d : list)
            res.append(d).append("\t");
        return res.toString();
    }

    public String toString(){
        return CollectionToString(path) + " : " + value;
    }

    public void setValue(R value){
        this.value = value;
    }

    public R calculateObjectiveFunction() {
        solution = problem.createSolution(path);
        value = problem.calculateObjectiveFunction(solution);
        return value;
    }

    @Override
    public int compareTo(LKHSolution<R> other) {
        return problem.compare(solution, other.solution);
    }

    public List<Integer> getPath() {
        return path;
    }

    List<Integer> turn(List<Integer> P){
        int temp;
        for(int i = 0; i < P.size()/2.; i++){
            temp = P.get(i);
            P.set(i, P.get(P.size() - i - 1));
            P.set(P.size() - i - 1, temp);
        }
        return P;
    }

    List<List<Integer>> copy(List<List<Integer>> list){
        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> integers : list) {
            res.add(new ArrayList<>(integers));
        }
        return res;
    }

    private class ResultLKH {
        public Integer resource;
        public boolean findBest;

        public ResultLKH(Integer resource, boolean findBest){
            this.resource = resource;
            this.findBest = findBest;
        }
    }


    boolean chooseNext(List<LKHSolution<R>> N3, List<List<Integer>> details){
        logger.debug("chooseNext");
        logger.debug("details");
        for (int i = 0; i < details.size(); i++) {
            logger.debug(CollectionToString(details.get(i)));
        }
        if(details.size() == 2){
            logger.debug("details == 2");
            List<Integer> d = turn(details.get(1));
            for (Integer integer : d) {
                details.get(0).add(integer);
            }
            details.remove(1);
            logger.debug("details");
            for (int i = 0; i < details.size(); i++) {
                logger.debug(CollectionToString(details.get(i)));
            }
            LKHSolution<R> sol = new LKHSolution<>(problem, details.get(0), problem.getWorst());
            N3.add(sol);
            logger.debug("N3 add");
            logger.debug(sol.toString());
            return true;
        }
        else if(details.size() >= 3){
            logger.debug("details >= 3");
            List<Integer> d1 = turn(details.get(1));
            List<List<Integer>> details1 = copy(details);
            for (Integer integer : d1) {
                details1.get(0).add(integer);
            }
            details1.remove(1);
            logger.debug("details1");
            for (int i = 0; i < details1.size(); i++) {
                logger.debug(CollectionToString(details1.get(i)));
            }
            chooseNext(N3, details1);

            List<Integer> d2 = turn(details.get(2));
            List<List<Integer>> details2 = copy(details);
            for (Integer integer : d2) {
                details2.get(0).add(integer);
            }
            details2.remove(2);
            logger.debug("details2");
            for (int i = 0; i < details2.size(); i++) {
                logger.debug(CollectionToString(details2.get(i)));
            }
            chooseNext(N3, details2);

            if(details.get(2).size() > 1){
                List<Integer> d3 = details.get(2);
                List<List<Integer>> details3 = copy(details);
                for(int i = 0; i < d2.size(); i++){
                    details3.get(0).add(d3.get(i));
                }
                details3.remove(2);
                logger.debug("details3");
                for (int i = 0; i < details3.size(); i++) {
                    logger.debug(CollectionToString(details3.get(i)));
                }
                chooseNext(N3, details3);
            }
            return true;
        }
        else{
            return false;
        }
    }

    ResultLKH improve(Integer resource, SolutionHistory<R> history){
        logger.debug("improve");
        int n = path.size();
        List<LKHSolution<R>> N3 = null;
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                for(int k=j+1; k<n; k++){
                    logger.debug("new N3");
                    N3 = new ArrayList<>();
                    List<List<Integer>> details = new ArrayList<>();
                    details.add(new ArrayList<>());
                    details.add(new ArrayList<>());
                    details.add(new ArrayList<>());
                    for(int h = k + 1; h < n; h++){
                        details.get(0).add(path.get(h));
                    }
                    for(int h = 0; h < i + 1; h++){
                        details.get(0).add(path.get(h));
                    }
                    for(int h = i + 1; h < j + 1; h++){
                        details.get(1).add(path.get(h));
                    }
                    for(int h = j + 1; h < k + 1; h++){
                        details.get(2).add(path.get(h));
                    }
                    chooseNext(N3, details);
                    for (LKHSolution<R> rlkhSolution : N3) {
                        rlkhSolution.calculateObjectiveFunction();
                        resource -= 1;
                        logger.debug(rlkhSolution.toString());
                        if (problem.compare(rlkhSolution.value, value) > 0) {
                            logger.debug("It is better then " + value);
                            path = rlkhSolution.path;
                            value = rlkhSolution.value;
                            this.solution = new Solution<Integer, R>(path, value);
                            return new ResultLKH(resource, true);
                        }
                        if (resource == 0) return new ResultLKH(resource, false);
                    }
                }
            }
        }
        return new ResultLKH(resource, false);
    }

/*
    private List<Integer> addInterval(List<Integer> solution, int i, int j){
        List<Integer> res = new ArrayList<>();
        for (int h = i; h <= j; h++){
            solution.add(path.get(h));
            res.add(path.get(h));
        }
        return res;
    }
    private List<Integer> addIntervalInverse(List<Integer> solution, int i, int j){
        List<Integer> res = new ArrayList<>();
        for (int h = j; h >= i; h--){
            solution.add(path.get(h));
            res.add(path.get(h));
        }
        return res;
    }

    private ResultLKH improve(Integer resource, SolutionHistory<R> history){
        logger.debug("improve");
        int n = path.size();
        for(int i = 0; i < n; i++){
            int x1 = i;
            int x2 = i + 1;
            for(int j = i + 1; j < n; j++) {
                int y1 = j;
                int y2 = j + 1;
                for (int k = j + 1; k < n; k++) {
                    int z1 = k;
                    int z2 = k + 1;
                    if (z2 == n) z2 = 0;
                    String tr = "x1=" + x1 + " x2=" + x2 + " y1=" + y1 + " y2=" + y2 + " z1=" + z1 + " z2=" + z2;
                    logger.debug(tr);

                    boolean res;

                    logger.debug("PATH " + path.toString());
                    logger.debug("solution1");
                    List<Integer> solution1 = new ArrayList<>();
                    logger.debug("0...x1" + "  " + addInterval(solution1, 0, x1).toString());
                    logger.debug("x2...y1 inv" + "  " + addIntervalInverse(solution1, x2, y1).toString());
                    logger.debug("y2...z1" + "  " + addInterval(solution1, y2, z1).toString());
                    logger.debug("z2...n-1");
                    if (z2 != 0) logger.debug(addInterval(solution1, z2, n - 1).toString());

                    if (!history.contains(solution1)) {
                        res = newSolution(solution1, history);
                        resource -= 1;
                        if (res == true) return new ResultLKH(resource, true);
                        if (resource == 0) return new ResultLKH(resource, false);
                    }
                    else {
                        logger.debug(solution1.toString() + " повтор");
                    }

                    logger.debug(tr);
                    logger.debug("PATH " + path.toString());
                    logger.debug("solution2");
                    List<Integer> solution2 = new ArrayList<>();
                    logger.debug("0...x1" + "  " + addInterval(solution2, 0, x1).toString());
                    logger.debug("x2...y1 inv" + "  " + addIntervalInverse(solution2, x2, y1).toString());
                    logger.debug("y2...z1 inv" + "  " + addIntervalInverse(solution2, y2, z1).toString());
                    logger.debug("z2...n-1");
                    if (z2 != 0) logger.debug(addInterval(solution2, z2, n - 1).toString());

                    if (!history.contains(solution2)) {
                        res = newSolution(solution2, history);
                        resource -= 1;
                        if (res == true) return new ResultLKH(resource, true);
                        if (resource == 0) return new ResultLKH(resource, false);
                    }
                    else {
                        logger.debug(solution1.toString() + " повтор");
                    }

                    logger.debug(tr);
                    logger.debug("PATH " + path.toString());
                    logger.debug("solution3");
                    List<Integer> solution3 = new ArrayList<>();
                    logger.debug("0...x1" + "  " + addInterval(solution3, 0, x1).toString());
                    logger.debug("y2...z1" + "  " + addInterval(solution3, y2, z1).toString());
                    logger.debug("x2...y1" + "  " + addInterval(solution3, x2, y1).toString());
                    logger.debug("z2...n-1");
                    if (z2 != 0) logger.debug(addInterval(solution3, z2, n - 1).toString());

                    if (!history.contains(solution3)) {
                        res = newSolution(solution3, history);
                        resource -= 1;
                        if (res == true) return new ResultLKH(resource, true);
                        if (resource == 0) return new ResultLKH(resource, false);
                        }
                    else {
                        logger.debug(solution1.toString() + " повтор");
                    }

                    logger.debug(tr);
                    logger.debug("PATH " + path.toString());
                    logger.debug("solution4");
                    List<Integer> solution4 = new ArrayList<>();
                    logger.debug("0...x1" + "  " + addInterval(solution4, 0, x1).toString());
                    logger.debug("y2...z1" + "  " + addInterval(solution4, y2, z1).toString());
                    logger.debug("x2...y1 inv" + "  " + addIntervalInverse(solution4, x2, y1).toString());
                    logger.debug("z2...n-1");
                    if (z2 != 0) logger.debug(addInterval(solution4, z2, n - 1).toString());

                    if (!history.contains(solution4)) {
                        res = newSolution(solution4, history);
                        resource -= 1;
                        if (res == true) return new ResultLKH(resource, true);
                        if (resource == 0) return new ResultLKH(resource, false);
                    }
                    else {
                        logger.debug(solution4.toString() + " повтор");
                    }

                    logger.debug(tr);
                    logger.debug("PATH " + path.toString());
                    logger.debug("solution5");
                    List<Integer> solution5 = new ArrayList<>();
                    logger.debug("0...x1" + "  " + addInterval(solution5, 0, x1).toString());
                    logger.debug("y2...z1 inv" + "  " + addIntervalInverse(solution5, y2, z1).toString());
                    logger.debug("x2...y1" + "  " + addInterval(solution5, x2, y1).toString());
                    logger.debug("z2...n-1");
                    if (z2 != 0) logger.debug(addInterval(solution5, z2, n - 1).toString());

                    if (!history.contains(solution5)) {
                        res = newSolution(solution5, history);
                        resource -= 1;
                        if (res == true) return new ResultLKH(resource, true);
                        if (resource == 0) return new ResultLKH(resource, false);
                    }
                    else {
                        logger.debug(solution5.toString() + " повтор");
                    }

                    logger.debug(tr);
                    logger.debug("PATH " + path.toString());
                    logger.debug("solution6");
                    List<Integer> solution6 = new ArrayList<>();
                    logger.debug("0...x1" + "  " + addInterval(solution6, 0, x1).toString());
                    logger.debug("y2...z1 inv" + "  " + addIntervalInverse(solution6, y2, z1).toString());
                    logger.debug("x2...y1 inv" + "  " + addIntervalInverse(solution6, x2, y1).toString());
                    logger.debug("z2...n-1");
                    if (z2 != 0) logger.debug(addInterval(solution6, z2, n - 1).toString());

                    if (!history.contains(solution6)) {
                        res = newSolution(solution6, history);
                        resource -= 1;
                        if (res == true) return new ResultLKH(resource, true);
                        if (resource == 0) return new ResultLKH(resource, false);
                    }
                    else {
                        logger.debug(solution6.toString() + " повтор");
                    }
                }
            }
        }
        return new ResultLKH(resource, false);
    }

    private boolean newSolution(List<Integer> solution, SolutionHistory<R> history){
        LKHSolution<R> rlkhSolution = new LKHSolution<>(problem, solution);
        rlkhSolution.calculateObjectiveFunction();
        history.add(solution, rlkhSolution.value);
        logger.debug(rlkhSolution.toString());
        if (problem.compare(rlkhSolution.value, value) > 0) {
            logger.debug("It is better then " + value);
            path = rlkhSolution.path;
            value = rlkhSolution.value;
            this.solution = new Solution<>(path, value);
            return true;
        }
        return false;
    }
*/
    Integer change_3(Integer resource, SolutionHistory<R> history){
        ResultLKH indet = new ResultLKH(resource, false);
        //int count = 0;
        do{
            if(indet.resource == 0) break;
            indet = improve(indet.resource, history);
            //count++;
        }while(indet.findBest);
        return indet.resource;
    }
}

