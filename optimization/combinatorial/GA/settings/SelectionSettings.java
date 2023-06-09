package scheduling.optimization.combinatorial.GA.settings;

import org.primefaces.PrimeFaces;
import scheduling.optimization.combinatorial.GA.util.OperatorType;
import scheduling.optimization.combinatorial.GA.util.RankingSelectionType;
import scheduling.optimization.combinatorial.GA.util.SelectionType;

import java.io.Serializable;

public class SelectionSettings implements OperatorSettings, Serializable {

    private OperatorType operatorType = OperatorType.SELECTION;
    private SelectionType selectionType;
    // для турнирной селекции
    private Integer tournamentSize;
    // для ранговой селекции
    private RankingSelectionType rankingSelectionType;
    private Double weight;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectionSettings that = (SelectionSettings) o;

        if (selectionType != that.selectionType) return false;
        if (tournamentSize != null && !tournamentSize.equals(that.tournamentSize)) return false;
        if (rankingSelectionType != that.rankingSelectionType) return false;
        return weight == null || weight.equals(that.weight);
    }

    @Override
    public int hashCode() {
        int result = selectionType.hashCode();
        if (tournamentSize != null) {
            result = 31 * result + tournamentSize.hashCode();
        }
        if (rankingSelectionType != null) {
            result = 31 * result + rankingSelectionType.hashCode();
        }
        if (weight != null) {
            result = 31 * result + weight.hashCode();
        }
        return result;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public Integer getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(Integer tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public RankingSelectionType getRankingSelectionType() {
        return rankingSelectionType;
    }

    public void setRankingSelectionType(RankingSelectionType rankingSelectionType) {
        this.rankingSelectionType = rankingSelectionType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public SelectionSettings() {

    }

    public SelectionSettings withSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
        return this;
    }

    public SelectionSettings tournamentSize(Integer tournamentSize) {
        this.tournamentSize = tournamentSize;
        return this;
    }

    public SelectionSettings rankingSelectionType(RankingSelectionType rankingSelectionType) {
        this.rankingSelectionType = rankingSelectionType;
        return this;
    }

    public SelectionSettings weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public String getParametersString() {
        return selectionType.getDisplayName() + "\t" +
                tournamentSize + "\t" +
                rankingSelectionType.getDisplayName() + "\t" +
                weight + "\t";
    }

}
