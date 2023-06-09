package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;
import java.util.List;

public class TSPOliver30 extends TSPInstance {

    public TSPOliver30(){
        coordinates = new ArrayList<>();
        addCoordinates(54, 67);
        addCoordinates(54, 62);
        addCoordinates(37,	84);
        addCoordinates(41,	94);
        addCoordinates(2,	99);
        addCoordinates(7,	64);
        addCoordinates(25,	62);
        addCoordinates(22,	60);
        addCoordinates(18,	54);
        addCoordinates(4,	50);
        addCoordinates(13,	40);
        addCoordinates(18,	40);
        addCoordinates(24,	42);
        addCoordinates(25,	38);
        addCoordinates(44,	35);
        addCoordinates(41,	26);
        addCoordinates(45,	21);
        addCoordinates(58,	35);
        addCoordinates(62,	32);
        addCoordinates(82,	7);
        addCoordinates(91,	38);
        addCoordinates(83,	46);
        addCoordinates(71,	44);
        addCoordinates(64,	60);
        addCoordinates(68,	58);
        addCoordinates(83,	69);
        addCoordinates(87,	76);
        addCoordinates(74,	78);
        addCoordinates(71,	71);
        addCoordinates(58,	69);
        calculateDistances();
    }

    @Override
    public String getName(){ return "Oliver30"; }

    @Override
    public double getOptimalValue() {
        return 423.741;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
