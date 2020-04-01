import java.util.*;

public class UserCommentDAO {
    public static int getUserIdByCommentId(int commentId) {
        String stmt = "SELECT USERID FROM USER_COMMENT WHERE COMMENTID = '" + commentId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        return Integer.parseInt(results.get(0).get(0));
    }

    public static void addUserComment(int userId, int commentId) {
        String stmt = "INSERT INTO USER_COMMENT (`userID`, `commentID`) VALUES ('" + userId + "', '" + commentId + "');";

        DataUtility.queryUpdate(stmt);
    }

    public static void deleteUserComment(int commentId) {
        String stmt = "DELETE FROM USER_COMMENT WHERE COMMENTID = '" + commentId + "';";

        DataUtility.queryUpdate(stmt);
    }
}