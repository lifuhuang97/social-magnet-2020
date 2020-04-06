package main.java;

import java.util.*;

public class PostLikeDAO {

    /**
     * Retrieve users who like the post
     * @param postId postId used to identify entry in database 
     * @return ArrayList containing userIds
     */
    public static ArrayList<Integer> getUserIdByPostId(int postId) {
        ArrayList<Integer> userIds = new ArrayList<>();

        String stmt = "SELECT USERID FROM POST_LIKE WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        for (ArrayList<String> result : results) {
            userIds.add(Integer.parseInt(result.get(0)));
        }

        return userIds;
    }

    /**
     * User unlikes post
     * @param postId postId used to identify entry in database 
     * @param userId userId used to identify entry in database 
     */
    public static void unlikePostByPostIdAndUserId(int postId, int userId) {
        String stmt = "DELETE FROM POST_LIKE WHERE POSTID = '" + postId + "' AND USERID = '" + userId + "';";
        DataUtility.queryUpdate(stmt);
    }

    /**
     * User likes post
     * @param postId postId used to add entry into database 
     * @param userId userId used to add entry into database 
     */
    public static void likePostByPostIdAndUserId(int postId, int userId) {
        String stmt = "INSERT INTO POST_LIKE VALUES(" + postId +", " + userId +");";
        DataUtility.queryUpdate(stmt);
    }

    /**
     * check if user liked post
     * @param postId postId used to identify entry into database 
     * @param userId userId used to identify entry into database 
     */
    public static boolean hasLiked(int postId, int userId) {
        String stmt = "SELECT USERID FROM POST_LIKE WHERE POSTID = '" + postId + "' AND USERID = '" + userId + "';";
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        return (results.size() == 0) ? false : true;
    }

     /**
     * delete post in database
     * @param postId postId used to identify entry into database 
     */
    public static void deletePostLike(int postId) {
        String stmt = "DELETE FROM POST_LIKE WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }
}