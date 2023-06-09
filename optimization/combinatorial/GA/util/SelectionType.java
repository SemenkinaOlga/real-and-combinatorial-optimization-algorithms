package scheduling.optimization.combinatorial.GA.util;

public enum SelectionType {

    TOURNAMENT("Турнирная"), PROPORTIONAL("Пропорциональная"), RANKING("Ранговая");

    private String name;

    SelectionType(final String name) {
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
