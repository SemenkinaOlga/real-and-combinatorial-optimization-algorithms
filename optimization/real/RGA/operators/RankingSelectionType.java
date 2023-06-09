package scheduling.optimization.real.RGA.operators;

public enum RankingSelectionType {
    LINEAR("Линейная"), EXPONENTIAL("Экспоненциальная");

    private String name;

    RankingSelectionType(final String name) {
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
