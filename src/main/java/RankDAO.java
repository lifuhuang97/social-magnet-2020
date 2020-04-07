package main.java;

import java.util.ArrayList;

public class RankDAO {

    private static ArrayList<Rank> allRanks = new ArrayList<Rank>();

    /**
     * Constructs rankDAO object
     */
    public RankDAO(){

        allRanks = new ArrayList<Rank>();

        allRanks.add(new Rank(1, "Novice", 0, 5));
        allRanks.add(new Rank(2, "Apprentice", 1000, 6));
        allRanks.add(new Rank(3, "Journeyman", 2500, 7));
        allRanks.add(new Rank(4, "Grandmaster", 5000, 8));
        allRanks.add(new Rank(5, "Legendary", 12000, 9));
    }

    /**
     * Retrieve rank object according to rankId
     * @param id id of rank
     * @returns Rank object of rank
     */
    public static Rank getMyRank(int id){
        return allRanks.get(id-1);
    }

    public static Rank getNextRank(int id){
        return allRanks.get(id);
    }

}
