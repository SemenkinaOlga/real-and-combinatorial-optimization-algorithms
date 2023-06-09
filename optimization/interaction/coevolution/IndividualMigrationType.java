package scheduling.optimization.interaction.coevolution;

public enum IndividualMigrationType {

    BEST_REPLACE_WORST("Лучший заменяет худшего"),
    BEST_REPLACE_RANDOM("Лучший заменяет случайного"),
    RANDOM_REPLACE_WORST("Случайный заменяет худшего"),
    RANDOM_REPLACE_RANDOM("Случайный заменяет случайного"),
    SAME_BEST_START_POPULATION("Одинаковая популяция лучших индивидов для всех");

    private String name;

    IndividualMigrationType(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

}
