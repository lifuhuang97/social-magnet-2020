import java.sql.*;
import java.util.ArrayList;

public class UserProfileDAO {

    public static UserProfile getUserProfileByUsername(String username) {
        UserProfile user = null;
        String stmt = "SELECT * FROM USERPROFILE WHERE USERNAME = '" + username + "';";
        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);

        if(results.size() == 1) {
            return user;
            // TODO: Throw error
        } else {
            int userId = Integer.parseInt(results.get(1).get(0));
            String fullName = results.get(1).get(1);
            String password = results.get(1).get(3);
            int rank = Integer.parseInt(results.get(1).get(4));
            int xp = Integer.parseInt(results.get(1).get(5));
            int gold = Integer.parseInt(results.get(1).get(6));
            user = new UserProfile(userId, fullName, username, password, rank, xp, gold);
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

}