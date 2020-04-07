// package main.java;
import java.util.*;

// TODO: use this file if prof confirms that crop data can be hardcoded

public class CropDAO {


    private static Crop[] cropData = {
        new Crop(1, "Papaya", 20, 30, 75, 100, 15, 8),
        new Crop(2, "Pumpkin", 30, 60, 5, 5, 200, 20),
        new Crop(3, "Sunflower", 40, 120, 20, 15, 20, 40),
        new Crop(4, "Watermelon", 50, 240, 1, 5, 800, 10)
    };


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

    public static String getCropname(int cropID){

        Crop crop = getCropById(cropID);
        
        return crop.getName();

    }

    public static String getCropGrowth(int cropID, SMDate plantTime, double timeToGrowCrop){


        String tbr = "[";

        double growthTime = SMDate.getTimeDifferenceInMinutes(plantTime);

        // System.out.println("This is growth time" + growthTime);
    
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

    public static String retrieveName (int cropID) {
        String stmt = "SELECT * FROM CROP WHERE CROPID = " + cropID;

        ArrayList<ArrayList<String>> arrayResults = DataUtility.querySelect(stmt);

        if (arrayResults == null || arrayResults.size() == 0) {
            return "";
        } else {
            return arrayResults.get(0).get(1);
        }
    }

    public static int getProduceAmt(Plot plot){

        int plantedCropId = plot.getCropID();

        Crop crop = CropDAO.getCropById(plantedCropId);

        int generateProduce = crop.generateYield();

        return generateProduce;

    }
    
}