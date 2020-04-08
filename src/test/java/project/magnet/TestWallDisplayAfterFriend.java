package project.magnet;

/**
 * Test WallDisplay after adding friend
 */

import org.junit.jupiter.api.*;
import java.util.*;

import project.utilities.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestWallDisplayAfterFriend {

    @BeforeAll
    public void login() {
        UserProfile user = WelcomeMenuCtrl.login("harveyspecter999", "iamthebest");
    }

    @Test
    public void testWallDisplay() {
        WallCtrl ctrl = new WallCtrl(user);
        Assertions.assertEquals("2nd", ctrl.WealthRanking());
    }

}