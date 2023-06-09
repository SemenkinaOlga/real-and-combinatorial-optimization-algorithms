package scheduling.optimization.combinatorial.GA.settings;

import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.combinatorial.GA.util.RecombinationType;

import java.io.Serializable;

public class RecombinationSettings implements OperatorSettings, Serializable {

    private OperatorType operatorType = OperatorType.RECOMBINATION;
    private RecombinationType recombinationType;

    public RecombinationSettings(RecombinationType recombinationType) {
        this.recombinationType = recombinationType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип рекомбинации: ");
        switch (recombinationType) {
            case TYPICAL:
                sb.append("стандартная");
                break;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecombinationSettings that = (RecombinationSettings) o;

        return recombinationType == that.recombinationType;
    }

    @Override
    public int hashCode() {
        return recombinationType.hashCode();
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public RecombinationType getRecombinationType() {
        return recombinationType;
    }

    public void setRecombinationType(RecombinationType recombinationType) {
        this.recombinationType = recombinationType;
    }

}
