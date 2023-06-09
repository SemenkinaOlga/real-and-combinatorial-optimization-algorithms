package scheduling.optimization;

public class Parameter<T> {
    T value;
    String name;

    public Parameter(T value, String name) {
        this.value = value;
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": " + value.toString();
    }
}
