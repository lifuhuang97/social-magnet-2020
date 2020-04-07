// package main.java;
import java.util.*;

public class RankDAO {

    private static Rank[] allRanks = {
        new Rank(1, "Novice", 0, 5),
        new Rank(2, "Apprentice", 1000, 6),
        new Rank(3, "Journeyman", 2500, 7),
        new Rank(4, "Grandmaster", 5000, 8),
        new Rank(5, "Legendary", 12000, 9)
    };

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
