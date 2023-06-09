package scheduling.business.logic.model.real.realOptimization;

public class RealProblemInstanceFactory {
    public static RealProblemInstance getRealProblemInstance(int count){
        switch (count){
            case 0:
                return new RealProblemInstanceFunction1();
            case 1:
                return new RealProblemInstanceFunction2();
            case 2:
                return new RealProblemInstanceFunction3();
            case 3:
                return new RealProblemInstanceFunction4();
            case 4:
                return new RealProblemInstanceFunction5();
            case 5:
                return new RealProblemInstanceFunction6();
            case 6:
                return new RealProblemInstanceFunction7();
            case 7:
                return new RealProblemInstanceFunction8();
            case 8:
                return new RealProblemInstanceFunction9();
            case 9:
                return new RealProblemInstanceFunction10();
            case 10:
                return new RealProblemInstanceFunction11();
            case 11:
                return new RealProblemInstanceFunction12();
            case 12:
                return new RealProblemInstanceFunction13();
            case 13:
                return new RealProblemInstanceFunction14();
            case 14:
                return new RealProblemInstanceFunctionCEC1();
            case 15:
                return new RealProblemInstanceFunctionCEC2();
            case 16:
                return new RealProblemInstanceFunctionCEC3();
            case 17:
                return new RealProblemInstanceFunctionCEC4();
            case 18:
                return new RealProblemInstanceFunctionCEC5();
            case 19:
                return new RealProblemInstanceFunctionCEC6();
            case 20:
                return new RealProblemInstanceFunctionCEC7();
            case 21:
                return new RealProblemInstanceFunctionCEC8();
            case 22:
                return new RealProblemInstanceFunctionCEC9();
            case 23:
                return new RealProblemInstanceFunctionCEC10();
            case 24:
                return new RealProblemInstanceFunctionCEC1_10();
            case 25:
                return new RealProblemInstanceFunctionCEC2_10();
            case 26:
                return new RealProblemInstanceFunctionCEC3_10();
            case 27:
                return new RealProblemInstanceFunctionCEC4_10();
            case 28:
                return new RealProblemInstanceFunctionCEC5_10();
            case 29:
                return new RealProblemInstanceFunctionCEC6_10();
            case 30:
                return new RealProblemInstanceFunctionCEC7_10();
            case 31:
                return new RealProblemInstanceFunctionCEC8_10();
            case 32:
                return new RealProblemInstanceFunctionCEC9_10();
            case 33:
                return new RealProblemInstanceFunctionCEC10_10();
        }
        return new RealProblemInstanceFunction1();
    }
}
