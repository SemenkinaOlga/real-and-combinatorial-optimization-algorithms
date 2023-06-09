package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPulysses16 extends TSPInstance {

    public TSPulysses16(){
        coordinates = new ArrayList<>();
        addCoordinates(38.24, 20.42);
        addCoordinates(39.57, 26.15);
        addCoordinates(40.56, 25.32);
        addCoordinates(36.26, 23.12);
        addCoordinates(33.48, 10.54);
        addCoordinates(37.56, 12.19);
        addCoordinates(38.42, 13.11);
        addCoordinates(37.52, 20.44);
        addCoordinates(41.23, 9.10);
        addCoordinates(41.17, 13.05);
        addCoordinates(36.08, -5.21);
        addCoordinates(38.47, 15.13);
        addCoordinates(38.15, 15.35);
        addCoordinates(37.51, 15.17);
        addCoordinates(35.49, 14.32);
        addCoordinates(39.36, 19.56);
        calculateDistances();
    }

    @Override
    public String getName(){ return "ulysses16"; }

    @Override
    public double getOptimalValue() {
        return 73.98883887193237;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
