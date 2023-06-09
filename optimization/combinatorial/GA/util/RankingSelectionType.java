package scheduling.optimization.combinatorial.GA.util;

public enum RankingSelectionType {

    LINEAR("Линейная"), EXPONENTIAL("Экспонинцеальная");

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
