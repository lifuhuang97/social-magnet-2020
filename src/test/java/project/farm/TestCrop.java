package project.farm;


import org.junit.jupiter.api.*;
import java.util.*;

import project.farm.*;
import project.magnet.UserProfile;
import project.utilities.*;
import static org.junit.Assert.*;


/**
 * TestCrop
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCrop {

    private UserProfile testUser;
    private Plot testPlot;
    private int testPlotId;
    private Crop testCrop = CropDAO.getCropById(1);
    private CityFarmerCtrl ctrl;
    private CityFarmerMenu farmMenu = new CityFarmerMenu();


    @BeforeAll
    public void createTestUser(){

        UserProfile checkIfExist = UserProfileDAO.getUserProfileByUsername("croptest");

        if(checkIfExist != null){
            DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'croptest'");
        }
        
        if(UserProfileDAO.createUser("croptest","Crop Growth Test", "croptest")){
            testUser = UserProfileDAO.getUserProfileByUsername("croptest");
            ctrl = new CityFarmerCtrl(testUser);
        }

    }

    @Test
    public void testIfPlotInitializeWithCorrectValues(){

        PlotDAO.checkIfPlotCountNeedsUpdating(testUser);

        ArrayList<Plot> myPlots = PlotDAO.getMyPlots(testUser);

        testPlot = myPlots.get(0);
        testPlotId = testPlot.getPlotID();

        int expectedCropID = 0;
        int expectedDate = null;
        int expectedProduceAmt = 0;
        int expectedStolen = 0;

        assertEquals(expectedCropID, testPlot.getCropID());
        assertEquals(expectedDate, testPlot.getPlantedTime());
        assertEquals(expectedProduceAmt, testPlot.getProductAmt());
        assertEquals(expectedStolen,testPlot.getStolen());
    }

    @Test
    public void testIfPlotUpdatesCorrectlyAfterPlant(){

        ctrl.plantPlot(testPlotId, testCrop.getCropID());

        Plot plantedPlot = PlotDAO.getPlotById(testPlotId);

        int expectedCropID = 1;
        
        assertEquals(expectedCropID, plantedPlot.getCropID());
    }

    @Test
    public void testCannotPlantOnGrowingPlot(){

        Plot plantedPlot = PlotDAO.getPlotById(testPlotId);

        SMDate originalPlantTime = plantedPlot.getPlantedTime();

        ctrl.plantPlot(testPlotId, testCrop.getCropID());

        SMDate newPlantedTime = plantedPlot.getPlantedTime();

        assertEquals(originalPlantTime, newPlantedTime);

    }

    @AfterAll
    public void deleteTestUser(){
        DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'croptest'");
    }
}