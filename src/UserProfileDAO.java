import java.sql.*;
import java.util.ArrayList;

public class UserProfileDAO {

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

    public static String wealthRankPrint(UserProfile user){

        int myGold = user.getGold();

        ArrayList<Integer> allWealth = wealthComparison(user.getUserId());

        int ranking = allWealth.indexOf(myGold) + 1;

        String tobeprintedRanking = ordinal(ranking);

        if(tobeprintedRanking.equals("1st")){
            return "";
        }else{
            return tobeprintedRanking;
        }


    }

    public static String ordinal(int i){
        String[] suffixes = new String[] {"\\^th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch(i%100){
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }

    public static ArrayList<Integer> wealthComparison(int userid){

        String listOfFriendsIncludingSelf = "(" + userid;

        String statement = "SELECT friendID FROM friends WHERE userID = " + userid;
        ArrayList<ArrayList<String>> friendsList = DataUtility.querySelect(statement);

        for(ArrayList<String> friend : friendsList){
            listOfFriendsIncludingSelf += ", " + friend.get(0);
        }

        listOfFriendsIncludingSelf += ")";

        String statement2 = "SELECT gold FROM userprofile WHERE userID in " + listOfFriendsIncludingSelf;
        ArrayList<ArrayList<String>> allWealth = DataUtility.querySelect(statement2);

        ArrayList<Integer> allWealthConv = new ArrayList<>();

        for (ArrayList<String> gold : allWealth){
            allWealthConv.add(Integer.parseInt(gold.get(0)));
        }

        return allWealthConv;

    }

}