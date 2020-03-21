import java.util.*;

public class UserCommentDAO {
    public static int getUserIdByCommentId(int commentId) {
        String stmt = "SELECT USERID FROM USER_COMMENT WHERE COMMENTID = '" + commentId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);
        results.remove(0);
        return Integer.parseInt(results.get(0).get(0));
    }
}