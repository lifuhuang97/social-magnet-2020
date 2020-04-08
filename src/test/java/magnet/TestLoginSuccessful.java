package project.magnet;

/**
 * Test Login Successful 
 */

import org.junit.jupiter.api.*;
import java.util.*;

import project.utilities.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLoginSuccessful {

    @Test
    public void testUnsuccessfulRegister() {
        
        UserProfile user = WelcomeMenuCtrl.login("hackerbob", "letmein");
        if (user != null) {
            assertTrue(true);
        } else {
            fail("Should have been logged in");
        }
    }

}