package scheduling.optimization;

public interface Model<T, R extends Number> {
    Solution solve(Algorithm<T, R> algorithm) throws Exception;
}
