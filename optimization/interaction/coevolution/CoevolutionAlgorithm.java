package scheduling.optimization.interaction.coevolution;

import scheduling.bean.algorithm.util.LoggerWrapper;
import scheduling.optimization.*;
import scheduling.optimization.statistic.Statistic;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class CoevolutionAlgorithm<T, R extends Number> implements Algorithm<T, R> {

    private List<LoggerWrapper> loggers;
    protected LoggerWrapper mainLogger;
    protected LoggerWrapper populationSizeLogger;

    protected String name;

    protected Problem<T, R> problem;
    protected int dimension;

    protected List<ICoevolutionary<T,R>> algorithms;

    protected int individualAmount;
    protected int iterationMax;
    protected CoevolutionAlgorithmParameters parameters;

    protected Map<ICoevolutionary<T,R>, Double> evaluations;
    protected Map<ICoevolutionary<T,R>, List<R>> results;
    protected List<ICoevolutionary<T,R>> bestAlgorithms;
    protected Solution<T, R> totalBest = null;
    protected int socialCardAmount;
    protected int amountOfPenalty;
    protected int individualPerAlgorithmInitial;
    protected int restIndividual;

    public CoevolutionAlgorithm() {

    }

    public CoevolutionAlgorithm<T,R> withIterationMax(int iterationMax) {
        this.iterationMax = iterationMax;
        return this;
    }

    public CoevolutionAlgorithm<T,R> individualAmount(int individualAmount) {
        this.individualAmount = individualAmount;
        return this;
    }

    public CoevolutionAlgorithm<T,R> parameters(CoevolutionAlgorithmParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    public CoevolutionAlgorithm<T,R> algorithms(List<ICoevolutionary<T,R>> algorithms) {
        this.algorithms = algorithms;
        return this;
    }

    public CoevolutionAlgorithm<T,R> loggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;

        return this;
    }

    public CoevolutionAlgorithm<T,R> initLoggers() {
        this.mainLogger = loggers.get(0);
        this.populationSizeLogger = loggers.get(1);
        return this;
    }

    private void writeParams(){
        mainLogger.info("individualPerAlgorithmInitial = " + individualPerAlgorithmInitial);
        mainLogger.info("restIndividual = " + restIndividual);
        mainLogger.info("adaptationInterval = " + parameters.getAdaptationInterval());
        mainLogger.info("socialCardAmount = " + socialCardAmount);
        mainLogger.info("amountOfPenalty = " + amountOfPenalty);
        mainLogger.info("isUseMigration = " + parameters.isUseMigration());
        mainLogger.info("topologyType = " + parameters.getTopologyType());
        mainLogger.info("migrationType = " + parameters.getMigrationType());
    }

    public CoevolutionAlgorithm<T,R> init() {
        mainLogger.info("---------------------------------------------------------------");
        mainLogger.info("CoevolutionAlgorithm");
        populationSizeLogger.info("---------------------------------------------------------------");
        populationSizeLogger.info("Algorithm population size");

        results = new HashMap<>();
        evaluations = new HashMap<>();

        individualPerAlgorithmInitial = (int) Math.floor((double) individualAmount / (double) algorithms.size());
        restIndividual = individualAmount - individualPerAlgorithmInitial * algorithms.size();

        socialCardAmount = parameters.getSocialCard();
        //socialCardAmount = (int) Math.floor((double) individPerAlgorithmInitial / 100.0 * (double) socialCard.getValue());
        //socialCardAmount = Math.max(socialCardAmount, 1);

        amountOfPenalty = (int) Math.floor((double) individualPerAlgorithmInitial * ((double) parameters.getPenalty() / 100.0));
        amountOfPenalty = Math.max(amountOfPenalty, 1);

        int i = 0;
        for (ICoevolutionary<T,R> alg : algorithms) {
            alg.setTotalIterationAmount(iterationMax);
            if (i < restIndividual) {
                alg.setInitialIndividualAmount(individualPerAlgorithmInitial + 1);
            } else {
                alg.setInitialIndividualAmount(individualPerAlgorithmInitial);
            }
            i++;
        }

        writeParams();

        return this;
    }

    public void setProblem(Problem<T, R> problem) {
        this.problem = problem;
        dimension = problem.getDimension();
        mainLogger.info("set problem, dimension = " + dimension);
        for (ICoevolutionary<T,R> alg : algorithms) {
            alg.setProblem(problem.copy());
        }
    }

    public Statistic<T, R> solveProblem(Problem<T, R> problem, int runNumber) {
        setProblem(problem);

        Statistic<T, R> statistic = new Statistic<T, R>(problem);

        for (int run = 0; run < runNumber; run++) {
            mainLogger.info("------- NEW RUN " + run + " -------");
            populationSizeLogger.info("------- NEW RUN " + run + " -------");
            Solution<T, R> current = solveProblem();
            statistic.addSolution(current);
            mainLogger.info(statistic.toString());
        }

        mainLogger.info("----------------------------");
        mainLogger.info("---------- RESULT ----------");
        mainLogger.info(statistic.toString());
        mainLogger.info("----------------------------");

        return statistic;
    }

    public void setLoggers(List<LoggerWrapper> loggers) {
        this.loggers = loggers;
        this.mainLogger = loggers.get(0);
        this.populationSizeLogger = loggers.get(1);
        mainLogger.info("---------------------------------------------------------------");
        populationSizeLogger.info("---------------------------------------------------------------");
        mainLogger.info("CoevolutionAlgorithm");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IAlgorithmParameters getParameters() {
        return parameters;
    }

    public Solution<T, R> solveProblem(Problem<T, R> problem) {
        setProblem(problem);
        return solveProblem();
    }

    protected Solution<T, R> solveProblem() {
        totalBest = null;
        for (ICoevolutionary<T, R> alg : algorithms) {
            alg.initialization();
        }
        writePopulationSize();

        int adaptationIntervalAmount = (int) Math.floor((double) iterationMax / (double) parameters.getAdaptationInterval());

        for (int k = 0; k < adaptationIntervalAmount; k++) {

            /*
            final CountDownLatch latch = new CountDownLatch(algorithms.size());
            for (ICoevolutionary<T, R> alg : algorithms) {
                mainLogger.info("start " + alg.getClass());
                new Thread(() -> {
                    results.put(alg, alg.makeSteps(parameters.adaptationInterval));
                    latch.countDown();
                }).start();
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            */

            for (ICoevolutionary<T, R> alg : algorithms) {
                mainLogger.info("start " + alg.getClass());
                results.put(alg, alg.makeSteps(parameters.adaptationInterval));
            }

            List<Solution<T, R>> metaPopulation = createSameBestPopulationForAllAlgorithms();
            evaluatePopulations();
            resourceReallocation();
            for (ICoevolutionary<T, R> alg : algorithms) {
                mainLogger.debug(alg.getBest().toString());
                if (totalBest == null || problem.compare(alg.getBest(), totalBest) > 0) {
                    totalBest = alg.getBest();
                }
            }
            mainLogger.info("totalBest:");
            mainLogger.info(totalBest.toString());
            if (parameters.isUseMigration) {
                if (parameters.migrationType == IndividualMigrationType.SAME_BEST_START_POPULATION) {
                    setupSameBestPopulationForAllAlgorithms(metaPopulation);
                }
                else {
                    migration();
                }
            }
        }

        return totalBest;
    }

    protected List<Solution<T, R>> createSameBestPopulationForAllAlgorithms(){
        mainLogger.debug("createSameBestPopulationForAllAlgorithms");
        List<Solution<T, R>> metaPopulation = new ArrayList<>();
        for (ICoevolutionary<T, R> alg : algorithms) {
            List<Solution<T, R>> solutions = alg.getPopulation();
            for (Solution<T, R> solution: solutions) {
                mainLogger.debug(solution.toString());
                metaPopulation.add(solution);
            }
        }
        return metaPopulation;
    }

    protected void setupSameBestPopulationForAllAlgorithms(List<Solution<T, R>> metaPopulation){
        // Reverse order so, the best individual is on index 0
        metaPopulation.sort((o1, o2) -> problem.compare(o2, o1));

        for (ICoevolutionary<T, R> alg : algorithms) {
            alg.setPopulation(metaPopulation.subList(0, alg.getPopulationSize()));
        }
    }

    protected void migration() {
        switch (parameters.topologyType) {
            case ALL_WITH_ALL:
                migrationAllWithAll();
                break;
            case BEST_TO_ALL:
                migrationBestToAll();
                break;
        }
    }

    private void migrationBestToAll() {
        List<Solution<T, R>> incomers = new ArrayList<>();
        switch (parameters.migrationType) {
            case BEST_REPLACE_WORST:
            case BEST_REPLACE_RANDOM:
                for (ICoevolutionary<T, R> alg : bestAlgorithms) {
                    incomers.add(alg.getCurrentBest());
                }
                break;
            case RANDOM_REPLACE_WORST:
            case RANDOM_REPLACE_RANDOM:
                for (ICoevolutionary<T, R> alg : bestAlgorithms) {
                    incomers.add(alg.getRandom());
                }
                break;
        }
        switch (parameters.migrationType) {
            case BEST_REPLACE_WORST:
            case RANDOM_REPLACE_WORST:
                for (ICoevolutionary<T, R> alg : algorithms) {
                    if (!bestAlgorithms.contains(alg)) {
                        alg.replaceWorst(incomers);
                    }
                }
                break;
            case BEST_REPLACE_RANDOM:
            case RANDOM_REPLACE_RANDOM:
                for (ICoevolutionary<T, R> alg : algorithms) {
                    if (!bestAlgorithms.contains(alg)) {
                        alg.replaceRandom(incomers);
                    }
                }
                break;
        }
    }

    private void migrationAllWithAll() {
        Map<ICoevolutionary<T, R>, Solution<T, R>> incomers = new HashMap<>();
        switch (parameters.migrationType) {
            case BEST_REPLACE_WORST:
            case BEST_REPLACE_RANDOM:
                for (ICoevolutionary<T, R> alg : algorithms) {
                    incomers.put(alg, alg.getCurrentBest());
                }
                break;
            case RANDOM_REPLACE_WORST:
            case RANDOM_REPLACE_RANDOM:
                for (ICoevolutionary<T, R> alg : algorithms) {
                    incomers.put(alg, alg.getRandom());
                }
                break;
        }

        switch (parameters.migrationType) {
            case BEST_REPLACE_WORST:
            case RANDOM_REPLACE_WORST:
                for (ICoevolutionary<T, R> alg : algorithms) {
                    alg.replaceWorst(getAllExceptSelf(alg, incomers));
                }
                break;
            case RANDOM_REPLACE_RANDOM:
            case BEST_REPLACE_RANDOM:
                for (ICoevolutionary<T, R> alg : algorithms) {
                    alg.replaceRandom(getAllExceptSelf(alg, incomers));
                }
                break;
        }
    }

    private List<Solution<T, R>> getAllExceptSelf(ICoevolutionary<T, R> alg,
                                                              Map<ICoevolutionary<T, R>, Solution<T, R>> incomers) {
        List<Solution<T, R>> currentIncomers = new ArrayList<>();

        for (Map.Entry<ICoevolutionary<T, R>, Solution<T, R>> entry : incomers.entrySet()) {
            if (entry.getKey() != alg) {
                currentIncomers.add(entry.getValue());
            }
        }

        return currentIncomers;
    }

    protected void evaluatePopulations() {
        mainLogger.info("evaluatePopulations");

        for (ICoevolutionary<T, R> alg : algorithms) {
            evaluations.put(alg, 0.0);
        }

        for (int k = 0; k < parameters.adaptationInterval; k++) {
            R best = results.get(algorithms.get(0)).get(k);
            for (ICoevolutionary<T, R> alg : algorithms) {
                if (problem.compare(results.get(alg).get(k), best) > 0) {
                    best = results.get(alg).get(k);
                }
            }

            for (ICoevolutionary<T, R> alg : algorithms) {
                if (results.get(alg).get(k).equals(best)) {
                    double ev = evaluations.get(alg) +
                            (double) (parameters.adaptationInterval - k) / (double) (k + 1);
                    evaluations.replace(alg, ev);
                }
            }

        }

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderName = new StringBuilder();
        for (ICoevolutionary<T, R> alg : algorithms) {
            stringBuilderName.append(alg.getName()).append("\t");
            stringBuilder.append(evaluations.get(alg)).append("\t");
        }
        mainLogger.info(stringBuilderName.toString());
        mainLogger.info(stringBuilder.toString());
    }

    protected void resourceReallocation() {
        mainLogger.info("resourceReallocation");

        double best = 0;
        for (ICoevolutionary<T, R> alg : algorithms) {
            if (best < evaluations.get(alg)) {
                best = evaluations.get(alg);
            }
        }

        bestAlgorithms = new ArrayList<>();
        int additionalIndividuals = 0;
        for (ICoevolutionary<T, R> alg : algorithms) {
            if (evaluations.get(alg) != best) {
                int size = alg.getPopulationSize();
                if (size != socialCardAmount) {
                    if (size - amountOfPenalty < socialCardAmount) {
                        additionalIndividuals += (size - socialCardAmount);
                        alg.resizePopulation(socialCardAmount);
                    } else {
                        additionalIndividuals += amountOfPenalty;
                        alg.resizePopulation(size - amountOfPenalty);
                    }
                }
            } else {
                bestAlgorithms.add(alg);
            }
        }

        int adIndividualPerAlg = (int) Math.floor((double) additionalIndividuals / (double) bestAlgorithms.size());
        int rest = additionalIndividuals - adIndividualPerAlg * bestAlgorithms.size();
        int i = 0;
        for (ICoevolutionary<T, R> alg : bestAlgorithms) {
            if (i < rest) {
                alg.resizePopulation(alg.getPopulationSize() + adIndividualPerAlg + 1);
            } else {
                alg.resizePopulation(alg.getPopulationSize() + adIndividualPerAlg);
            }
            i++;
        }

        writePopulationSize();
    }

    private void writePopulationSize() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderNames = new StringBuilder();

        for (ICoevolutionary<T, R> alg : algorithms) {
            stringBuilderNames.append(alg.getName());
            stringBuilderNames.append("\t");
            stringBuilder.append(alg.getPopulationSize());
            stringBuilder.append("\t");
        }
        populationSizeLogger.info(stringBuilder.toString());
        mainLogger.info(stringBuilderNames.toString());
        mainLogger.info(stringBuilder.toString());
    }
}