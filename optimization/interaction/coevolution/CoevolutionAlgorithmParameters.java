package scheduling.optimization.interaction.coevolution;

import scheduling.optimization.IAlgorithmParameters;

public class CoevolutionAlgorithmParameters implements IAlgorithmParameters {

    protected int adaptationInterval;
    protected int socialCard;
    protected int penalty;
    protected boolean isUseMigration;
    protected TopologyTypeEnum topologyType;
    protected IndividualMigrationType migrationType;

    public CoevolutionAlgorithmParameters(int adaptationInterval, int socialCard, int penalty, boolean isUseMigration,
                                          TopologyTypeEnum topologyType, IndividualMigrationType migrationType){
        this.adaptationInterval = adaptationInterval;
        this.socialCard = socialCard;
        this.penalty = penalty;
        this.isUseMigration = isUseMigration;
        this.topologyType = topologyType;
        this.migrationType = migrationType;
    }

    public void setUseMigration(boolean useMigration) {
        isUseMigration = useMigration;
    }

    public void setTopologyType(TopologyTypeEnum topologyType) {
        this.topologyType = topologyType;
    }

    public void setMigrationType(IndividualMigrationType migrationType) {
        this.migrationType = migrationType;
    }

    public boolean isUseMigration() {
        return isUseMigration;
    }

    public TopologyTypeEnum getTopologyType() {
        return topologyType;
    }

    public IndividualMigrationType getMigrationType() {
        return migrationType;
    }

    public int getAdaptationInterval() {
        return adaptationInterval;
    }

    public void setAdaptationInterval(int adaptationInterval) {
        this.adaptationInterval = adaptationInterval;
    }

    public int getSocialCard() { return socialCard; }

    public void setSocialCard(int socialCard) { this.socialCard = socialCard; }

    public int getPenalty() { return penalty; }

    public void setPenalty(int socialCard) { this.penalty = penalty; }

    public CoevolutionAlgorithmParameters() {
        adaptationInterval = 10;
        socialCard = 1;
        penalty = 1;
        isUseMigration = false;
        topologyType = TopologyTypeEnum.BEST_TO_ALL;
        migrationType = IndividualMigrationType.BEST_REPLACE_RANDOM;
    }

    @Override
    public String toString() {
        return "параметры {" +
                "adaptationInterval: " + adaptationInterval +
                ", socialCard: " + socialCard +
                ", penalty: " + penalty +
                ", topologyType: " + topologyType.getDisplayName() +
                ", migrationType: " + migrationType.getDisplayName() +
                '}';
    }

    @Override
    public String getParametersString() {
        return adaptationInterval + "\t" +
                socialCard + "\t" +
                penalty + "\t" +
                topologyType.getDisplayName() + "\t" +
                migrationType.getDisplayName() + "\t";
    }
}
