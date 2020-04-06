package main.java;


// TODO: use this file if prof confirms that crop data can be hardcoded

public class CropDAO {



    private static Crop[] cropData = {
        new Crop(1, "Papaya", 20, 30, 75, 100, 15, 8),
        new Crop(2, "Pumpkin", 30, 60, 5, 5, 200, 20),
        new Crop(3, "Sunflower", 40, 120, 20, 15, 20, 40),
        new Crop(4, "Watermelon", 50, 240, 1, 5, 800, 10)
    };


    public static Crop getCrop(int cropID){
        
        return cropData[cropID -1];

    }

    public static String getCropname(int cropID){

        return cropData[cropID-1].getName();

    }

    public static String getCropGrowth(SMDate plantTime, double timeToGrowCrop){


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

        Crop crop = getCrop(cropID);

        return crop.getHarvestTime();
    }

}