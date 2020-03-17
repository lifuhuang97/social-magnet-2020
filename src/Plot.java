

public class Plot {
    private int plotID;
    private int cropID;
    private int plantedTime;

    public Plot(int plotID, int cropID, int plantedTime) {
        this.plotID = plotID;
        this.cropID = cropID;
        this.plantedTime = plantedTime;
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

    public int getPlantedTime() {
        return plantedTime;
    }

    public void setPlantedTime(int plantedTime) {
        this.plantedTime = plantedTime;
    }
}