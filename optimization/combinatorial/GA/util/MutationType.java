package scheduling.optimization.combinatorial.GA.util;

public enum MutationType {

    BY_2_EXCHANGE("Обменом"), BY_INVERSION("Инверсией"), BY_INSERTION("Вставкой"), BY_SHIFTING("Сдвигом");

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
