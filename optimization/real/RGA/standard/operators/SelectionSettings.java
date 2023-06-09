package scheduling.optimization.real.RGA.standard.operators;

import scheduling.optimization.real.RGA.operators.RankingSelectionType;
import scheduling.optimization.real.RGA.operators.SelectionType;

public class SelectionSettings {
    private SelectionType selectionType;
    private int tournamentSize;
    private RankingSelectionType rankingSelectionType;
    private double weight;

    public SelectionSettings(SelectionType selectionType, int tournamentSize,
                             RankingSelectionType rankingSelectionType, double weight){
        this.selectionType = selectionType;
        this.tournamentSize = tournamentSize;
        this.rankingSelectionType = rankingSelectionType;
        this.weight = weight;
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (selectionType != null) {
            result = 31 * result + selectionType.hashCode();
        }
        result = 31 * result + tournamentSize;
        if (rankingSelectionType != null) {
            result = 31 * result + rankingSelectionType.hashCode();
        }
        result = 31 * result + Double.valueOf(weight).hashCode();;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SelectionSettings other = (SelectionSettings) obj;

        if (selectionType == null) {
            if (other.selectionType != null)
                return false;
        } else if (!selectionType.equals(other.selectionType))
            return false;

        else if (tournamentSize != other.tournamentSize)
            return false;

        if (rankingSelectionType == null) {
            if (other.rankingSelectionType != null)
                return false;
        } else if (!rankingSelectionType.equals(other.rankingSelectionType))
            return false;

        else if (weight != other.weight)
            return false;

        return true;
    }

    public SelectionType getSelectionType(){ return selectionType; }

    public int getTournamentSize(){ return tournamentSize; }

    public RankingSelectionType getRankingSelectionType(){ return rankingSelectionType; }

    public double getWeight(){ return weight; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип селекции: ");
        switch (selectionType) {
            case PROPORTIONAL:
                sb.append("пропорциональная");
                break;
            case RANKING:
                sb.append("ранговая, ");
                switch (rankingSelectionType) {
                    case LINEAR:
                        sb.append("линейная");
                        break;
                    case EXPONENTIAL:
                        sb.append("экспоненциальная");
                        sb.append(". Вес: ").append(weight);
                        break;
                }
                break;
            case TOURNAMENT:
                sb.append("турнирная");
                sb.append(". Размер турнира: ").append(tournamentSize);
                break;
        }
        return sb.toString();
    }

    public String getParametersString() {
        return selectionType.getDisplayName() + "\t" +
                tournamentSize + "\t" +
                rankingSelectionType.getDisplayName() + "\t" +
                weight + "\t";
    }
}
