package project.magnet;

/**
 * Test Register Unsuccessful
 */

import org.junit.jupiter.api.*;
import java.util.*;

import project.utilities.*;
import project.exception.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestRegisterUnsuccessful {

    @Test
    public void testUnsuccessfulRegister() {
        try {
            WelcomeMenuCtrl.register("speci@lch@r@cters", "specialcharacters", "password", "password");
            fail("Should have thrown an exception");
        } catch (RegisterException e) {
            assertTrue(true);
        }
    }

}