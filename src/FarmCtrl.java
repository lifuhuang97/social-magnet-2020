/**
 * FarmCtrl
 */
import java.util.*;

 public class FarmCtrl {

    
    // contains all the DAO requirements from methods in main menu

    // e,g, ctrl.searchPresident(IGName, year)


    public ArrayList<Plot> getMyPlots(UserProfile currentUser){

        ArrayList<Plot> allMyPlots = new ArrayList<Plot>();
        int currentUserId = currentUser.getUserId();

        ArrayList<ArrayList<String>> myPlotIds_DB = DataUtility.querySelect("SELECT * FROM USER_PLOT WHERE USERID = " + currentUserId);

        for(ArrayList<String> myPlotId_DB : myPlotIds_DB){

            String thisPlotId = myPlotId_DB.get(0);
            ArrayList<String> thisPlotDetails = DataUtility.singleQuerySelect("SELECT * FROM PLOT WHERE PLOTID = " + thisPlotId);
            
            // Plot thisPlot = new Plot(Integer.parseInt(thisPlotDetails(0)));

        }

        return allMyPlots;

    }


}