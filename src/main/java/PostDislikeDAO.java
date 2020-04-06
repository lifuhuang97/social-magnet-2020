package main.java;
import java.util.*;

public class PostDislikeDAO {

    public static ArrayList<Integer> getUserIdByPostId(int postId) {
        ArrayList<Integer> userIds = new ArrayList<>();

        String stmt = "SELECT USERID FROM POST_DISLIKE WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        for (ArrayList<String> result : results) {
            userIds.add(Integer.parseInt(result.get(0)));
        }

        return userIds;
    }

    public static void undislikePostByPostIdAndUserId(int postId, int userId) {
        String stmt = "DELETE FROM POST_DISLIKE WHERE POSTID = '" + postId + "' AND USERID = '" + userId + "';";
        DataUtility.queryUpdate(stmt);
    }

    public static void dislikePostByPostIdAndUserId(int postId, int userId) {
        String stmt = "INSERT INTO POST_DISLIKE VALUES(" + postId +", " + userId +");";
        DataUtility.queryUpdate(stmt);
    }

    public static boolean hasDisliked(int postId, int userId) {
        String stmt = "SELECT USERID FROM POST_DISLIKE WHERE POSTID = '" + postId + "' AND USERID = '" + userId + "';";
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        return (results.size() == 0) ? false : true;
    }

    public static void deletePostDislike(int postId) {
        String stmt = "DELETE FROM POST_DISLIKE WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }
}