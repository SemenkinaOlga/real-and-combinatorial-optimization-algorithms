package scheduling.business.logic.model.combinatorial.TSP;

public class TSPInstanceFactory {
    public static TSPInstance getTSPInstanceInstance(int count){
        switch (count){
            case 0:
                return new TSPburma14();
            case 1:
                return new TSPulysses16();
            case 2:
                return new TSPOliver30();
            case 3:
                return new TSPatt48();
            case 4:
                return new TSPEil51();
            case 5:
                return new TSPberlin52();
            case 6:
                return new TSPst70();
            case 7:
                return new TSPpr76();
            case 8:
                return new TSPkroA100();
            case 9:
                return new TSPlin105();
            case 10:
                return new TSPbier127();
            case 11:
                return new TSPch150();
            case 12:
                return new TSPrat195();
            case 13:
                return new TSPtest1();
        }
        return new TSPOliver30();
    }
}
