package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPtest1 extends TSPInstance {

    public TSPtest1(){
        coordinates = new ArrayList<>();
        addCoordinates(0, 0);
        addCoordinates(0, 1);
        addCoordinates(0, 2);
        addCoordinates(1, 0);
        addCoordinates(1, 1);
        addCoordinates(1, 2);
        calculateDistances();
    }

    @Override
    public String getName(){ return "test1"; }

    @Override
    public double getOptimalValue() {
        return 6;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
