package scheduling.optimization.real.RGA.operators;

public enum RecombinationType {
    ONEPOINT("Одноточечное"),
    TWOPOINT("Двух точечное"),
    UNIFORM("Равномерное");

    private String name;

    RecombinationType(final String name) {
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
