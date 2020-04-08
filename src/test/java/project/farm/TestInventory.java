package project.farm;


import org.junit.jupiter.api.*;

import main.java.project.utilities.CropDAO;

import java.util.*;

import project.farm.*;
import project.magnet.UserProfile;
import project.utilities.*;
import static org.junit.Assert.*;


/**
 * TestPlot
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInventory {
    
    //Tests for successful addition of Inventory upon purchase from shop.
    @test 
    public void successfulInventory() {
        UserProfile mikeross = UserProfileDAO.getUserProfileByUsername("mikeross");
        CityFarmerCtrl ctrl = new CityFarmerCtrl();
        Crop watermelon = CropDAO.getCropById(4);
        int count = 0;
        ctrl.registerPurchase(watermelon,20,mikeross);
        ArrayList<Inventory> result = InventoryDAO.retrieveByUserID(3);

        if (result != null) {
            for (Inventory each : result) {
                if (each.getCropId() == 4) {
                    count += 1;
                }
            }
        }
        
        if (count == 20) {
            assertTrue("Test case passed.", true);
        } else {
            fail("Test case failed. Inventory mismatch.");
        }
        
    }
}