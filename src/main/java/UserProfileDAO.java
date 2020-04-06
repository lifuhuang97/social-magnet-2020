package main.java;

import java.util.ArrayList;

public class UserProfileDAO {

    /**
     * Retrieves UserProfile object by username
     * @param username username of user
     * @return UserProfile object of user
     */
    public static UserProfile getUserProfileByUsername(String username) {

        UserProfile user = null;
        String stmt = "SELECT * FROM USERPROFILE WHERE USERNAME = '" + username + "';";
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if(results.size() == 0) {
            return user;
            // TODO: Throw error
        } else {
            int userId = Integer.parseInt(results.get(0).get(0));
            String fullName = results.get(0).get(1);
            String password = results.get(0).get(3);
            int rank = Integer.parseInt(results.get(0).get(4));
            int xp = Integer.parseInt(results.get(0).get(5));
            int gold = Integer.parseInt(results.get(0).get(6));

            Rank myRank =  new RankDAO().getMyRank(rank);

            user = new UserProfile(userId, fullName, username, password, myRank, xp, gold);
        }

        return user;
    }

    /**
     * Retrieves UserProfile object by userId
     * @param userId userId of user
     * @return UserProfile object of user
     */
    public static UserProfile getUserProfileByUserId(int userId) {

        UserProfile user = null;
        String stmt = "SELECT * FROM USERPROFILE WHERE USERID = '" + userId + "';";
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if(results.size() == 0) {
            return user;
            // TODO: Throw error
        } else {
            String fullName = results.get(0).get(1);
            String username = results.get(0).get(2);
            String password = results.get(0).get(3);
            int rank = Integer.parseInt(results.get(0).get(4));
            int xp = Integer.parseInt(results.get(0).get(5));
            int gold = Integer.parseInt(results.get(0).get(6));

            Rank myRank =  new RankDAO().getMyRank(rank);

            user = new UserProfile(userId, fullName, username, password, myRank, xp, gold);
        }

        return user;
    }

    /**
     * Creates a new user in the database
     * @param username username of user
     * @param fullName full name of user
     * @param password password of user
     * @return boolean of status
     */
    public static boolean createUser(String username, String fullName, String password) {
        boolean status = true;
        ArrayList<String> stmts = new ArrayList<>();
        stmts.add("INSERT INTO USERPROFILE (fullname, username, password) VALUES ('" + fullName + "', '" + username + "', '" + password + "');");

        DataUtility.multiQueryUpdate(stmts);

        // TODO: Handle DB errors
        // try {
        //     DataUtility.multiQueryUpdate(stmts);
        // } catch (SQLException e) {
        //     status = false;
        // }

        return status;
    }

}