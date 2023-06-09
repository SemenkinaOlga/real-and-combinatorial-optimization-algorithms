package scheduling.optimization.combinatorial.GA.util;

import scheduling.optimization.Problem;
import scheduling.optimization.combinatorial.GA.operators.Mutation;
import scheduling.optimization.combinatorial.GA.operators.Operator;
import scheduling.optimization.combinatorial.GA.operators.Recombination;
import scheduling.optimization.combinatorial.GA.operators.Selection;
import scheduling.optimization.combinatorial.GA.settings.MutationSettings;
import scheduling.optimization.combinatorial.GA.settings.OperatorSettings;
import scheduling.optimization.combinatorial.GA.settings.RecombinationSettings;
import scheduling.optimization.combinatorial.GA.settings.SelectionSettings;
import scheduling.optimization.util.RandomUtils;

import java.util.Arrays;
import java.util.Map;

public class OperatorFactory {

    public <R extends Number> Operator<R> createOperator(OperatorSettings settings,
                                          Problem<Integer, R> problem) {
        try {
            switch (settings.getOperatorType()) {
                case RECOMBINATION:
                    return new Recombination<>((RecombinationSettings)settings, problem);
                case SELECTION:
                    return new Selection<>((SelectionSettings)settings, problem);
                case MUTATION:
                    return new Mutation<>((MutationSettings)settings, problem);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Передан неверный тип оператора: " +
                    settings.getOperatorType().toString() +
                    " допустимые значения: " + Arrays.toString(OperatorType.values()));
        }
        return null;
    }

    public <R extends Number> Operator<R> selectOperator(Map<OperatorSettings, Operator<R>> operators) {
        Operator<R> result = null;

        if (operators.size() == 1) {
            result = operators.entrySet().iterator().next().getValue();
        } else {
            double random = RandomUtils.random.nextDouble();
            for (Map.Entry<OperatorSettings, Operator<R>> entry : operators.entrySet()) {
                if (entry.getValue().getDistribution() >= random) {
                    result = entry.getValue();
                    break;
                }
            }
        }
        return result;
    }

}
