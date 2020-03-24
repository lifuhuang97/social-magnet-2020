public class WelcomeMenuCtrl {
    
    public static String register(String username, String fullName, String password, String confirmedPassword) {
        /*
            For now, this function returns the string that is to be displayed to the user when he/she registers (includes error messages). 

            TODO: Remove display strings and throw an exception instead 
        */

        String return_msg = null;
        if (!(password.equals(confirmedPassword))) {

            return_msg = "Please ensure that password and confirmed passwor1d match!";

        } else if (username.matches("^.*[^a-zA-Z0-9].*$")) { 

            return_msg = "Please ensure that your username only consists of alphanumerics!";

        } else if (UserProfileDAO.getUserProfileByUsername(username) != null) {

            return_msg = "Username already exists!";

        } else {

            if(UserProfileDAO.createUser(username, fullName, password)) {
                return_msg = username + ", your account is successfully created!";
            } else {
                return_msg = "An error occured while trying to create an account!";
            }
            
        }

        return return_msg;
    }

    public static UserProfile login(String username, String password) {
        UserProfile retrievedUser = UserProfileDAO.getUserProfileByUsername(username);
        if (retrievedUser == null || !(password.equals(retrievedUser.getPassword()))) {
            return null;
        } else {
            return retrievedUser;
        }
    }
}