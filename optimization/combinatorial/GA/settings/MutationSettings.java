package scheduling.optimization.combinatorial.GA.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduling.optimization.combinatorial.GA.util.MutationProbabilityType;
import scheduling.optimization.combinatorial.GA.util.MutationType;
import scheduling.optimization.combinatorial.GA.util.OperatorType;

import java.io.Serializable;

public class MutationSettings implements OperatorSettings, Serializable {

    private static Logger logger = LoggerFactory.getLogger(MutationSettings.class);

    private OperatorType operatorType = OperatorType.MUTATION;
    private MutationType mutationType;
    private MutationProbabilityType mutationProbabilityType;
    private Double mutationProbability;

    public MutationSettings(MutationType mutationType, MutationProbabilityType mutationProbabilityType) {
        this.mutationType = mutationType;
        this.mutationProbabilityType = mutationProbabilityType;
        calcMutationProbability();
    }

    public MutationSettings(MutationSettings settings) {
        this.mutationType = settings.mutationType;
        this.mutationProbabilityType = settings.mutationProbabilityType;
        calcMutationProbability();
    }

    public void calcMutationProbability() {
        double probability = 0;
        switch (mutationProbabilityType) {
            case VERY_LOW:
                probability = 0.2;
                break;
            case LOW:
                probability = 0.5;
                break;
            case AVERAGE:
                probability = 1;
                break;
            case HIGH:
                probability = 1.5;
                break;
            case VERY_HIGH:
                probability = 2;
                break;
        }
        mutationProbability = probability;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип мутации: ");
        switch (mutationType) {
            case BY_2_EXCHANGE:
                sb.append("мутация обменом");
                break;
            case BY_INVERSION:
                sb.append("мутация инверсией");
                break;
            case BY_INSERTION:
                sb.append("мутация вставкой");
                break;
            case BY_SHIFTING:
                sb.append("мутация сдвигом");
                break;
        }

        if (mutationType != MutationType.BY_2_EXCHANGE) {
            sb.append(". ").append(mutationProbabilityType).append(" вероятность мутации");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MutationSettings that = (MutationSettings) o;

        if (mutationType != that.mutationType) return false;
        if (mutationProbabilityType != that.mutationProbabilityType) return false;
        return mutationProbability.equals(that.mutationProbability);
    }

    @Override
    public int hashCode() {
        int result = mutationType.hashCode();
        result = 31 * result + mutationProbabilityType.hashCode();
        result = 31 * result + mutationProbability.hashCode();
        return result;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public Double getMutationProbability() {
        return mutationProbability;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public MutationProbabilityType getMutationProbabilityType() {
        return mutationProbabilityType;
    }

    public void setMutationProbabilityType(MutationProbabilityType mutationProbabilityType) {
        this.mutationProbabilityType = mutationProbabilityType;
    }

}
