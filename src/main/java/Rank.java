package main.java;


public class Rank {

    private int rankID;
    private String rankName;
    private int xp;
    private int plots;

    /**
     * Constructs a Rank object
     * @param rankID rankId of rank
     * @param rankName rankName of rank
     * @param xp xp of rank
     * @param plots number of lots for rank
     */
    public Rank(int rankID, String rankName, int xp, int plots) {
        this.rankID = rankID;
        this.rankName = rankName;
        this.xp = xp;
        this.plots = plots;
    }

    /**
     * Retrieves rankId of rank
     * @return rankId
     */
    public int getRankID() {
        return rankID;
    }

    /**
     * Sets rankId of current rank object
     * @param rankId rank id to be set to
     */
    public void setRankID(int rankID) {
        this.rankID = rankID;
    }

     /**
     * Retrieves rank name of rank object
     * @return rank name
     */
    public String getRankName() {
        return rankName;
    }

     /**
     * Sets rankName of current rank object
     * @param rankName Name to set rankName to
     */
    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

     /**
     * Retrieves xp of rank object
     * @return xp
     */
    public int getXp() {
        return xp;
    }
    
     /**
     * Sets xp of current rank object
     * @param xp xp to change to
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

     /**
     * Retrieves number of plots of current rank object
     * @return plots
     */
    public int getPlots() {
        return plots;
    }

     /**
     * Sets number of plots of current rank object
     * @param plots int to set number of plots to
     */
    public void setPlots(int plots) {
        this.plots = plots;
    }
}