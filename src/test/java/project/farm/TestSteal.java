package project.farm;


import org.junit.jupiter.api.*;
import java.util.*;

import project.farm.*;
import project.magnet.UserProfile;
import project.utilities.*;
import static org.junit.Assert.*;


/**
 * TestSteal
 */

@TestInstance(TestInstance.LifeCycle.PER_CLASS)
public class TestSteal{

    private UserProfile testUser1;
    private UserProfile testUser2;
    private CityFarmerCtrl ctrl;
    private Crop testCrop = CropDAO.getCropById(1);
    Plot testStealPlot;
    private int testStealPlotId;
    private HashMap<Integer,Integer> testHashMap = new HashMap<Integer,Integer>();

    @BeforeAll
    public void createTestUser(){

        UserProfile checkIfExist = UserProfileDAO.getUserProfileByUsername("stealtest1");

        if(checkIfExist != null){
            DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'stealtest1'");
        }
        
        if(UserProfileDAO.createUser("stealtest1","Steal Crop Test", "stealtest")){
            testUser1 = UserProfileDAO.getUserProfileByUsername("croptest");
            ctrl = new CityFarmerCtrl(testUser);
        }

        UserProfile checkIfExist = UserProfileDAO.getUserProfileByUsername("stealtest2");

        if(checkIfExist != null){
            DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'stealtest2'");
        }
        
        if(UserProfileDAO.createUser("stealtest2","Steal Crop Test", "stealtest")){
            testUser2 = UserProfileDAO.getUserProfileByUsername("croptest");
        }

        testStealPlot = new Plot(555, 1, new SMDate(), 1000, 0);
        DataUtility.queryUpdate("INSERT INTO ")
        testHashMap.put(1,555);

    }

    @Test
    public void testIfCanSteal(){

        ArrayList<String> result = ctrl.processSteal(testUser2, testHashMap);

        String invalidXp = "";
        String invalidGold = "";

        assertNotEquals(result.get(1), invalidXp);
        assertNotEquals(result.get(2), invalidGold);
    }


    @AfterAll
    public void deleteTestUser(){
        DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'stealtest1'");
        DataUtility.queryUpdate("DELETE FROM USERPROFILE WHERE USERNAME = 'stealtest2'");
    }


}