package scheduling.optimization.real.RGA.operators;

public enum MutationType {
    VERYLOW("Очень низкая"),
    LOW("Низкая"),
    AVERAGE("Средняя"),
    HIGH("Высокая"),
    VERYHIGH("Очень высокая");

    private String name;

    MutationType(final String name) {
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
