package scheduling.optimization.real.RGA.standard.operators;

import scheduling.optimization.real.RGA.operators.MutationType;

public class MutationSettings {
    private MutationType mutationType;

    public MutationSettings(MutationType mutationType){
        this.mutationType = mutationType;
    }

    public MutationType getMutationType(){ return mutationType; }

    public String toString() {
        return "Тип мутации: " +
                mutationType.getDisplayName();
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (mutationType != null) {
            result = 31 * result + mutationType.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MutationSettings other = (MutationSettings) obj;

        if (mutationType == null) {
            if (other.mutationType != null)
                return false;
        } else if (!mutationType.equals(other.mutationType))
            return false;

        return true;
    }

    public String getParametersString() {
        return mutationType.getDisplayName() + "\t";
    }

    public double getFinalProbability(int bitNumberPerVariable, int dimension){
        double mutationProbability = 1 / (double)(dimension * bitNumberPerVariable);
        switch (mutationType){
            case VERYLOW:
                return mutationProbability * 0.2;
            case LOW:
                return mutationProbability * 0.5;
            case AVERAGE:
                return mutationProbability;
            case HIGH:
                return mutationProbability * 2;
            case VERYHIGH:
                return mutationProbability * 5;
        }
        return mutationProbability;
    }
}
