package scheduling.optimization.interaction.coevolution;

public interface IIICoevolutionary {
    void makeStep();
    void resizePopulation(int size);
    void initialization();
    int getPopulationSize();
    void setTotalIterationAmount(int iterationAmount);
    void setInitialIndividualAmount(int individualAmount);
    String getName();
    void setName(String name);
}
