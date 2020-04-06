import java.util.ArrayList;

public class PostTagDAO {
    /**
     * retrieve postsIds from tagId
     * @param TagId tag id
     * @return ArrayList of integers representing postIds
     */
    public static ArrayList<Integer> getPostIdsByTagId(int TagId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT POSTID FROM POST_TAGS WHERE TAGID = '" + TagId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return ids;
        } else {
            for (ArrayList<String> result : results) {
                ids.add(Integer.parseInt(result.get(0)));
            }
        }

        return ids;
    }

    /**
     * retrieve tagIds tagged to postIds
     * @param postId post Id to retrieve tags from
     * @return ArrayList of integers representing tagIds
     */
    public static ArrayList<Integer> getTagIdsByPostId(int postId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT TAGID FROM POST_TAGS WHERE POSTID = '" + postId +"';";
        
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        
        if (results.size() == 0) {
            return ids;
        } else {
            for (ArrayList<String> result : results) {
                ids.add(Integer.parseInt(result.get(0)));
            }
        }

        return ids;
    }

    /**
     * delete post in database according to postId
     * @param postId postId used to identify entry in database 
     */
    public static void deletePostTag(int postId) {
        String stmt = "DELETE FROM POST_TAGS WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }

     /**
     * delete post in database according to postId and tagId
     * @param postId postId used to identify entry in database 
     * @param tagId tagId used to identify entry in database 
     */
    public static void deletePostTagByTagId(int postId, int tagId) {
        String stmt = "DELETE FROM POST_TAGS WHERE POSTID = '" + postId + "' AND TAGID ='" + tagId + "';";

        DataUtility.queryUpdate(stmt);
    }

    /**
     * Add post and tag into database
     * @param postId postId to be added
     * @param tagId tagId to be added
     */
    public static void addPostTag(int postId, int tagId) {
        String stmt = "INSERT INTO POST_TAGS (`postID`, `tagID`) VALUES ('" + postId + "', '" + tagId + "');";

        DataUtility.queryUpdate(stmt);
    }
}