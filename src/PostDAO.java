import java.util.HashMap;

import main.java.com.company.DataUtility;

public class PostDAO {

    DataUtility d = new DataUtility();

    public PostDAO() {
    }

    //Returns latest 3 postsID on his/friends wall
    public Integer[] retrievePosts (int UserID) {
        //SELECT
    }

    public HashMap<String, Integer> retrieveLikes (int postID) {
        //SELECT
    }

    //Returns String array containing top 3 tagged posts
    public String[] retrieveWall(int UserID) {
        //SELECT

    }

    public String[] retrieveComments(int postID) {
        //retrieve commenet from commentID table and post_comment table
    }

}