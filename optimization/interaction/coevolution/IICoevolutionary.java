package scheduling.optimization.interaction.coevolution;

import java.util.List;

public interface IICoevolutionary<R> extends IIICoevolutionary {
    List<R> makeSteps(int amount);
}
