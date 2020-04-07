// package main.java;

import java.util.*;

public class PlotDAO {


    public static String printPlotDetails(Plot plot){

        String tbr = "";

        if(plot != null){

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

            // System.out.println(thisPlotDetails.toString());
            // System.out.println("This is the plot details length" + thisPlotDetails.size());

            // System.out.println("This is crop ID" + Integer.parseInt(thisPlotDetails.get(1)));

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
                allMyPlots.add(null);
            }
        }

        return allMyPlots;

    }

    public static void plantNewCrop(Plot plot, int cropId){
        int plotId = plot.getPlotID();

        String plantTime = new SMDate().toString();

        String stmt = "UPDATE PLOT SET CROPID = " + cropId + ", plantTime = " + plantTime + " WHERE plotID = " + plotId;

        DataUtility.queryUpdate(stmt);

        //UPDATE PLOT SET cropID = 2, plantTime = "07/04/2020 04:20" WHERE plotID = 5;

    }

    public static void removeCrop(Plot plot){

        int plotId = plot.getPlotID();

        String stmt = "UPDATE PLOT SET CROPID = 0, plantTime = NULL WHERE plotID = " + plotId;
        
        DataUtility.queryUpdate(stmt);

    }

    public static boolean produceYieldAndWiltChecker(Plot plot){
        
        boolean wilt = false;

        if(plot.getProductAmt() == 0){
            int produce = CropDAO.getProduceAmt(plot);
            plot.setProductAmt(produce);

            String stmt = "UPDATE PLOT SET PRODUCEAMT = " + produce + " WHERE plotID = " + plot.getPlotID();
    
            DataUtility.queryUpdate(stmt);
        }else if(plot.getProductAmt() != 0){

            int cropId = plot.getCropID();
            SMDate plantedTime = plot.getPlantedTime();
    
            Crop crop = CropDAO.getCropById(cropId);
    
            wilt = crop.checkIfWilted(plantedTime);
        }

        return wilt;
    }

}