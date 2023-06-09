package scheduling.business.logic.model;

public enum ProblemType {

    LOT_ORDER("Задача выбора порядка партий"),
    PRIORITY_ORDER("Задача выбора порядка приоритетов операций"),
    ACTIVITY_PRIORITY("Задача выбора вещественных приоритетов операций"),
    ACTIVITY_START_TIME("Задача выбора времен начала операций"),
    TRAVELLING_SALESMAN_PROBLEM("Задача коммивояжера"),
    REAL_OPTIMIZATION("Вещественная оптимизация"),
    LOT_ORDER_WITH_NESTED("Задача выбора порядка партий с вложенной задачей"),
    NESTED_EQUIPMENT_PRIORITY("Вложенная задача приоритета оборудования");

    private String name;

    ProblemType(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

}
