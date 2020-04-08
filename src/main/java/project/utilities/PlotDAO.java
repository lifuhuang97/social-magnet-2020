// package main.java;

import java.util.*;

public class PlotDAO {

    /**
     * Retrieve Plot object associated plotId
     * @param plotId plotId associated with Plot
     * @return Plot object assciated with plotId
     */
    public static Plot getPlotById(int plotId){

        Plot tbr;
        String stmt = "SELECT * from PLOT WHERE PLOTID = " + plotId;

        ArrayList<String> result = DataUtility.singleQuerySelect(stmt);

        if(Integer.parseInt(result.get(1)) != 0){

            tbr = new Plot(Integer.parseInt(result.get(0)), Integer.parseInt(result.get(1)), new SMDate(result.get(2)), Integer.parseInt(result.get(3)));
        }else{
            tbr = new Plot(Integer.parseInt(result.get(0)), 0, null, 0);
        }
        return tbr;
    }

    /**
     * Retrieve stealable Plot objects associated plotId 
     * @param plotId plotId associated with stealable Plot object
     * @return stealable Plot object assciated with plotId
     */
    public static Plot getPlotWithStealableById(int plotId){

        Plot tbr;
        String stmt = "SELECT * from PLOT WHERE PLOTID = " + plotId;

        ArrayList<String> result = DataUtility.singleQuerySelect(stmt);

        if(Integer.parseInt(result.get(1)) != 0){

            tbr = new Plot(Integer.parseInt(result.get(0)), Integer.parseInt(result.get(1)), new SMDate(result.get(2)), Integer.parseInt(result.get(3)), Integer.parseInt(result.get(4)));
        }else{
            tbr = new Plot(Integer.parseInt(result.get(0)), 0, null, 0);
        }
        return tbr;
    }    

    /**
     * Update Plot on the stolen amount
     * @param plotId the plotId of Plot object
     * @param newStolen the stolen amount
     * @param newProduce the new produce
     */
    public static void updatePlotStolenAmt(int plotId, int newStolen, int newProduce){

        String stmt = "UPDATE PLOT SET PRODUCEAMT = " + newProduce + ", STOLEN = " + newStolen + " WHERE PLOTID = " + plotId;

        DataUtility.queryUpdate(stmt);
    }


    /**
     * Retrieve the userId of the Plot 
     * @param plotId plotId
     * @return userId that owns the Plot
     */
    public static int retrievePlotOwner(int plotId){

        String stmt = "SELECT userID from USER_PLOT WHERE PLOTID = " + plotId;

        ArrayList<String> result = DataUtility.singleQuerySelect(stmt);

        int userId = Integer.parseInt(result.get(0));

        return userId;
    }

    /**
     * Print the the details of the plot
     * @param Plot Plot object to be printed
     * @return tbr a String describing the Plot
     */
    public static String printPlotDetails(Plot plot){

        String tbr = "";

        if(plot.getPlantedTime() != null){

            int cropInThisPlot = plot.getCropID();

            double timeNeededToGrow = (double) CropDAO.getTimeToGrowThisCrop(cropInThisPlot);

            String cropname = CropDAO.getCropname(cropInThisPlot);

            SMDate plantedTime = plot.getPlantedTime();

            String thisCropGrowthPercentage = CropDAO.getCropGrowth(cropInThisPlot,plantedTime, timeNeededToGrow);
        
            tbr += cropname + "\t" + thisCropGrowthPercentage;
        }else{
            tbr += "<empty>";
        }

        return tbr;

    }

    /**
     * Retrieve an ArrayList of Plot objects own by a user
     * @param user UserProfile object of user
     * @return ArrayList of Plot objects
     */
    public static ArrayList<Plot> getMyPlots(UserProfile user){

        ArrayList<Plot> allMyPlots = new ArrayList<Plot>();
        int currentUserId = user.getUserId();

        ArrayList<ArrayList<String>> myPlotIds_DB = DataUtility.querySelect("SELECT * FROM USER_PLOT WHERE USERID = " + currentUserId);


        for(ArrayList<String> myPlotId_DB : myPlotIds_DB){

            String thisPlotId = myPlotId_DB.get(1);

            ArrayList<String> thisPlotDetails = DataUtility.singleQuerySelect("SELECT * FROM PLOT WHERE PLOTID = " + thisPlotId);

            

            if(Integer.parseInt(thisPlotDetails.get(1)) != 0){

                Plot thisPlot;

                int plotId = Integer.parseInt(thisPlotDetails.get(0));
                int cropId = Integer.parseInt(thisPlotDetails.get(1));
                SMDate plantTime = new SMDate(thisPlotDetails.get(2));
            
                if(Integer.parseInt(thisPlotDetails.get(3)) != 0){
                    int produce = Integer.parseInt(thisPlotDetails.get(3));
                    thisPlot = new Plot(plotId,cropId,plantTime,produce);
                }else{
                    thisPlot = new Plot(plotId,cropId,plantTime,0);
                }

                allMyPlots.add(thisPlot);
            }else{
                int plotId = Integer.parseInt(thisPlotDetails.get(0));
                allMyPlots.add(new Plot(plotId, 0, null, 0));
            }
        }
        
        return allMyPlots;

    }

    /**
     * Plant a new crop on specified Plot 
     * @param plotId of the Plot to be planted on
     * @param cropId of the Crop to be planted 
     */
    public static void plantNewCrop(Plot plot, int cropId){
        int plotId = plot.getPlotID();

        String plantTime = new SMDate().toString();

        String stmt = "UPDATE PLOT SET CROPID = " + cropId + ", plantTime = '" + plantTime + "' WHERE plotID = " + plotId;

        DataUtility.queryUpdate(stmt);

        //UPDATE PLOT SET cropID = 2, plantTime = "07/04/2020 04:20" WHERE plotID = 5;

    }

    /**
     * Remove a crop from plot
     * @param Plot object to have crop removed from
     */
    public static void removeCrop(Plot plot){

        int plotId = plot.getPlotID();

        UserStealDAO.deleteStealRecords(plotId);

        String stmt = "UPDATE PLOT SET CROPID = 0, plantTime = NULL, produceAmt = 0, stolen = 0 WHERE plotID = " + plotId;
        
        DataUtility.queryUpdate(stmt);

    }

    /**
     * Check if the crop planted on a plot should have any produce.
     * If yes, call a method to generate its produce and update in DB.
     * 
     * Also checks if the method should have wilted via a different method and returns that result.
     * 
     * @param Plot object to check 
     * @return whether the crop on plot has wilted
     */
    public static boolean produceYieldAndWiltChecker(Plot plot){
        
        boolean wilt = false;

        if(plot.getProductAmt() == 0){
            int produce = CropDAO.getProduceAmt(plot);
            plot.setProductAmt(produce);

            String stmt = "UPDATE PLOT SET PRODUCEAMT = " + produce + " WHERE plotID = " + plot.getPlotID();
    
            DataUtility.queryUpdate(stmt);
        }

        int cropId = plot.getCropID();
        SMDate plantedTime = plot.getPlantedTime();

        Crop crop = CropDAO.getCropById(cropId);

        wilt = crop.checkIfWilted(plantedTime);

        return wilt;
    }

    /**
     * Checks if Plot count needs updating and update his plots if it does.
     * 
     * @param user UserProfile who needs to be checked
     */
    public static void checkIfPlotCountNeedsUpdating(UserProfile user){

        int userId = user.getUserId();

        String stmt = "SELECT * FROM USER_PLOT WHERE USERID = " + userId;

        ArrayList<ArrayList<String>> result = DataUtility.querySelect(stmt);

        ArrayList<Integer> existingPlotIds = new ArrayList<Integer>();


        int supposedNumOfPlots = user.getRank().getPlots();
        int actualNumOfPlots = result.size();

        if(actualNumOfPlots > 0){
            for(ArrayList<String> plot : result){
                int plotId = Integer.parseInt(plot.get(1));
                existingPlotIds.add(plotId);
            }
        }

        int difference = supposedNumOfPlots - actualNumOfPlots;

        for(int i=0;i<difference;i++){
            String insertStmt = "INSERT INTO USER_PLOT (USERID) VALUES (" + userId + ")";
            DataUtility.queryUpdate(insertStmt);
        }

        ArrayList<ArrayList<String>> newResult = DataUtility.querySelect(stmt);

        for(ArrayList<String> plot : newResult){

            int currentPlotId = Integer.parseInt(plot.get(1));

            if(!existingPlotIds.contains(currentPlotId)){
                String addNewPlotStmt = "INSERT INTO PLOT (PLOTID) VALUES (" + currentPlotId + ")";
                DataUtility.queryUpdate(addNewPlotStmt);
            }
        }
    }

}