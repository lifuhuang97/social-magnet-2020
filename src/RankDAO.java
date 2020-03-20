import java.util.ArrayList;

public class RankDAO {

    private ArrayList<Rank> allRanks = new ArrayList<Rank>();

    public RankDAO(){

        this.allRanks = new ArrayList<Rank>();

        allRanks.add(new Rank(1, "Novice", 0, 5));
        allRanks.add(new Rank(2, "Apprentice", 1000, 6));
        allRanks.add(new Rank(3, "Journeyman", 2500, 7));
        allRanks.add(new Rank(4, "Grandmaster", 5000, 8));
        allRanks.add(new Rank(5, "Legendary", 12000, 9));
    }

    public Rank getMyRank(int id){
        return allRanks.get(id-1);
    }

}
