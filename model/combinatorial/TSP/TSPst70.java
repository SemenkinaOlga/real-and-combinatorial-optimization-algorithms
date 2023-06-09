package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPst70 extends TSPInstance {

    public TSPst70(){
        coordinates = new ArrayList<>();
        addCoordinates(64, 96);
        addCoordinates(80, 39);
        addCoordinates(69, 23);
        addCoordinates(72, 42);
        addCoordinates(48, 67);
        addCoordinates(58, 43);
        addCoordinates(81, 34);
        addCoordinates(79, 17);
        addCoordinates(30, 23);
        addCoordinates(42, 67);
        addCoordinates(7, 76);
        addCoordinates(29, 51);
        addCoordinates(78, 92);
        addCoordinates(64, 8);
        addCoordinates(95, 57);
        addCoordinates(57, 91);
        addCoordinates(40, 35);
        addCoordinates(68, 40);
        addCoordinates(92, 34);
        addCoordinates(62, 1);
        addCoordinates(28, 43);
        addCoordinates(76, 73);
        addCoordinates(67, 88);
        addCoordinates(93, 54);
        addCoordinates(6, 8);
        addCoordinates(87, 18);
        addCoordinates(30, 9);
        addCoordinates(77, 13);
        addCoordinates(78, 94);
        addCoordinates(55, 3);
        addCoordinates(82, 88);
        addCoordinates(73, 28);
        addCoordinates(20, 55);
        addCoordinates(27, 43);
        addCoordinates(95, 86);
        addCoordinates(67, 99);
        addCoordinates(48, 83);
        addCoordinates(75, 81);
        addCoordinates(8, 19);
        addCoordinates(20, 18);
        addCoordinates(54, 38);
        addCoordinates(63, 36);
        addCoordinates(44, 33);
        addCoordinates(52, 18);
        addCoordinates(12, 13);
        addCoordinates(25, 5);
        addCoordinates(58, 85);
        addCoordinates(5, 67);
        addCoordinates(90, 9);
        addCoordinates(41, 76);
        addCoordinates(25, 76);
        addCoordinates(37, 64);
        addCoordinates(56, 63);
        addCoordinates(10, 55);
        addCoordinates(98, 7);
        addCoordinates(16, 74);
        addCoordinates(89, 60);
        addCoordinates(48, 82);
        addCoordinates(81, 76);
        addCoordinates(29, 60);
        addCoordinates(17, 22);
        addCoordinates(5, 45);
        addCoordinates(79, 70);
        addCoordinates(9, 100);
        addCoordinates(17, 82);
        addCoordinates(74, 67);
        addCoordinates(10, 68);
        addCoordinates(48, 19);
        addCoordinates(83, 86);
        addCoordinates(84, 94);
        calculateDistances();
    }

    @Override
    public String getName(){ return "st70"; }

    @Override
    public double getOptimalValue() {
        return 675;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
