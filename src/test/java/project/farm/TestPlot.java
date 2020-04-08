package project.farm;


import org.junit.jupiter.api.*;
import java.util.*;

import project.farm.*;
import project.magnet.UserProfile;
import project.utilities.*;
import static org.junit.Assert.*;


/**
 * TestPlot
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPlot {

    private UserProfile testUser;
    private CityFarmerCtrl ctrl;
    private CityFarmerMenu farmMenu = new CityFarmerMenu();

    @BeforeAll
    public void resetUser(){

        UserProfile checkIfExist = UserProfileDAO.getUserProfileByUsername("plottest");

        if(checkIfExist != null){
            DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'plottest'");
        }
        
        if(UserProfileDAO.createUser("plottest","Plot Test", "plottest")){
            testUser = UserProfileDAO.getUserProfileByUsername("plottest");
            ctrl = new CityFarmerCtrl(testUser);
        }

    }

    @Test
    public void testIfStartWithZeroPlot(){

        ArrayList<Plot> myPlots = PlotDAO.getMyPlots(testUser);

        int expectedPlotSize = 0;

        int myPlotSize = myPlots.size();

        assertEquals(expectedPlotSize, myPlotSize);

    }

    @Test
    public void testIfAutomaticGeneratePlotWhenFirstEnterFarm(){
        CityFarmerCtrl ctrl = new CityFarmCtrl(testUser);

        ArrayList<Plot> myPlots = PlotDAO.getMyPlots(testUser);

        if()



    }




    
}