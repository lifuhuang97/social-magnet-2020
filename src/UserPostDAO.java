import java.util.*;

public class UserPostDAO {
    public static ArrayList<Integer> getPostIdsByUserId(int userId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT POSTID FROM USER_POST WHERE USERID = '" + userId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);

        if (results.size() == 1) {
            return ids;
        } else {
            results.remove(0);
            for (ArrayList<String> result : results) {
                ids.add(Integer.parseInt(result.get(0)));
            }
        }

        return ids;
    }

    public static int getUserIdByPostId(int postId) {
        String stmt = "SELECT USERID FROM USER_POST WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);
        results.remove(0);
        return Integer.parseInt(results.get(0).get(0));
    }
}