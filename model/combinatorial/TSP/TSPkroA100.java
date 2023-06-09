package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPkroA100 extends TSPInstance {

    public TSPkroA100(){
        coordinates = new ArrayList<>();
        addCoordinates(1380, 939);
        addCoordinates(2848, 96);
        addCoordinates(3510, 1671);
        addCoordinates(457, 334);
        addCoordinates(3888, 666);
        addCoordinates(984, 965);
        addCoordinates(2721, 1482);
        addCoordinates(1286, 525);
        addCoordinates(2716, 1432);
        addCoordinates(738, 1325);
        addCoordinates(1251, 1832);
        addCoordinates(2728, 1698);
        addCoordinates(3815, 169);
        addCoordinates(3683, 1533);
        addCoordinates(1247, 1945);
        addCoordinates(123, 862);
        addCoordinates(1234, 1946);
        addCoordinates(252, 1240);
        addCoordinates(611, 673);
        addCoordinates(2576, 1676);
        addCoordinates(928, 1700);
        addCoordinates(53, 857);
        addCoordinates(1807, 1711);
        addCoordinates(274, 1420);
        addCoordinates(2574, 946);
        addCoordinates(178, 24);
        addCoordinates(2678, 1825);
        addCoordinates(1795, 962);
        addCoordinates(3384, 1498);
        addCoordinates(3520, 1079);
        addCoordinates(1256, 61);
        addCoordinates(1424, 1728);
        addCoordinates(3913, 192);
        addCoordinates(3085, 1528);
        addCoordinates(2573, 1969);
        addCoordinates(463, 1670);
        addCoordinates(3875, 598);
        addCoordinates(298, 1513);
        addCoordinates(3479, 821);
        addCoordinates(2542, 236);
        addCoordinates(3955, 1743);
        addCoordinates(1323, 280);
        addCoordinates(3447, 1830);
        addCoordinates(2936, 337);
        addCoordinates(1621, 1830);
        addCoordinates(3373, 1646);
        addCoordinates(1393, 1368);
        addCoordinates(3874, 1318);
        addCoordinates(938, 955);
        addCoordinates(3022, 474);
        addCoordinates(2482, 1183);
        addCoordinates(3854, 923);
        addCoordinates(376, 825);
        addCoordinates(2519, 135);
        addCoordinates(2945, 1622);
        addCoordinates(953, 268);
        addCoordinates(2628, 1479);
        addCoordinates(2097, 981);
        addCoordinates(890, 1846);
        addCoordinates(2139, 1806);
        addCoordinates(2421, 1007);
        addCoordinates(2290, 1810);
        addCoordinates(1115, 1052);
        addCoordinates(2588, 302);
        addCoordinates(327, 265);
        addCoordinates(241, 341);
        addCoordinates(1917, 687);
        addCoordinates(2991, 792);
        addCoordinates(2573, 599);
        addCoordinates(19, 674);
        addCoordinates(3911, 1673);
        addCoordinates(872, 1559);
        addCoordinates(2863, 558);
        addCoordinates(929, 1766);
        addCoordinates(839, 620);
        addCoordinates(3893, 102);
        addCoordinates(2178, 1619);
        addCoordinates(3822, 899);
        addCoordinates(378, 1048);
        addCoordinates(1178, 100);
        addCoordinates(2599, 901);
        addCoordinates(3416, 143);
        addCoordinates(2961, 1605);
        addCoordinates(611, 1384);
        addCoordinates(3113, 885);
        addCoordinates(2597, 1830);
        addCoordinates(2586, 1286);
        addCoordinates(161, 906);
        addCoordinates(1429, 134);
        addCoordinates(742, 1025);
        addCoordinates(1625, 1651);
        addCoordinates(1187, 706);
        addCoordinates(1787, 1009);
        addCoordinates(22, 987);
        addCoordinates(3640, 43);
        addCoordinates(3756, 882);
        addCoordinates(776, 392);
        addCoordinates(1724, 1642);
        addCoordinates(198, 1810);
        addCoordinates(3950, 1558);
        calculateDistances();
    }

    @Override
    public String getName(){ return "kroA100"; }

    @Override
    public double getOptimalValue() {
        return 21282;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
