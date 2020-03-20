import java.util.ArrayList;

public class MyWallCtrl {

    private UserProfileDAO userDAO;
    private UserProfile user;

    public MyWallCtrl(UserProfile user){
        userDAO = new UserProfileDAO();
        this.user = user;
    }

    public void displayPersonalInfo(int id){

        String username = user.getUsername();
        String fullname = user.getFullName();
        Rank rank = user.getRank();
        String rankDesc = rank.getRankName();

        System.out.println("About " + username);
        System.out.println("Full Name: " + fullname);

        String wealthRank = UserProfileDAO.wealthRankPrint(user);
        System.out.print(rankDesc + " Farmer, ");
        System.out.print(wealthRank + " richest");
        System.out.println();
    }

    public ArrayList<Post> getPosts(int id){

        ArrayList<Post> results = new ArrayList<Post>();



        return results;

    }

}