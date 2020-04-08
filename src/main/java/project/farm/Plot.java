// package main.java;

public class Plot {
    private int plotID;
    private int cropID;
    private SMDate plantedTime;
    private Integer produceAmt;
    private Integer stolen;

    /**
     * Constructs a specified Plot object
     * @param plotID the plotID of the Plot object
     * @param cropID the cropID of the Plot object
     * @param plantedTime the plantedTime of the Plot object
     * @param produceAmt the produceAmt of the Plot object
     */
    public Plot(int plotID, int cropID, SMDate plantedTime, Integer produceAmt) {
        this.plotID = plotID;
        this.cropID = cropID;
        this.plantedTime = plantedTime;
        this.produceAmt = produceAmt;
        this.stolen = 0;
    }

    /**
     * Constructs a specified Stolen Plot object
     * @param plotID the plotID of the Plot object
     * @param cropID the cropID of the Plot object
     * @param plantedTime the plantedTime of the Plot object
     * @param produceAmt the produceAmt of the Plot object
     * @param stolen the amount stolen
     */
    public Plot(int plotID, int cropID, SMDate plantedTime, Integer produceAmt, Integer stolen){
        this(plotID, cropID, plantedTime, produceAmt);
        this.stolen = stolen;
    }

    /**
     * Gets the plotID
     * @return the plotID
     */
    public int getPlotID() {
        return plotID;
    }

    /**
     * Sets the plotID of the crop
     * @param the plotID
     */
    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    /**
     * Gets the cropID
     * @return the cropID
     */
    public int getCropID() {
        return cropID;
    }

    /**
     * Sets the cropID of the crop
     * @param the cropID
     */
    public void setCropID(int cropID) {
        this.cropID = cropID;
    }

    /**
     * Gets the plantedTime
     * @return the plantedTime
     */
    public SMDate getPlantedTime() {
        return plantedTime;
    }

    /**
     * Sets the plantedTime of the crop
     * @param the plantedTime
     */
    public void setPlantedTime(SMDate plantedTime) {
        this.plantedTime = plantedTime;
    }

    /**
     * Gets the produceAmt
     * @return the produceAmt
     */
    public int getProductAmt(){
        return produceAmt;
    }

    /**
     * Sets the produceAmt of the crop
     * @param the produceAmt
     */
    public void setProductAmt(Integer produceAmt){
        this.produceAmt = produceAmt;
    }

    /**
     * Gets the stolen amount
     * @return the stolen amount
     */
    public int getStolen(){
        return stolen;
    }

    /**
     * Sets the amount of the crop stolen
     * @param the amount stolen
     */
    public void setStolen(Integer stolen){
        this.stolen = stolen;
    }
}