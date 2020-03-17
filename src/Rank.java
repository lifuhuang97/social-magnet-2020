
public class Rank {

    private int rankID;
    private String rankName;
    private int xp;
    private int plots;

    public Rank(int rankID, String rankName, int xp, int plots) {
        this.rankID = rankID;
        this.rankName = rankName;
        this.xp = xp;
        this.plots = plots;
    }

    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getPlots() {
        return plots;
    }

    public void setPlots(int plots) {
        this.plots = plots;
    }
}