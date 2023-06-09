package scheduling.optimization.real.DE.standard;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.PopulationBase;
import scheduling.optimization.real.ProblemReal;

import java.util.ArrayList;
import java.util.List;

public class Population<R extends Number> extends PopulationBase<R, Individual<R>> {

    public Population(ProblemReal<Double, R> problem, int individualsAmount, LoggerWrapper logger) {
        super(problem, individualsAmount, logger);

        logger.debug("Ининициализация популяции");
        for (int i = 0; i < individualsAmount; i++) {
            generation.add(new Individual<>(problem, logger));
        }
        calculateObjectiveFunction();
        findBest();
        logger.debug("Ининициализация популяции. Окончание");
    }

    @Override
    public void addToGenerationNewIndividual(Solution<Double, R> solution) {
        generation.add(new Individual<>(problem, solution, logger));
    }

    @Override
    public void addToGenerationNewIndividual() {
        Individual<R> individual = new Individual<>(problem, logger);
        individual.calculateObjectiveFunction(problem);
        generation.add(individual);
    }
}
