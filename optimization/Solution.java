package scheduling.optimization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Solution <T, R> implements Serializable {
    protected List<T> data;
    protected R value;

    public Solution(List<T> data, R value) {
        this.data = data;
        this.value = value;
    }

    public Solution(Solution<T, R> other) {
        this.data = new ArrayList<>(other.getData());
        this.value = (R)other.getValue();
    }

    public List<T> getData() {
        return data;
    }

    public R getValue() {
        return value;
    }

    public void setValue(R value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "data=" + data +
                ", value=" + value +
                '}';
    }
}
