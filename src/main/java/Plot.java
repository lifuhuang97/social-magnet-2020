// package main.java;

public class Plot {
    private int plotID;
    private int cropID;
    private SMDate plantedTime;
    private Integer produceAmt;

    public Plot(int plotID, int cropID, SMDate plantedTime, Integer produceAmt) {
        this.plotID = plotID;
        this.cropID = cropID;
        this.plantedTime = plantedTime;
        this.produceAmt = produceAmt;
    }

    public int getPlotID() {
        return plotID;
    }

    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    public int getCropID() {
        return cropID;
    }

    public void setCropID(int cropID) {
        this.cropID = cropID;
    }

    public SMDate getPlantedTime() {
        return plantedTime;
    }

    public void setPlantedTime(SMDate plantedTime) {
        this.plantedTime = plantedTime;
    }

    public int getProductAmt(){
        return produceAmt;
    }

    public void setProductAmt(Integer produceAmt){
        this.produceAmt = produceAmt;
    }
}