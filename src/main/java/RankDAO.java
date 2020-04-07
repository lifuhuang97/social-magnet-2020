// package main.java;
import java.util.*;

public class RankDAO {

    /**
     * Retrieve rank object according to rankId
     * @param id id of rank
     * @returns Rank object of rank
     */
    public static Rank getMyRank(int currentRankId){

        String query = "SELECT * FROM RANK WHERE RANKID = " + currentRankId;

        ArrayList<String> rankInfo = DataUtility.singleQuerySelect(query);

        int rankID = Integer.parseInt(rankInfo.get(0));
        String rankName = rankInfo.get(1);
        int xp = Integer.parseInt(rankInfo.get(2));
        int plots = Integer.parseInt(rankInfo.get(3));

        Rank rank = new Rank(rankID, rankName, xp, plots);

        return rank;
    }

    public static Rank getNextRank(int currentRankId){

        int nextRankId = currentRankId + 1;

        String query = "SELECT * FROM RANK WHERE RANKID = " + nextRankId;

        ArrayList<String> rankInfo = DataUtility.singleQuerySelect(query);

        int rankID = Integer.parseInt(rankInfo.get(0));
        String rankName = rankInfo.get(1);
        int xp = Integer.parseInt(rankInfo.get(2));
        int plots = Integer.parseInt(rankInfo.get(3));

        Rank rank = new Rank(rankID, rankName, xp, plots);

        return rank;
        
    }

}
