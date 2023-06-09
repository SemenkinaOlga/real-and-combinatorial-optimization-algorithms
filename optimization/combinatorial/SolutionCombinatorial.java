package scheduling.optimization.combinatorial;

import scheduling.optimization.Solution;

import java.util.List;
import java.util.Random;

public class SolutionCombinatorial<T, R> extends Solution <T, R> {
    public SolutionCombinatorial(List<T> data, R value) {
        super(data, value);
    }

    public SolutionCombinatorial(Solution other) {
        super(other);
    }

    public void SwapData(int i, int j)
    {
        int size = data.size();
        if (i < size && j < size && i >= 0 && j >= 0 && i != j)
        {
            T tmp = data.get(i);
            data.set(i, data.get(j));
            data.set(j, tmp);
        }
    }

    public void MakeRandomPermutation()
    {
        // Generating a random permutation of a finite sequence by Fisher–Yates shuffle
        int dimension = data.size();
        Random rnd = new Random();
        for (int i = dimension - 1; i >= 0; i--)
        {
            int j = rnd.nextInt(i + 1); // Return random int number 0 <= j < i + 1
            T tmp = data.get(i);
            data.set(i, data.get(j));
            data.set(j, tmp);
        }
    }
}
