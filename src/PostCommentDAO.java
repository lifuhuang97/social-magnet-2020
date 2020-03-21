import java.util.*;

public class PostCommentDAO {
    public static ArrayList<Integer> getCommentIdsByPostId(int postId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT COMMENTID FROM POST_COMMENT WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);

        if (results.size() == 1) {
            return ids;
        } else {
            results.remove(0);
            for (ArrayList<String> result : results) {
                ids.add(Integer.valueOf(result.get(0)));
            }
        }

        return ids;
    }
}