package scheduling.optimization.combinatorial.IWDs;

import scheduling.optimization.IAlgorithmParameters;

public class IWDsParameters implements IAlgorithmParameters {

    private double av;
    private double bv;
    private double cv;

    private double as;
    private double bs;
    private double cs;

    private double es;

    private double pn;
    private double pIWD;

    private double initSoil;
    private double initVel;

    private double alpha;
    private int N1;

    public double getAv() {
        return av;
    }

    public void setAv(double av) {
        this.av = av;
    }

    public double getBv() {
        return bv;
    }

    public void setBv(double bv) {
        this.bv = bv;
    }

    public double getCv() {
        return cv;
    }

    public void setCv(double cv) {
        this.cv = cv;
    }

    public double getAs() {
        return as;
    }

    public void setAs(double as) {
        this.as = as;
    }

    public double getBs() {
        return bs;
    }

    public void setBs(double bs) {
        this.bs = bs;
    }

    public double getCs() {
        return cs;
    }

    public void setCs(double cs) {
        this.cs = cs;
    }

    public double getEs() {
        return es;
    }

    public void setEs(double es) {
        this.es = es;
    }

    public double getPn() {
        return pn;
    }

    public void setPn(double pn) {
        this.pn = pn;
    }

    public double getpIWD() {
        return pIWD;
    }

    public void setpIWD(double pIWD) {
        this.pIWD = pIWD;
    }

    public double getInitSoil() {
        return initSoil;
    }

    public void setInitSoil(double initSoil) {
        this.initSoil = initSoil;
    }

    public double getInitVel() {
        return initVel;
    }

    public void setInitVel(double initVel) {
        this.initVel = initVel;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getN1() {
        return N1;
    }

    public void setN1(int n1) {
        N1 = n1;
    }

    public IWDsParameters() {
        av = 1;
        bv = 0.01;
        cv = 1;
        as = 1;
        bs = 0.01;
        cs = 1;
        pIWD = 0.9;
        N1 = 15;
        es = 0.001;
        alpha = 1;
        initSoil = 100000;
        initVel = 0;
        pn = 0.8;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "a: " + av +
                ", bv: " + bv +
                ", cv: " + cv +
                ", as: " + as +
                ", bs: " + bs +
                ", cs: " + cs +
                ", es: " + es +
                ", pn: " + pn +
                ", pIWD: " + pIWD +
                ", InitSoil: " + initSoil +
                ", initVelocity: " + initVel +
                ", α: " + alpha +
                ", N1: " + N1 +
                '}';
    }

    @Override
    public String getParametersString() {
        return av + "\t" +
                bv + "\t" +
                cv + "\t" +
                as + "\t" +
                bs + "\t" +
                cs + "\t" +
                es + "\t" +
                pn + "\t" +
                pIWD + "\t" +
                initSoil + "\t" +
                initVel + "\t" +
                alpha + "\t"+
                N1 + "\t";
    }
}
