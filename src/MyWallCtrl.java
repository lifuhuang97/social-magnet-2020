import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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

    public ArrayList<Post> getTopFivePosts(int id){

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Post> relevantPosts = new ArrayList<Post>();

        String getAllOfMyPosts = "SELECT postID FROM user_post where userID = " + id;
        String getAllOfTagPosts = "SELECT postID FROM post_tags where tagID = " + id;

        ArrayList<ArrayList<String>> allMyPosts = DataUtility.querySelect(getAllOfMyPosts);
        ArrayList<ArrayList<String>> allTaggedPosts = DataUtility.querySelect(getAllOfTagPosts);

        // to merge all posts retrieved
        for(ArrayList<String> taggedPost : allTaggedPosts){
            allMyPosts.add(taggedPost);
        }

        Map<Integer, SMDate> post_with_date = new HashMap<Integer,SMDate>();

        

        return relevantPosts;

    }

}