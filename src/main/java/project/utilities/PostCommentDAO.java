package project.utilities;

import java.util.*;

public class PostCommentDAO {

    /**
     * Retrieve commentIds associated with postId
     * @param postId postId used to identify entry in database 
     * @return ArrayList containing commentIds
     */
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

    /**
     * Add a post-comment association
     * @param postId postId used to identify entry in database 
     * @param commentId postId used to identify entry in database 
     */
    public static void addPostComment(int postId, int commentId) {
        String stmt = "INSERT INTO POST_COMMENT (`postID`, `commentID`) VALUES ('" + postId + "', '" + commentId + "');";

        DataUtility.queryUpdate(stmt);
    }

    /**
     * Delete a post-comment association
     * @param postId postId used to identify entry in database 
     * @param commentId postId used to identify entry in database 
     */
    public static void deletePostComment(int postId) {
        String stmt = "DELETE FROM POST_COMMENT WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }
}