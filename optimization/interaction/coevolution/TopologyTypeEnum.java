package scheduling.optimization.interaction.coevolution;

public enum TopologyTypeEnum {

    ALL_WITH_ALL("Все со всеми"),
    BEST_TO_ALL("Лучший со всеми");

    private String name;

    TopologyTypeEnum(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

}
