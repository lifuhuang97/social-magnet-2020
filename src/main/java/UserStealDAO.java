import java.util.ArrayList;

public class UserStealDAO {


    public static boolean checkIfAlreadyStole(int userID, int plotID){

        String stmt = "SELECT * FROM USER_STEAL WHERE USERID = " + userID + " AND PLOTID = " + plotID;

        ArrayList<String> result = DataUtility.singleQuerySelect(stmt);

        if(!result.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public static void addStealRecord(int userID, int plotID){

        String stmt = "INSERT INTO USER_STEAL VALUES (" + userID + ", " + plotID + ")";
        DataUtility.queryUpdate(stmt);

    }


    // use this whenever clearPlot is invoked
    public static void deleteStealRecords(int plotID){

        String prepStmt = "SET SQL_SAFE_UPDATES = 0";
        DataUtility.queryUpdate(prepStmt);
        String stmt = "DELETE FROM USER_STEAL WHERE PLOTID = " + plotID;
        DataUtility.queryUpdate(stmt);
    }


}