package project.magnet;

/**
 * Test AddFriend 
 */

import org.junit.jupiter.api.*;
import java.util.*;

import project.utilities.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAddFriend {

    @Test
    public void testAddFriend() {
        UserProfile user = WelcomeMenuCtrl.login("harveyspecter999", "iamthebest");
        FriendsCtrl ctrl = new FriendsCtrl(user);
        UserProfile friend = WelcomeMenuCtrl.login("mikeross", "bigbrainmike");
        ctrl.accept(friend);
        Assertions.assertEquals(true, FriendsDAO.isFriends(user.getUserId(), friend.getUserId()));
    }

}