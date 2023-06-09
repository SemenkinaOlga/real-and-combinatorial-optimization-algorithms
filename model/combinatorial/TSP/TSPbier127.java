package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPbier127 extends TSPInstance {

    public TSPbier127(){
        coordinates = new ArrayList<>();
        addCoordinates(9860, 14152);
        addCoordinates(9396,  14616);
        addCoordinates(11252,  14848);
        addCoordinates(11020,  13456);
        addCoordinates(9512,  15776);
        addCoordinates(10788,  13804);
        addCoordinates(10208,  14384);
        addCoordinates(11600,  13456);
        addCoordinates(11252,  14036);
        addCoordinates(10672,  15080);
        addCoordinates(11136,  14152);
        addCoordinates(9860,  13108);
        addCoordinates(10092,  14964);
        addCoordinates(9512,  13340);
        addCoordinates(10556,  13688);
        addCoordinates(9628,  14036);
        addCoordinates(10904,  13108);
        addCoordinates(11368,  12644);
        addCoordinates(11252,  13340);
        addCoordinates(10672,  13340);
        addCoordinates(11020,  13108);
        addCoordinates(11020,  13340);
        addCoordinates(11136,  13572);
        addCoordinates(11020,  13688);
        addCoordinates(8468,  11136);
        addCoordinates(8932,  12064);
        addCoordinates(9512,  12412);
        addCoordinates(7772,  11020);
        addCoordinates(8352,  10672);
        addCoordinates(9164,  12876);
        addCoordinates(9744,  12528);
        addCoordinates(8352,  10324);
        addCoordinates(8236,  11020);
        addCoordinates(8468,  12876);
        addCoordinates(8700,  14036);
        addCoordinates(8932,  13688);
        addCoordinates(9048,  13804);
        addCoordinates(8468,  12296);
        addCoordinates(8352,  12644);
        addCoordinates(8236,  13572);
        addCoordinates(9164,  13340);
        addCoordinates(8004,  12760);
        addCoordinates(8584,  13108);
        addCoordinates(7772,  14732);
        addCoordinates(7540,  15080);
        addCoordinates(7424,  17516);
        addCoordinates(8352,  17052);
        addCoordinates(7540,  16820);
        addCoordinates(7888,  17168);
        addCoordinates(9744,  15196);
        addCoordinates(9164,  14964);
        addCoordinates(9744,  16240);
        addCoordinates(7888,  16936);
        addCoordinates(8236,  15428);
        addCoordinates(9512,  17400);
        addCoordinates(9164,  16008);
        addCoordinates(8700,  15312);
        addCoordinates(11716,  16008);
        addCoordinates(12992,  14964);
        addCoordinates(12412,  14964);
        addCoordinates(12296,  15312);
        addCoordinates(12528,  15196);
        addCoordinates(15312,   6612);
        addCoordinates(11716,  16124);
        addCoordinates(11600,  19720);
        addCoordinates(10324,  17516);
        addCoordinates(12412,  13340);
        addCoordinates(12876,  12180);
        addCoordinates(13688,  10904);
        addCoordinates(13688,  11716);
        addCoordinates(13688,  12528);
        addCoordinates(11484,  13224);
        addCoordinates(12296,  12760);
        addCoordinates(12064,  12528);
        addCoordinates(12644,  10556);
        addCoordinates(11832,  11252);
        addCoordinates(11368,  12296);
        addCoordinates(11136,  11020);
        addCoordinates(10556,  11948);
        addCoordinates(10324,  11716);
        addCoordinates(11484,   9512);
        addCoordinates(11484,   7540);
        addCoordinates(11020,   7424);
        addCoordinates(11484,   9744);
        addCoordinates(16936,  12180);
        addCoordinates(17052,  12064);
        addCoordinates(16936,  11832);
        addCoordinates(17052,  11600);
        addCoordinates(13804,  18792);
        addCoordinates(12064,  14964);
        addCoordinates(12180,  15544);
        addCoordinates(14152,  18908);
        addCoordinates(5104,  14616);
        addCoordinates(6496,  17168);
        addCoordinates(5684,  13224);
        addCoordinates(15660,  10788);
        addCoordinates(5336,  10324);
        addCoordinates(812,   6264);
        addCoordinates(14384,  20184);
        addCoordinates(11252,  15776);
        addCoordinates(9744,   3132);
        addCoordinates(10904,   3480);
        addCoordinates(7308,  14848);
        addCoordinates(16472,  16472);
        addCoordinates(10440,  14036);
        addCoordinates(10672,  13804);
        addCoordinates(1160,  18560);
        addCoordinates(10788,  13572);
        addCoordinates(15660,  11368);
        addCoordinates(15544,  12760);
        addCoordinates(5336,  18908);
        addCoordinates(6264,  19140);
        addCoordinates(11832,  17516);
        addCoordinates(10672,  14152);
        addCoordinates(10208,  15196);
        addCoordinates(12180,  14848);
        addCoordinates(11020,  10208);
        addCoordinates(7656,  17052);
        addCoordinates(16240,   8352);
        addCoordinates(10440,  14732);
        addCoordinates(9164,  15544);
        addCoordinates(8004,  11020);
        addCoordinates(5684,  11948);
        addCoordinates(9512,  16472);
        addCoordinates(13688,  17516);
        addCoordinates(11484, 8468);
        addCoordinates(3248, 14152);
        calculateDistances();
    }

    @Override
    public String getName(){ return "bier127"; }

    @Override
    public double getOptimalValue() { return 118282; }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
