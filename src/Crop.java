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

    public int getCropID() {
        return cropID;
    }

    public void setCropID(int cropID) {
        this.cropID = cropID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(int harvestTime) {
        this.harvestTime = harvestTime;
    }

    public int getMinyield() {
        return minyield;
    }

    public void setMinyield(int minyield) {
        this.minyield = minyield;
    }

    public int getMaxyield() {
        return maxyield;
    }

    public void setMaxyield(int maxyield) {
        this.maxyield = maxyield;
    }

    public int getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(int saleprice) {
        this.saleprice = saleprice;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void printPercentageGrowth(int minutesGrown){

        String growth = "";

        // double growthPercentage = minutesGrown/

    }

}