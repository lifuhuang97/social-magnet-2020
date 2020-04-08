
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UtilityTest {
    @Test
    public void testRed() {
        System.out.println("XXX");
        assertEquals("Red", Utility.getColor('R'));
    }

    @Test
    public void testBlue() {
        assertEquals("Blue", Utility.getColor('B'));          
    }


    @Test
    public void testGreen() {
        assertEquals("Green", Utility.getColor('G'));          
    }    
    
    @Test
    public void testInvalid() {
        assertEquals("Invalid", Utility.getColor(' '));          
    }
}