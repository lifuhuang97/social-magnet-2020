
import java.util.*;

public class PostDAO {
    // Returns all the Post objects made by an array of users 
    public static  ArrayList<Post> retrievePostsByUserIds (ArrayList<Integer> ids) {

        ArrayList<Post> posts = new ArrayList<>();

        for (Integer id : ids) {
            int int_id = id.intValue();
            ArrayList<Post> postsById = retrievePostsByPostId(int_id);

            for (Post postById : postsById) {
                posts.add(postById);
            }

        }
        
        return posts;
    }

    public static ArrayList<Post> retrievePostsByPostId(int id) {
        ArrayList<Post> posts = new ArrayList<>();

        String stmt = "SELECT * FROM POST WHERE POSTID = '" + id + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return posts;
        } else {
            for (ArrayList<String> result : results) {
                int postId = Integer.parseInt(result.get(0));
                String content = result.get(1);
                SMDate datetime = new SMDate(result.get(2));

                posts.add(new Post(postId, content, datetime));
            }
        }

        return posts;
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