// package main.java;

import java.util.*;

public class UserPostDAO {
    /**
     * Retrieves Posts made by user
     * @param userId userId of user
     * @return ArrayList of posts made by user
     */
    public static ArrayList<Integer> getPostIdsByUserId(int userId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT POSTID FROM USER_POST WHERE USERID = '" + userId +"';";

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
     * Retrieves user who made post
     * @param postId postId of post
     * @return userId of user who made post
     */
    public static int getUserIdByPostId(int postId) {
        String stmt = "SELECT USERID FROM USER_POST WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        return Integer.parseInt(results.get(0).get(0));
    }

    /**
     * Delete post
     * @param postId postId of post to be deleted
     */
    public static void deleteUserPost(int postId) {
        String stmt = "DELETE FROM USER_POST WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }

    /**
     * Register user to post in databse
     * @param postId postId of post
     * @param userId userId of uer
     */
    public static void addUserPost(int userId, int postId) {
        String stmt = "INSERT INTO USER_POST VALUES ('"+ userId +"', '" + postId + "');";

        DataUtility.queryUpdate(stmt);
    }
}