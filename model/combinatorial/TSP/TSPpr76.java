package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPpr76 extends TSPInstance {

    public TSPpr76(){
        coordinates = new ArrayList<>();
        addCoordinates(3600, 2300);
        addCoordinates(3100, 3300);
        addCoordinates(4700, 5750);
        addCoordinates(5400, 5750);
        addCoordinates(5608, 7103);
        addCoordinates(4493, 7102);
        addCoordinates(3600, 6950);
        addCoordinates(3100, 7250);
        addCoordinates(4700, 8450);
        addCoordinates(5400, 8450);
        addCoordinates(5610, 10053);
        addCoordinates(4492, 10052);
        addCoordinates(3600, 10800);
        addCoordinates(3100, 10950);
        addCoordinates(4700, 11650);
        addCoordinates(5400, 11650);
        addCoordinates(6650, 10800);
        addCoordinates(7300, 10950);
        addCoordinates(7300, 7250);
        addCoordinates(6650, 6950);
        addCoordinates(7300, 3300);
        addCoordinates(6650, 2300);
        addCoordinates(5400, 1600);
        addCoordinates(8350, 2300);
        addCoordinates(7850, 3300);
        addCoordinates(9450, 5750);
        addCoordinates(10150, 5750);
        addCoordinates(10358, 7103);
        addCoordinates(9243, 7102);
        addCoordinates(8350, 6950);
        addCoordinates(7850, 7250);
        addCoordinates(9450, 8450);
        addCoordinates(10150, 8450);
        addCoordinates(10360, 10053);
        addCoordinates(9242, 10052);
        addCoordinates(8350, 10800);
        addCoordinates(7850, 10950);
        addCoordinates(9450, 11650);
        addCoordinates(10150, 11650);
        addCoordinates(11400, 10800);
        addCoordinates(12050, 10950);
        addCoordinates(12050, 7250);
        addCoordinates(11400, 6950);
        addCoordinates(12050, 3300);
        addCoordinates(11400, 2300);
        addCoordinates(10150, 1600);
        addCoordinates(13100, 2300);
        addCoordinates(12600, 3300);
        addCoordinates(14200, 5750);
        addCoordinates(14900, 5750);
        addCoordinates(15108, 7103);
        addCoordinates(13993, 7102);
        addCoordinates(13100, 6950);
        addCoordinates(12600, 7250);
        addCoordinates(14200, 8450);
        addCoordinates(14900, 8450);
        addCoordinates(15110, 10053);
        addCoordinates(13992, 10052);
        addCoordinates(13100, 10800);
        addCoordinates(12600, 10950);
        addCoordinates(14200, 11650);
        addCoordinates(14900, 11650);
        addCoordinates(16150, 10800);
        addCoordinates(16800, 10950);
        addCoordinates(16800, 7250);
        addCoordinates(16150, 6950);
        addCoordinates(16800, 3300);
        addCoordinates(16150, 2300);
        addCoordinates(14900, 1600);
        addCoordinates(19800, 800);
        addCoordinates(19800, 10000);
        addCoordinates(19800, 11900);
        addCoordinates(19800, 12200);
        addCoordinates(200, 12200);
        addCoordinates(200, 1100);
        addCoordinates(200, 800);
        calculateDistances();
    }

    @Override
    public String getName(){ return "pr76"; }

    @Override
    public double getOptimalValue() {
        return 108159;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
