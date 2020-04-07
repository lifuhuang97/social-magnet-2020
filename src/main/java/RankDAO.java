package main.java;

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
        return allRanks[currentRankId-1];
    }

    public static Rank getNextRank(int currentRankId){
        return allRanks[currentRankId];
    }

}
