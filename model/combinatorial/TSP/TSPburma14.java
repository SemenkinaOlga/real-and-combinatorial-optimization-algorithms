package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPburma14 extends TSPInstance {

    public TSPburma14(){
        coordinates = new ArrayList<>();
        addCoordinates(16.47, 96.10);
        addCoordinates(16.47, 94.44);
        addCoordinates(20.09, 92.54);
        addCoordinates(22.39, 93.37);
        addCoordinates(25.23, 97.24);
        addCoordinates(22.00, 96.05);
        addCoordinates(20.47, 97.02);
        addCoordinates(17.20, 96.29);
        addCoordinates(16.30, 97.38);
        addCoordinates(14.05, 98.12);
        addCoordinates(16.53, 97.38);
        addCoordinates(21.52, 95.59);
        addCoordinates(19.41, 97.13);
        addCoordinates(20.09, 94.55);
        calculateDistances();
    }

    @Override
    public String getName(){ return "burma14"; }

    @Override
    public double getOptimalValue() {
        return 30.878503892587997;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
