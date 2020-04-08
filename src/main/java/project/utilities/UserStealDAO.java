package project.utilities;
import java.util.ArrayList;

/**
 * This is the UserStealDAO that connects to the UserSteal Database 
 */

public class UserStealDAO {

    /**
     * Check if a plot has already been stolen from
     * @param userID userID associated with stolen plot
     * @param plotID plotID associated with stolen plot
     * @return whether plot hs been stolen from
     */
    public static boolean checkIfAlreadyStole(int userID, int plotID){

        String stmt = "SELECT * FROM USER_STEAL WHERE USERID = " + userID + " AND PLOTID = " + plotID;

        ArrayList<String> result = DataUtility.singleQuerySelect(stmt);

        if(!result.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Add record indicating that the plot has been stolen from
     * @param userID userID associated with stolen plot
     * @param plotID plotID associated with stolen plot
     */
    public static void addStealRecord(int userID, int plotID){

        String stmt = "INSERT INTO USER_STEAL VALUES (" + userID + ", " + plotID + ")";
        DataUtility.queryUpdate(stmt);

    }

    /**
     * Delete steal records for a plot
     * Used when the plot owner harvests / clears the plot.
     * 
     * @param plotID plotID associated with plot
     */
    public static void deleteStealRecords(int plotID){

        String prepStmt = "SET SQL_SAFE_UPDATES = 0";
        DataUtility.queryUpdate(prepStmt);
        String stmt = "DELETE FROM USER_STEAL WHERE PLOTID = " + plotID;
        DataUtility.queryUpdate(stmt);
    }


}