package scheduling.optimization;

import scheduling.bean.algorithm.util.LoggerWrapper;

import java.util.List;
import java.util.UUID;

public interface IIAlgorithm {

    UUID uuid = UUID.randomUUID();

    void setLoggers(List<LoggerWrapper> loggers);

    String getName();

    void setName(String name);

    IAlgorithmParameters getParameters();

}
