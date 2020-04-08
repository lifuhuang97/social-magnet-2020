package project.magnet;

import project.utilities.*;
import project.exception.*;

public class WelcomeMenuCtrl {
    
    /**
     * Registers a new user
     * @param username the Username of the User
     * @param fullName  the Full Name of the User
     * @param password the Password of the User
     * @param confirmedPassword the Confirmed Password of the User
     */
    public static void register(String username, String fullName, String password, String confirmedPassword) {

        if (!(password.equals(confirmedPassword))) {

            throw new RegisterException("Please ensure that password and confirmed password match!");

        } else if (!username.matches("^[a-zA-Z0-9]*$")) { 

            throw new RegisterException("Please ensure that your username only consists of alphanumerics!");

        } else if (UserProfileDAO.getUserProfileByUsername(username) != null) {

            throw new RegisterException("Username already exists!");

        } else {

            UserProfileDAO.createUser(username, fullName, password);
            
        }

    }

    /**
     * Authenticates and logs a user in
     * @param username the Username of the User
     * @param password the Password of the User
     * @return UserProfile object
     */
    public static UserProfile login(String username, String password) {
        UserProfile retrievedUser = UserProfileDAO.getUserProfileByUsername(username);

        
        if (retrievedUser == null || !(password.equals(retrievedUser.getPassword()))) {
            return null;
        } else {
            return retrievedUser;
        }
    }
}