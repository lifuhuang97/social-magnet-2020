public class WelcomeMenuCtrl {
    public static String register(String username, String fullName, String password, String confirmedPassword) {
        String return_msg = null;
        if (!(password.equals(confirmedPassword))) {
            return_msg = "Please ensure that password and confirmed passwor1d match!";
        } else if (username.matches("^.*[^a-zA-Z0-9].*$")) { 
            return_msg = "Please ensure that your username only consists of alphanumerics!";
        } else if (UserProfileDAO.getUserProfileByUsername(username) != null) {
            return_msg = "Username already exists!";
        } else {
            boolean status = UserProfileDAO.createUser(username, fullName, password);
            if(status == true) {
                return_msg = username + ", your account is successfully created!";
            } else {
                return_msg = "An error occured while trying to create an account!";
            }
        }
        return return_msg;
    }

    public static UserProfile login(String username, String password) {
        UserProfile retrievedUser = UserProfileDAO.getUserProfileByUsername(username);
        if (password.equals(retrievedUser.getPassword())) {
            return retrievedUser;
        } else {
            return null;
        }
    }
}