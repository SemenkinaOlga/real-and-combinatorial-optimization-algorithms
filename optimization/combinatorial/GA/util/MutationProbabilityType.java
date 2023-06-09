package scheduling.optimization.combinatorial.GA.util;

public enum MutationProbabilityType {

    VERY_LOW("Очень низкая"), LOW("Низкая"), AVERAGE("Средняя"), HIGH("Высокая"), VERY_HIGH("Очень высокая");

    private String name;

    MutationProbabilityType(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
