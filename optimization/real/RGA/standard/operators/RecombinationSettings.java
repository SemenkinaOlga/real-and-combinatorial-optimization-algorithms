package scheduling.optimization.real.RGA.standard.operators;

import scheduling.optimization.real.RGA.operators.RecombinationType;

public class RecombinationSettings {
    private RecombinationType recombinationType;

    public RecombinationSettings(RecombinationType recombinationType){
        this.recombinationType = recombinationType;
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (recombinationType != null) {
            result = 31 * result + recombinationType.hashCode();
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
        final RecombinationSettings other = (RecombinationSettings) obj;

        if (recombinationType == null) {
            if (other.recombinationType != null)
                return false;
        } else if (!recombinationType.equals(other.recombinationType))
            return false;

        return true;
    }

    public RecombinationType getRecombinationType(){ return recombinationType; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип скрещивания: ");
        switch (recombinationType) {
            case ONEPOINT:
                sb.append("одноточечное");
                break;
            case TWOPOINT:
                sb.append("двухточечное");
                break;
            case UNIFORM:
                sb.append("равномерное");
                break;
        }
        return sb.toString();
    }

    public String getParametersString() {
        return recombinationType.getDisplayName() + "\t";
    }
}
