// package main.java;

import java.util.*;

public class PlotDAO {

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

    public static int retrievePlotOwner(int plotId){

        String stmt = "SELECT userID from USER_PLOT WHERE PLOTID = " + plotId;

        ArrayList<String> result = DataUtility.singleQuerySelect(stmt);

        int userId = Integer.parseInt(result.get(0));

        return userId;
    }

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

    public static ArrayList<Plot> getMyPlots(UserProfile user){

        ArrayList<Plot> allMyPlots = new ArrayList<Plot>();
        int currentUserId = user.getUserId();

        ArrayList<ArrayList<String>> myPlotIds_DB = DataUtility.querySelect("SELECT * FROM USER_PLOT WHERE USERID = " + currentUserId);

        for(ArrayList<String> myPlotId_DB : myPlotIds_DB){

            String thisPlotId = myPlotId_DB.get(1);

            // System.out.println("This is plot ID " + thisPlotId);

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

    public static void plantNewCrop(Plot plot, int cropId){
        int plotId = plot.getPlotID();

        String plantTime = new SMDate().toString();

        String stmt = "UPDATE PLOT SET CROPID = " + cropId + ", plantTime = '" + plantTime + "' WHERE plotID = " + plotId;

        DataUtility.queryUpdate(stmt);

        //UPDATE PLOT SET cropID = 2, plantTime = "07/04/2020 04:20" WHERE plotID = 5;

    }

    public static void removeCrop(Plot plot){

        int plotId = plot.getPlotID();

        String stmt = "UPDATE PLOT SET CROPID = 0, plantTime = NULL, produceAmt = 0 WHERE plotID = " + plotId;
        
        DataUtility.queryUpdate(stmt);

    }

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

}