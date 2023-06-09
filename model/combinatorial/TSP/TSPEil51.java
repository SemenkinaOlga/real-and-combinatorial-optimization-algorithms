package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;
import java.util.List;

public class TSPEil51 extends TSPInstance {

    public TSPEil51(){
        coordinates = new ArrayList<>();
        addCoordinates(37, 52);
        addCoordinates(49, 49);
        addCoordinates(52, 64);
        addCoordinates(20, 26);
        addCoordinates(40, 30);
        addCoordinates(21, 47);
        addCoordinates(17, 63);
        addCoordinates(31, 62);
        addCoordinates(52, 33);
        addCoordinates(51, 21);
        addCoordinates(42, 41);
        addCoordinates(31, 32);
        addCoordinates(5, 25);
        addCoordinates(12, 42);
        addCoordinates(36, 16);
        addCoordinates(52, 41);
        addCoordinates(27, 23);
        addCoordinates(17, 33);
        addCoordinates(13, 13);
        addCoordinates(57, 58);
        addCoordinates(62, 42);
        addCoordinates(42, 57);
        addCoordinates(16, 57);
        addCoordinates(8, 52);
        addCoordinates(7, 38);
        addCoordinates(27, 68);
        addCoordinates(30, 48);
        addCoordinates(43, 67);
        addCoordinates(58, 48);
        addCoordinates(58, 27);
        addCoordinates(37, 69);
        addCoordinates(38, 46);
        addCoordinates(46, 10);
        addCoordinates(61, 33);
        addCoordinates(62, 63);
        addCoordinates(63, 69);
        addCoordinates(32, 22);
        addCoordinates(45, 35);
        addCoordinates(59, 15);
        addCoordinates(5, 6);
        addCoordinates(10, 17);
        addCoordinates(21, 10);
        addCoordinates(5, 64);
        addCoordinates(30, 15);
        addCoordinates(39, 10);
        addCoordinates(32, 39);
        addCoordinates(25, 32);
        addCoordinates(25, 55);
        addCoordinates(48, 28);
        addCoordinates(56, 37);
        addCoordinates(30, 40);
        calculateDistances();
    }

    @Override
    public String getName(){ return "Eil51"; }

    @Override
    public double getOptimalValue() {
        return 426;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
