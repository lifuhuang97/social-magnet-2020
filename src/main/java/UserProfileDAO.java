// package main.java;

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

            Rank myRank =  RankDAO.getMyRank(rank);

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
            int rankId = Integer.parseInt(results.get(0).get(4));
            int xp = Integer.parseInt(results.get(0).get(5));
            int gold = Integer.parseInt(results.get(0).get(6));

            Rank myRank =  RankDAO.getMyRank(rankId);

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


    public static UserProfile updateUserGoldAndXp(UserProfile user, int xpChange, int goldChange){

        int userId = user.getUserId();
        int currentXp = user.getXp();
        int currentGold = user.getGold();

        int newXp = currentXp + xpChange;
        int newGold = currentGold + goldChange;

        String stmt = "UPDATE USERPROFILE SET XP = " + newXp + ", GOLD = " + newGold + " WHERE USERID = " + userId;

        DataUtility.queryUpdate(stmt);

        String stmt2 = "SELECT * FROM USERPROFILE WHERE USERID = " + user.getUserId();
        ArrayList<String> result = DataUtility.singleQuerySelect(stmt2);

        UserProfile updatedUser = new UserProfile(Integer.parseInt(result.get(0)), result.get(1), result.get(2), result.get(3), 
                                                  RankDAO.getMyRank(Integer.parseInt(result.get(4))), Integer.parseInt(result.get(5)), 
                                                  Integer.parseInt(result.get(6)));
        return updatedUser;
    }

    /**
     *  Use this method whenever there's a change in XP for a user
     * @param user The userprofile in view
     */
    public static boolean checkIfRankUpdate(UserProfile user){
        int currentExp = user.getXp();
        int currentRankId = user.getRank().getRankID();

        Rank nextRank = RankDAO.getNextRank(currentRankId);
        int nextRankXpRequirement = nextRank.getXp();

        if(currentExp >= nextRankXpRequirement){
            int nextRankId = nextRank.getRankID();
            int currentUserId = user.getUserId();
            String stmt = "UPDATE USERPROFILE SET RANK = " + nextRankId + " WHERE USERID = " + currentUserId;
            DataUtility.queryUpdate(stmt);
            return true;
        }else{
            return false;
        }
    }

}