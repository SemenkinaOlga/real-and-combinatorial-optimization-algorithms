package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPlin105 extends TSPInstance {

    public TSPlin105(){
        coordinates = new ArrayList<>();
        addCoordinates(63, 71);
        addCoordinates(94, 71);
        addCoordinates(142, 370);
        addCoordinates(173, 1276);
        addCoordinates(205, 1213);
        addCoordinates(213, 69);
        addCoordinates(244, 69);
        addCoordinates(276, 630);
        addCoordinates(283, 732);
        addCoordinates(362, 69);
        addCoordinates(394, 69);
        addCoordinates(449, 370);
        addCoordinates(480, 1276);
        addCoordinates(512, 1213);
        addCoordinates(528, 157);
        addCoordinates(583, 630);
        addCoordinates(591, 732);
        addCoordinates(638, 654);
        addCoordinates(638, 496);
        addCoordinates(638, 314);
        addCoordinates(638, 142);
        addCoordinates(669, 142);
        addCoordinates(677, 315);
        addCoordinates(677, 496);
        addCoordinates(677, 654);
        addCoordinates(709, 654);
        addCoordinates(709, 496);
        addCoordinates(709, 315);
        addCoordinates(701, 142);
        addCoordinates(764, 220);
        addCoordinates(811, 189);
        addCoordinates(843, 173);
        addCoordinates(858, 370);
        addCoordinates(890, 1276);
        addCoordinates(921, 1213);
        addCoordinates(992, 630);
        addCoordinates(1000, 732);
        addCoordinates(1197, 1276);
        addCoordinates(1228, 1213);
        addCoordinates(1276, 205);
        addCoordinates(1299, 630);
        addCoordinates(1307, 732);
        addCoordinates(1362, 654);
        addCoordinates(1362, 496);
        addCoordinates(1362, 291);
        addCoordinates(1425, 654);
        addCoordinates(1425, 496);
        addCoordinates(1425, 291);
        addCoordinates(1417, 173);
        addCoordinates(1488, 291);
        addCoordinates(1488, 496);
        addCoordinates(1488, 654);
        addCoordinates(1551, 654);
        addCoordinates(1551, 496);
        addCoordinates(1551, 291);
        addCoordinates(1614, 291);
        addCoordinates(1614, 496);
        addCoordinates(1614, 654);
        addCoordinates(1732, 189);
        addCoordinates(1811, 1276);
        addCoordinates(1843, 1213);
        addCoordinates(1913, 630);
        addCoordinates(1921, 732);
        addCoordinates(2087, 370);
        addCoordinates(2118, 1276);
        addCoordinates(2150, 1213);
        addCoordinates(2189, 205);
        addCoordinates(2220, 189);
        addCoordinates(2220, 630);
        addCoordinates(2228, 732);
        addCoordinates(2244, 142);
        addCoordinates(2276, 315);
        addCoordinates(2276, 496);
        addCoordinates(2276, 654);
        addCoordinates(2315, 654);
        addCoordinates(2315, 496);
        addCoordinates(2315, 315);
        addCoordinates(2331, 142);
        addCoordinates(2346, 315);
        addCoordinates(2346, 496);
        addCoordinates(2346, 654);
        addCoordinates(2362, 142);
        addCoordinates(2402, 157);
        addCoordinates(2402, 220);
        addCoordinates(2480, 142);
        addCoordinates(2496, 370);
        addCoordinates(2528, 1276);
        addCoordinates(2559, 1213);
        addCoordinates(2630, 630);
        addCoordinates(2638, 732);
        addCoordinates(2756, 69);
        addCoordinates(2787, 69);
        addCoordinates(2803, 370);
        addCoordinates(2835, 1276);
        addCoordinates(2866, 1213);
        addCoordinates(2906, 69);
        addCoordinates(2937, 69);
        addCoordinates(2937, 630);
        addCoordinates(2945, 732);
        addCoordinates(3016, 1276);
        addCoordinates(3055, 69);
        addCoordinates(3087, 69);
        addCoordinates(606, 220);
        addCoordinates(1165, 370);
        addCoordinates(1780, 370);
        calculateDistances();
    }

    @Override
    public String getName(){ return "lin105"; }

    @Override
    public double getOptimalValue() { return 14379; }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
