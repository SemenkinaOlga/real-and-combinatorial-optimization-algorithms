package scheduling.optimization.real.DE;

public enum DEType {
    BY_RANDOM("С использованием трех случайных векторов"),
    BY_BEST("С использованием лучшего вектора");

    private String name;

    DEType(final String name) {
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
