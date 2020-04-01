import java.util.ArrayList;

public class PostTagDAO {
    public static ArrayList<Integer> getPostIdsByTagId(int TagId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT POSTID FROM POST_TAGS WHERE TAGID = '" + TagId +"';";

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

    public static ArrayList<Integer> getTagIdsByPostId(int postId) {
        ArrayList<Integer> ids = new ArrayList<>();
        String stmt = "SELECT TAGID FROM POST_TAGS WHERE POSTID = '" + postId +"';";
        
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

    public static void deletePostTag(int postId) {
        String stmt = "DELETE FROM POST_TAGS WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }

    public static void deletePostTagByTagId(int postId, int tagId) {
        String stmt = "DELETE FROM POST_TAGS WHERE POSTID = '" + postId + "' AND TAGID ='" + tagId + "';";

        DataUtility.queryUpdate(stmt);
    }

    public static void addPostTag(int postId, int tagId) {
        String stmt = "INSERT INTO POST_TAGS (`postID`, `tagID`) VALUES ('" + postId + "', '" + tagId + "');";

        DataUtility.queryUpdate(stmt);
    }
}