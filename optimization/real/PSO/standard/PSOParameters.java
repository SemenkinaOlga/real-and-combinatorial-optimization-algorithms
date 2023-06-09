package scheduling.optimization.real.PSO.standard;

import scheduling.optimization.IAlgorithmParameters;

public class PSOParameters implements IAlgorithmParameters {
    private double cognitiveParameter;
    private double socialParameter;
    private double w;

    public double getCognitiveParameter() {
        return cognitiveParameter;
    }

    public void setCognitiveParameter(double cognitiveParameter) {
        this.cognitiveParameter = cognitiveParameter;
    }

    public double getSocialParameter() {
        return socialParameter;
    }

    public void setSocialParameter(double socialParameter) {
        this.socialParameter = socialParameter;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public PSOParameters() {
        socialParameter = 1;
        cognitiveParameter = 1;
        w = 1;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "socialParameter: " + socialParameter +
                "cognitiveParameter: " + cognitiveParameter +
                "w: " + w +
                '}';
    }

    @Override
    public String getParametersString() {
        return socialParameter + "\t" +
                cognitiveParameter + "\t"+
                w + "\t";
    }
}
