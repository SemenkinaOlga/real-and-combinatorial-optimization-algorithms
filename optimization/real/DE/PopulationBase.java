package scheduling.optimization.real.DE;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.PopulationRoot;
import scheduling.optimization.Solution;
import scheduling.optimization.real.DE.standard.Individual;
import scheduling.optimization.real.ProblemReal;

import java.util.ArrayList;
import java.util.List;

public abstract class PopulationBase<R extends Number, Ind extends IndividualBase<R>>
        extends PopulationRoot<Double, R, ProblemReal<Double, R>, Ind> {

    protected List<Ind> newGeneration;

    public PopulationBase(ProblemReal<Double, R> problem, int individualsAmount, LoggerWrapper logger) {
        super(problem, individualsAmount, logger);

        newGeneration = new ArrayList<>();
    }

    public void setupPopulation() {
        logger.debug("setupPopulation");
        if (newGeneration != null) {
            generation = newGeneration;
            newGeneration =  new ArrayList<>();
        }
        else {
            logger.error("newGeneration is empty for DE");
        }
    }

    public void addIndividualToNewGeneration(Ind individual){
        newGeneration.add(individual);
    }

    public void calculateObjectiveFunction() {
        logger.debug("Подсчёт значения функции пригодности в популяции. Начало");
        for (Ind individual : generation) {
            individual.calculateObjectiveFunction(problem);
            logger.debug(individual.toString());
        }
        logger.debug("Подсчёт значения функции пригодности в популяции. Окончание");
    }

}
