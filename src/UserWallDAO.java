import java.util.ArrayList;

public class UserWallDAO {
    public static ArrayList<Integer> getPostIdsByUserId(int userId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT POSTID FROM USER_WALL WHERE USERID = '" + userId +"';";

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

    public static int getUserIdByPostId(int postId) {
        String stmt = "SELECT USERID FROM USER_WALL WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        return Integer.parseInt(results.get(0).get(0));
    }

    public static void deleteUserWallPost(int postId) {
        String stmt = "DELETE FROM USER_WALL WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }

    public static void addUserWallPost(int userId, int postId) {
        String stmt = "INSERT INTO USER_WALL VALUES ('"+ userId +"', '" + postId + "');";

        DataUtility.queryUpdate(stmt);
    }


}