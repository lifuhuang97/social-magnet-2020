import java.util.*;

public class PostLikeDAO {

    public static ArrayList<Integer> getUserIdByPostId(int postId) {
        ArrayList<Integer> userIds = new ArrayList<>();

        String stmt = "SELECT USERID FROM POST_LIKE WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        for (ArrayList<String> result : results) {
            userIds.add(Integer.parseInt(result.get(0)));
        }

        return userIds;
    }

    public static void unlikePostByPostIdAndUserId(int postId, int userId) {
        String stmt = "DELETE FROM POST_LIKE WHERE POSTID = '" + postId + "' AND USERID = '" + userId + "';";
        DataUtility.queryUpdate(stmt);
    }

    public static void likePostByPostIdAndUserId(int postId, int userId) {
        String stmt = "INSERT INTO POST_LIKE VALUES(" + postId +", " + userId +");";
        DataUtility.queryUpdate(stmt);
    }

    public static boolean hasLiked(int postId, int userId) {
        String stmt = "SELECT USERID FROM POST_LIKE WHERE POSTID = '" + postId + "' AND USERID = '" + userId + "';";
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        return (results.size() == 0) ? false : true;
    }

    public static void deletePostLike(int postId) {
        String stmt = "DELETE FROM POST_LIKE WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }
}