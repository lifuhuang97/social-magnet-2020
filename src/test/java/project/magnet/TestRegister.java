package project.magnet;

/**
 * Test Login
 */

import org.junit.jupiter.api.*;
import java.util.*;

import SocialMagnet.Social.Member.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestRegister {
    private MemberDAO memberDAO = new MemberDAO();
    private LoginCtrl ctrl = new LoginCtrl();
    
    @BeforeAll
    public void resetUser() {
        Member testUser = memberDAO.getUser("tester");
        if (testUser != null) {
            memberDAO.deleteUser("tester");
        }
        memberDAO.addUser("tester", "password", "Tester");
    }

    @Test
    public void testSuccessfulLogin() {
        Member user = ctrl.userLogin("tester", "password");
        Assertions.assertEquals("tester", user.getUserID());
    }

    @Test
    public void testUnsuccessfulLogin() {
        Member user = ctrl.userLogin("tester", "idkthepass");
        Assertions.assertEquals(null, user);
    }

    @AfterAll
    public void removeUser() {
        Member testUser = memberDAO.getUser("tester");
        if (testUser != null) {
            memberDAO.deleteUser("tester");
        }   
    }

}