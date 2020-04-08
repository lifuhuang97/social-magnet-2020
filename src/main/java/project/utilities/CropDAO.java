package project.utilities;

import project.farm.Crop;
import project.farm.Plot;

import java.util.*;

public class CropDAO {

    /**
     * Retrieve Crop object based on its cropID
     * @param cropId cropID of the Crop object
     * @return Crop object retrieved
     */
    public static Crop getCropById(int cropID){
        
        String query = "SELECT * FROM CROP WHERE CROPID = " + cropID;

        ArrayList<String> cropInfo= DataUtility.singleQuerySelect(query);

        int cropId = Integer.parseInt(cropInfo.get(0));
        String cropname = cropInfo.get(1);
        int cropCost = Integer.parseInt(cropInfo.get(2));
        int harvestTime = Integer.parseInt(cropInfo.get(3));
        int minYield = Integer.parseInt(cropInfo.get(4));
        int maxYield = Integer.parseInt(cropInfo.get(5));
        int salePrice = Integer.parseInt(cropInfo.get(6));
        int xp = Integer.parseInt(cropInfo.get(7));

        Crop crop = new Crop(cropId,cropname,cropCost,harvestTime,minYield,maxYield,salePrice,xp);
        return crop;
    }

    /**
     * Retrieve name of the Crop object based on cropID
     * @param cropID cropID associated with Crop object
     * @return name of the Crop object 
     */
    public static String getCropname(int cropID){

        Crop crop = getCropById(cropID);
        
        return crop.getName();

    }

    /**
     * Get the growth of the crop for display
     * @param cropID the cropID associated with crop
     * @param plantTime datetime in which Crop was planted
     * @param timeToGrowCrop the amount of time needed for crop to grow
     * @return growth rate in #
     */
    public static String getCropGrowth(int cropID, SMDate plantTime, double timeToGrowCrop){


        String tbr = "[";

        double growthTime = SMDate.getTimeDifferenceInMinutes(plantTime);
    
        double percentageGrowth = growthTime / timeToGrowCrop * 100;

        double percentageGrowthDivideTen = percentageGrowth / 10;

        double numberToPrint = Math.floor(percentageGrowthDivideTen);

        int loopValue = (int) numberToPrint;
        int remainder = 10 - loopValue;

        if(loopValue >= 10){
            tbr += "##########] 100%";
        }else{
            for(int i=0;i<loopValue;i++){
                tbr += "#";
            }
            for(int i=0; i<remainder;i++){
                tbr += "-";
            }
            tbr += "] " + String.format("%.0f", percentageGrowth) + "%";
        }

        return tbr;

    }

    /**
     * Retrieve the amount of time needed to grow a Crop
     * @param cropID cropID associated with Crop
     * @return amount of time needed to grow the crop
     */
    public static int getTimeToGrowThisCrop(int cropID){

        Crop crop = getCropById(cropID);

        return crop.getHarvestTime();
    }

    public static ArrayList<Crop> retrieveAll () {
        String stmt = "SELECT * FROM CROP";
        ArrayList<Crop> result = new ArrayList<>();

        ArrayList<ArrayList<String>> arrayResults = DataUtility.querySelect(stmt);

        for (ArrayList<String> each : arrayResults) {
            Crop item = new Crop(Integer.parseInt(each.get(0)), each.get(1), Integer.parseInt(each.get(2)), Integer.parseInt(each.get(3)),
                Integer.parseInt(each.get(4)), Integer.parseInt(each.get(5)), Integer.parseInt(each.get(6)), Integer.parseInt(each.get(7)));
            result.add(item);
        }
        
        return result;
    }

    /**
     * Retrieve the name of the Crop
     * @param cropID cropID associated with Crop
     * @return name of the Crop
     */
    public static String retrieveName (int cropID) {
        String stmt = "SELECT * FROM CROP WHERE CROPID = " + cropID;

        ArrayList<ArrayList<String>> arrayResults = DataUtility.querySelect(stmt);

        if (arrayResults == null || arrayResults.size() == 0) {
            return "";
        } else {
            return arrayResults.get(0).get(1);
        }
    }

    /**
     * Retrieve the produce amount of the Crop 
     * @param plot Plot object that crop is growing on
     * @return produce amount of crop
     */
    public static int getProduceAmt(Plot plot){

        int plantedCropId = plot.getCropID();

        Crop crop = CropDAO.getCropById(plantedCropId);

        int generateProduce = crop.generateYield();

        return generateProduce;

    }
    
}