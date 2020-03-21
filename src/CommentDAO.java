import java.util.*;

public class CommentDAO {
    public static Comment retrieveCommentById(int commentId) {

        String stmt = "SELECT * FROM COMMENT WHERE COMMENTID = '" + commentId + "';";

        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);

        results.remove(0);
        String content = results.get(0).get(1);
        SMDate datetime = new SMDate(results.get(0).get(2));

        return new Comment(commentId, content, datetime);
    }
}