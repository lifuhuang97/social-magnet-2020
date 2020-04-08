package project.utilities;

import java.util.ArrayList;

public class UserWallDAO {

    /**
     * Retrieves all Posts created by user
     * @param userId current user's userId
     * @return ArrayList of postIDs of posts by user
     */
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

    /**
     * Retrieves the user who created the post
     * @param postId postId of post
     * @return int of user who created post
     */
    public static int getUserIdByPostId(int postId) {
        String stmt = "SELECT USERID FROM USER_WALL WHERE POSTID = '" + postId +"';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        return Integer.parseInt(results.get(0).get(0));
    }

    /**
     * Delete post from user's wall
     * @param postId postId of post
     */
    public static void deleteUserWallPost(int postId) {
        String stmt = "DELETE FROM USER_WALL WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }

    /**
     * Adds post to user's wall
     * @param userId user who wants to add post to wall
     * @param postId postId of post
     */
    public static void addUserWallPost(int userId, int postId) {
        String stmt = "INSERT INTO USER_WALL VALUES ('"+ userId +"', '" + postId + "');";

        DataUtility.queryUpdate(stmt);
    }


}