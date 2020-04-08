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
public class TestShop {

    //Checks for deduction of gold upon successful purchase of seeds from shop.
    @test 
    public void successfulPurchase() {
        UserProfile mikeross = UserProfileDAO.getUserProfileByUsername("mikeross");
        CityFarmerCtrl ctrl = new CityFarmerCtrl();
        Crop watermelon = CropDAO.getCropById(4);
        ctrl.registerPurchase(watermelon,20,mikeross);
        if (mikeross.getGold() == 14750) {
            assertTrue("Test case passed.", true);
        } else {
            fail("Test case failed. Gold mismatch.");
        }
        
    }
}