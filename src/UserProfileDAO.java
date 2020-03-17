import java.sql.*;
import java.util.ArrayList;

public class UserProfileDAO {

    public static UserProfile getUserProfileByUsername(String username) {
        // Connection conn = DataUtility.open();
        // ArrayList<String> stmts = new ArrayList<>();
        // UserProfile user = null;
        // stmts.add("SELECT * FROM USERPROFILE WHERE USERNAME = '" + username + "';");
        // if(DataUtility.prepareStmt(stmts).isEmpty()) {
        //     return user;
        //     // TODO: Throw error
        // } else {
        //     ResultSet rs = DataUtility.QuerySelect(DataUtility.prepareStmt(stmts).get(0));
        //     try {
        //         while(rs.next()){
        //             int userId = rs.getInt(1);
        //             String fullName = rs.getString(2);
        //             String uName = rs.getString(3);
        //             String password = rs.getString(4);
        //             int rank = rs.getInt(5);
        //             int xp = rs.getInt(6);
        //             int gold = rs.getInt(7);
        //             user = new UserProfile(userId, fullName, uName, password, rank, xp, gold);
        //         }
        //         return user;
        //     } catch (SQLException e) {
        //         System.out.println("Printing error");
        //     }
        // }
        // return user;
        return null;
    }

    public static boolean createUser(String username, String fullName, String password) {
        // Connection conn = DataUtility.open();
        // ArrayList<String> stmts = new ArrayList<>();
        // stmts.add("INSERT INTO USERPROFILE (fullname, username, password) VALUES ('" + fullName + "', '" + username + "', '" + password + "');");
        // ArrayList<PreparedStatement> preparedStmts = DataUtility.prepareStmt(stmts);

        // boolean status = DataUtility.multiQueryUpdate(preparedStmts);
        // return status;
        return true;
    }

}