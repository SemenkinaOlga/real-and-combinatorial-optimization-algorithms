package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPatt48 extends TSPInstance {

    public TSPatt48(){
        coordinates = new ArrayList<>();
        addCoordinates(6734, 1453);
        addCoordinates(2233, 10);
        addCoordinates(5530, 1424);
        addCoordinates(401, 841);
        addCoordinates(3082, 1644);
        addCoordinates(7608, 4458);
        addCoordinates(7573, 3716);
        addCoordinates(7265, 1268);
        addCoordinates(6898, 1885);
        addCoordinates(1112, 2049);
        addCoordinates(5468, 2606);
        addCoordinates(5989, 2873);
        addCoordinates(4706, 2674);
        addCoordinates(4612, 2035);
        addCoordinates(6347, 2683);
        addCoordinates(6107, 669);
        addCoordinates(7611, 5184);
        addCoordinates(7462, 3590);
        addCoordinates(7732, 4723);
        addCoordinates(5900, 3561);
        addCoordinates(4483, 3369);
        addCoordinates(6101, 1110);
        addCoordinates(5199, 2182);
        addCoordinates(1633, 2809);
        addCoordinates(4307, 2322);
        addCoordinates(675, 1006);
        addCoordinates(7555, 4819);
        addCoordinates(7541, 3981);
        addCoordinates(3177, 756);
        addCoordinates(7352, 4506);
        addCoordinates(7545, 2801);
        addCoordinates(3245, 3305);
        addCoordinates(6426, 3173);
        addCoordinates(4608, 1198);
        addCoordinates(23, 2216);
        addCoordinates(7248, 3779);
        addCoordinates(7762, 4595);
        addCoordinates(7392, 2244);
        addCoordinates(3484, 2829);
        addCoordinates(6271, 2135);
        addCoordinates(4985, 140);
        addCoordinates(1916, 1569);
        addCoordinates(7280, 4899);
        addCoordinates(7509, 3239);
        addCoordinates(10, 2676);
        addCoordinates(6807, 2993);
        addCoordinates(5185, 3258);
        addCoordinates(3023, 1942);
        calculateDistances();
    }

    @Override
    public String getName(){ return "att48"; }

    @Override
    public double getOptimalValue() {
        return 10628;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
