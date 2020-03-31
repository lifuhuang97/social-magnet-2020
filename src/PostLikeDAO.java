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
}