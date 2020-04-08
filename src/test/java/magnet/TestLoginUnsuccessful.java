package project.magnet;

/**
 * Test Login Unsuccesful 
 */

import org.junit.jupiter.api.*;
import java.util.*;

import project.utilities.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLoginUnsuccessful {

    @Test
    public void testUnsuccessfulRegister() {
        
        UserProfile user = WelcomeMenuCtrl.login("idonotexist", "unknownpassword");
        if (user != null) {
            fail("Should not have been logged in");
        } else {
            assertTrue(true);
        }
    }

}