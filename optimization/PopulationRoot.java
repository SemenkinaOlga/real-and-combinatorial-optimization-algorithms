package scheduling.optimization;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class PopulationRoot<Data, Value extends Number, Prob extends Problem<Data, Value>,
        Ind extends IndividualRoot<Data, Value, Prob>>{

    protected LoggerWrapper logger;

    protected List<Ind> generation;
    protected Ind currentBestIndividual;
    protected Integer individualsAmount;

    protected Prob problem;

    public PopulationRoot(Prob problem, int individualsAmount, LoggerWrapper logger) {
        this.logger = logger;
        this.problem = problem;
        this.individualsAmount = individualsAmount;
        generation = new ArrayList<>();
    }

    public Ind getIndividual(int i){
        return generation.get(i);
    }

    public void calculateObjectiveFunction() {
        logger.debug("Подсчёт значения функции пригодности в популяции. Начало");
        for (Ind individual : generation) {
            individual.calculateObjectiveFunction(problem);
            logger.debug(individual.toString());
        }
        logger.debug("Подсчёт значения функции пригодности в популяции. Окончание");
    }

    public Ind findBest() {
        Ind individual = generation.get(0);

        for (int i = 1; i < generation.size(); i++) {
            Ind current = generation.get(i);
            if (current.compareTo(individual) > 0) {
                individual = current;
            }
        }
        currentBestIndividual = individual;
        return individual;
    }

    public Ind getBestIndividual() {
        return currentBestIndividual;
    }

    public Ind getRandomIndividual() {
        return generation.get(RandomUtils.random.nextInt(generation.size()));
    }

    public int getIndividualsAmount() {
        return this.individualsAmount;
    }

    public void setIndividualsAmount(Integer individualsAmount) {
        this.individualsAmount = individualsAmount;
    }

    public void resizePopulation(int size){
        logger.debug("resizePopulation " + size);
        sort();
        if (size > generation.size()) {
            int generationSize = generation.size();
            for (int i = 0; i < size - generationSize; i++){
                addToGenerationNewIndividual();
            }
        } else {
            generation.subList(size, generation.size()).clear();
        }
        individualsAmount = generation.size();
    }

    public void replaceRandom(List<Solution<Data, Value>> solutions){
        List<Integer> indexes = RandomUtils.getRandomIndexes(solutions.size(), generation.size());
        List<Ind> toRemove = new ArrayList<>();
        for (int index: indexes) {
            toRemove.add(generation.get(index));
        }
        generation.removeAll(toRemove);

        for (Solution<Data, Value> solution: solutions) {
            addToGenerationNewIndividual(solution);
        }
    }

    public void sort() {
        // Reverse order so, best individual is on index 0
        generation.sort((o1, o2) -> o2.compareTo(o1));
    }

    public void replaceWorst(List<Solution<Data, Value>> solutions){
        sort();
        generation.subList(generation.size() - solutions.size(), generation.size()).clear();
        for (Solution<Data, Value> solution: solutions) {
            addToGenerationNewIndividual(solution);
        }
    }

    public List<Solution<Data, Value>> getPopulation(){
        List<Solution<Data, Value>> solutions = new ArrayList<>();
        for (Ind ind: generation) {
            solutions.add(ind.getSolution());
        }
        return solutions;
    }

    public void setPopulation(List<Solution<Data, Value>> solutions){
        generation = new ArrayList<>();
        for (Solution<Data, Value> solution: solutions) {
            addToGenerationNewIndividual(solution);
        }
    }

    public abstract void addToGenerationNewIndividual(Solution<Data, Value> solution);

    public abstract void addToGenerationNewIndividual();

    public int getSize(){
        return generation.size();
    }

    public int chooseRandomIndex(){
        return RandomUtils.random.nextInt(generation.size());
    }

    public Ind chooseRandom(){
        return generation.get(chooseRandomIndex());
    }
}
