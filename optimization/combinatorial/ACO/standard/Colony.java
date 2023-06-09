package scheduling.optimization.combinatorial.ACO.standard;

import scheduling.optimization.Problem;
import scheduling.optimization.Solution;
import scheduling.optimization.combinatorial.ACO.ColonyBase;

import java.util.ArrayList;

public class Colony<R extends Number> extends ColonyBase<R, Ant<R>> {

    private ACOParameters parameters;

    public Colony(int antAmount, Problem<Integer, R> problem, ACOParameters parameters) {
        super(antAmount, problem);
        this.parameters = parameters;
    }

    @Override
    public void initialization() {
        ants = new ArrayList<>();
        for (int i = 0; i < antAmount; i++) {
            ants.add(new Ant<>(problem, parameters));
        }
    }

    public void addAnts(int size) {
        antAmount += size;
        for (int i = 0; i < size; i++) {
            Ant<R> ant = new Ant<>(problem, parameters);
            ant.generateAnyPath();
            ants.add(ant);
        }
    }

    public void addAnt(Solution<Integer, R> solution){
        ants.add(new Ant<>(problem, parameters, solution));
    }
}
