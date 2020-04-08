package project.utilities;

import java.util.*;

public class UserCommentDAO {
    /**
     * Retrieves user who made the comment
     * @param commentId commentId of comment
     * @return userId of user who made the comment
     */
    public static int getUserIdByCommentId(int commentId) {
        String stmt = "SELECT USERID FROM USER_COMMENT WHERE COMMENTID = '" + commentId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        return Integer.parseInt(results.get(0).get(0));
    }

    /**
     * Tag user to comment in database
     * @param commentId commentId of comment
     * @param userId userId of user
     */
    public static void addUserComment(int userId, int commentId) {
        String stmt = "INSERT INTO USER_COMMENT (`userID`, `commentID`) VALUES ('" + userId + "', '" + commentId + "');";

        DataUtility.queryUpdate(stmt);
    }

    /**
     * Deletes comment and corresponding user tagged to it in database
     * @param commentId commentId of comment
     */
    public static void deleteUserComment(int commentId) {
        String stmt = "DELETE FROM USER_COMMENT WHERE COMMENTID = '" + commentId + "';";

        DataUtility.queryUpdate(stmt);
    }
}