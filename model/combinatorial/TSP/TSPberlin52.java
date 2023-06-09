package scheduling.business.logic.model.combinatorial.TSP;

import java.util.ArrayList;

public class TSPberlin52 extends TSPInstance {

    public TSPberlin52(){
        coordinates = new ArrayList<>();
        addCoordinates(565.0, 575.0);//1
        addCoordinates(25.0, 185.0);//2
        addCoordinates(345.0, 750.0);//3
        addCoordinates(945.0, 685.0);//4
        addCoordinates(845.0, 655.0);//5
        addCoordinates(880.0, 660.0);//6
        addCoordinates(25.0, 230.0);//7
        addCoordinates(525.0, 1000.0);//8
        addCoordinates(580.0, 1175.0);//9
        addCoordinates(650.0, 1130.0);//10
        addCoordinates(1605.0, 620.0);//11
        addCoordinates(1220.0, 580.0);//12
        addCoordinates(1465.0, 200.0);//13
        addCoordinates(1530.0, 5.0);//14
        addCoordinates(845.0, 680.0);//15
        addCoordinates(725.0, 370.0);//16
        addCoordinates(145.0, 665.0);//17
        addCoordinates(415.0, 635.0);//18
        addCoordinates(510.0, 875.0);//19
        addCoordinates(560.0, 365.0);//20
        addCoordinates(300.0, 465.0);//21
        addCoordinates(520.0, 585.0);//22
        addCoordinates(480.0, 415.0);//23
        addCoordinates(835.0, 625.0);//24
        addCoordinates(975.0, 580.0);
        addCoordinates(1215.0, 245.0);
        addCoordinates(1320.0, 315.0);
        addCoordinates(1250.0, 400.0);
        addCoordinates(660.0, 180.0);
        addCoordinates(410.0, 250.0);
        addCoordinates(420.0, 555.0);
        addCoordinates(575.0, 665.0);
        addCoordinates(1150.0, 1160.0);
        addCoordinates(700.0, 580.0);
        addCoordinates(685.0, 595.0);
        addCoordinates(685.0, 610.0);
        addCoordinates(770.0, 610.0);
        addCoordinates(795.0, 645.0);
        addCoordinates(720.0, 635.0);
        addCoordinates(760.0, 650.0);
        addCoordinates(475.0, 960.0);
        addCoordinates(95.0, 260.0);
        addCoordinates(875.0, 920.0);
        addCoordinates(700.0, 500.0);
        addCoordinates(555.0, 815.0);
        addCoordinates(830.0, 485.0);
        addCoordinates(1170.0, 65.0);
        addCoordinates(830.0, 610.0);
        addCoordinates(605.0, 625.0);
        addCoordinates(595.0, 360.0);
        addCoordinates(1340.0, 725.0);
        addCoordinates(1740.0, 245.0);
        calculateDistances();
    }

    @Override
    public String getName(){ return "berlin52"; }

    @Override
    public double getOptimalValue() {
        return 7542;
    }

    @Override
    public boolean isOptimumKnown(){
        return true;
    }
}
