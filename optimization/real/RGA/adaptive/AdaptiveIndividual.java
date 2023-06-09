package scheduling.optimization.real.RGA.adaptive;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.ProblemReal;
import scheduling.optimization.real.RGA.IndividualBase;
import scheduling.optimization.real.RGA.operators.MutationType;
import scheduling.optimization.real.RGA.operators.RankingSelectionType;
import scheduling.optimization.real.RGA.operators.RecombinationType;
import scheduling.optimization.real.RGA.operators.SelectionType;
import scheduling.optimization.real.RGA.standard.Individual;
import scheduling.optimization.real.RGA.standard.operators.MutationSettings;
import scheduling.optimization.real.RGA.standard.operators.RecombinationSettings;
import scheduling.optimization.real.RGA.standard.operators.SelectionSettings;

import java.util.List;

public class AdaptiveIndividual<R extends Number> extends IndividualBase<R> {

    public SelectionSettings selectionSettings;
    public RecombinationSettings recombinationSettings;
    public MutationSettings mutationSettings;

    public AdaptiveIndividual(ProblemReal<Double, R> problem, int bitNumberPerVariable, LoggerWrapper logger) {
        super(problem, bitNumberPerVariable, logger);
        selectionSettings = new SelectionSettings(SelectionType.TOURNAMENT, 2, RankingSelectionType.LINEAR, 1);
        recombinationSettings = new RecombinationSettings(RecombinationType.UNIFORM);
        mutationSettings = new MutationSettings(MutationType.AVERAGE);
    }

    public AdaptiveIndividual(ProblemReal<Double, R> problem, int bitNumberPerVariable, List<Integer> genotype, LoggerWrapper logger) {
        super(problem, bitNumberPerVariable, genotype, logger);
    }

    public AdaptiveIndividual(Individual<R> individual) {
        super(individual);
    }

    public AdaptiveIndividual(ProblemReal<Double, R> problem, int bitNumberPerVariable, Solution<Double, R> solution, LoggerWrapper logger) {
        super(problem, bitNumberPerVariable, solution, logger);
    }
}
