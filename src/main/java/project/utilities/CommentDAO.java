// package main.java;

import java.util.*;

public class CommentDAO {

    /**
     * Retrieves the Comment object using the CommentID
     * @param commentId commentId of comment
     * @return Comment object with the CommentID
     */
    public static Comment retrieveCommentById(int commentId) {

        String stmt = "SELECT * FROM COMMENT WHERE COMMENTID = '" + commentId + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        String content = results.get(0).get(1);
        SMDate datetime = new SMDate(results.get(0).get(2));

        return new Comment(commentId, content, datetime);
    }

    /**
     * Add a commment 
     * @param content content of comment
     * @param datetime current datetime 
     * @return newly created Comment object CommentID
     */
    public static int addComment(String content, String datetime) {
        String stmt = "INSERT INTO COMMENT (`content`, `datetime`) VALUES ('" + content + "', '" + datetime + "')";

        int commentId = DataUtility.queryUpdateRetrieveID(stmt);

        return commentId;
    }

    /**
     * Removes a comment with the specified CommentID
     * @param commentId commentId of comment
     */
    public static void deleteComment(int commentId) {
        String stmt = "DELETE FROM COMMENT WHERE COMMENTID = '" + commentId + "';";

        DataUtility.queryUpdate(stmt);
    }
}