package project.magnet;

/**
 * Test Register Successful
 */

import org.junit.jupiter.api.*;
import java.util.*;

import project.utilities.*;
import project.exception.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestRegisterSuccessful {

    @Test
    public void testUnsuccessfulRegister() {
        try {
            WelcomeMenuCtrl.register("Bob TAN", "hackerbob", "letmein", "letmein");
            assertTrue(true);
        } catch (RegisterException e) {
            fail("Should not have thrown an exception");
        }
    }

}