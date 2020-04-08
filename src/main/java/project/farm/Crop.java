package project.farm;

import project.utilities.SMDate;

import java.util.*;

public class Crop {
    private int cropID;
    private String name;
    private int cost;
    private int harvestTime;
    private int minyield;
    private int maxyield;
    private int saleprice;
    private int xp;

    /**
     * Constructs a specified Crop object
     * @param cropID the cropID of the Crop
     * @param name  the name of the Crop
     * @param cost the cost of the Crop 
     * @param harvestTime the harvestTime of the Crop
     * @param minyield the minimum yield the Crop can provide
     * @param maxyield the maximum yield the Crop can provide
     * @param saleprice the price of the Crop
     * @param xp amount of xp that can be gained from Crop
     */
    public Crop(int cropID, String name, int cost, int harvestTime, int minyield, int maxyield, int saleprice, int xp) {
        this.cropID = cropID;
        this.name = name;
        this.cost = cost;
        this.harvestTime = harvestTime;
        this.minyield = minyield;
        this.maxyield = maxyield;
        this.saleprice = saleprice;
        this.xp = xp;
    }

    /**
     * Gets the CropID
     * @return the CropID
     */
    public int getCropID() {
        return cropID;
    }

    /**
     * Sets the CropID
     * @param the CropID
     */
    public void setCropID(int cropID) {
        this.cropID = cropID;
    }

    /**
     * Gets the name of the Crop
     * @return the name of the Crop
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Crop
     * @param the name of the Crop
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the cost of the Crop
     * @return the cost of the Crop
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of the Crop
     * @param the cost of the Crop
     */
    public void setCost(int cost) {
        this.cost = cost;
    }


    /**
     * Gets the harvestTime of the Crop
     * @return the harvestTime of the Crop
     */
    public int getHarvestTime() {
        return harvestTime;
    }

    /**
     * Sets the harvestTime of the Crop
     * @param the harvestTime of the Crop
     */
    public void setHarvestTime(int harvestTime) {
        this.harvestTime = harvestTime;
    }

    /**
     * Gets the minyield of the Crop
     * @return the minyield of the Crop
     */
    public int getMinyield() {
        return minyield;
    }

    /**
     * Sets the minyield of the Crop
     * @param the minyield of the Crop
     */
    public void setMinyield(int minyield) {
        this.minyield = minyield;
    }

    /**
     * Gets the maxyield of the Crop
     * @return the maxyield of the Crop
     */
    public int getMaxyield() {
        return maxyield;
    }

    /**
     * Sets the maxyield of the Crop
     * @param the maxyield of the Crop
     */
    public void setMaxyield(int maxyield) {
        this.maxyield = maxyield;
    }

    /**
     * Gets the saleprice of the Crop
     * @return the saleprice of the Crop
     */
    public int getSaleprice() {
        return saleprice;
    }

    /**
     * Sets the saleprice of the Crop
     * @param the saleprice of the Crop
     */
    public void setSaleprice(int saleprice) {
        this.saleprice = saleprice;
    }

    /**
     * Gets the xp gained from Crop
     * @return the xp gained from Crop
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the xp gained from the Crop
     * @param the xp grained from the Crop
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Randomly generate the yield fro the crop
     * @return produce gainedfrom the Crop
     */
    public int generateYield(){

        int minMaxDiff = maxyield - minyield + 1;

        int produce = minyield + new Random().nextInt(minMaxDiff);

        return produce;
    }

    /**
     * Check if the crop has wilted
     * @param the time the crop was planted
     * @return whether the crop has wilted
     */
    public boolean checkIfWilted(SMDate plantTime){

        int GrowthDuration = SMDate.getTimeDifferenceInMinutes(plantTime);

        if(GrowthDuration > (2 * harvestTime)){
            return true;
        }

        return false;

    }

}