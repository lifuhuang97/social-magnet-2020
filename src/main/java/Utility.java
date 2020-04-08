
public class Utility {

    public static String getColor(char ch) {
        if (ch == 'R') {
            return "Red";
        }
        
        if (ch == 'G') {
            return "Green";
        }

        if (ch == 'B') {
            return "Blue";
        }

        return "Invalid";
    }

}