package main.java;

import java.util.*;

public class PostCommentDAO {
    public static ArrayList<Integer> getCommentIdsByPostId(int postId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT COMMENTID FROM POST_COMMENT WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return ids;
        } else {
            for (ArrayList<String> result : results) {
                ids.add(Integer.valueOf(result.get(0)));
            }
        }

        return ids;
    }

    public static void addPostComment(int postId, int commentId) {
        String stmt = "INSERT INTO POST_COMMENT (`postID`, `commentID`) VALUES ('" + postId + "', '" + commentId + "');";

        DataUtility.queryUpdate(stmt);
    }

    public static void deletePostComment(int postId) {
        String stmt = "DELETE FROM POST_COMMENT WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }
}