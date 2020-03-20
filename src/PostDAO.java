
import java.util.HashMap;

public class PostDAO {

    DataUtility d = new DataUtility();

    public PostDAO() {
    }

    //Returns latest 3 postsID on his/friends wall
    public int[] retrievePosts (int UserID) {
        //SELECT
        return new int[]{};
    }

    public HashMap<String, Integer> retrieveLikes (int postID) {
        //SELECT
        return new HashMap<String, Integer>();
    }

    //Returns String array containing top 3 tagged posts
    public static String[] retrieveWall(int UserID) {
        //SELECT
        return new String[]{};

    }

    public String[] retrieveComments(int postID) {
        //retrieve comment from commentID table and post_comment table
        return new String[]{};
    }

}