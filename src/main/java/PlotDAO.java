package main.java;

import java.util.*;

public class PlotDAO {


    public static String printPlotDetails(Plot plot){

        String tbr = "";

        if(plot != null){

            int cropInThisPlot = plot.getCropID();

            double timeNeededToGrow = (double) CropDAO.getTimeToGrowThisCrop(cropInThisPlot);

            String cropname = CropDAO.getCropname(cropInThisPlot);

            SMDate plantedTime = plot.getPlantedTime();

            String thisCropGrowthPercentage = CropDAO.getCropGrowth(plantedTime, timeNeededToGrow);
        
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

            String thisPlotId = myPlotId_DB.get(0);

            ArrayList<String> thisPlotDetails = DataUtility.singleQuerySelect("SELECT * FROM PLOT WHERE PLOTID = " + thisPlotId);

            if(Integer.parseInt(thisPlotDetails.get(1)) != 0){
                Plot thisPlot = new Plot(Integer.parseInt(thisPlotDetails.get(0)), Integer.parseInt(thisPlotDetails.get(1)), new SMDate(thisPlotDetails.get(2)));
                allMyPlots.add(thisPlot);
            }else{
                allMyPlots.add(null);
            }
        }

        return allMyPlots;

    }

}