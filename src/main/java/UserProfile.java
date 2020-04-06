package main.java;

public class UserProfile {
    private int userId;
    private String fullName;
    private String username;
    private String password;
    private Rank rank;
    private int xp;
    private int gold;

    /**
     * Constructs a specified Profile object
     * @param userId the UserID of the User
     * @param fullName  the Full Name of the User
     * @param username the Username of the User
     * @param password the Password of the User
     * @param rank the Rank of the User
     * @param xp the XP of the User
     * @param gold the Gold of the User
     */
    public UserProfile(int userId, String fullName, String username, String password, Rank rank, int xp, int gold) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.rank = rank;
        this.xp = xp;
        this.gold = gold;
    }

    /**
     * Gets the UserID
     * @return the UserID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the Full Name
     * @return the Full Name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Gets the Username
     * @return the Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the Password
     * @return the Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the Rank
     * @return the Rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Gets the XP
     * @return the XP
     */
    public int getXp() {
        return xp;
    }

    /**
     * Gets the gold
     * @return the gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets userId
     * @param userId the UserID of the User
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

     /**
     * Sets fullName
     * @param fullName the Full Name of the User
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

     /**
     * Sets username
     * @param username the Username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }

     /**
     * Sets password
     * @param password the Password of the User
     */
    public void setPassword(String password) {
        this.password = password;
    }

     /**
     * Sets rank
     * @param rank the rank of the user
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

     /**
     * Sets xp
     * @param xp the xp of the user
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

     /**
     * Sets gold
     * @param gold the gold of the user
     */
    public void setGold(int gold) {
        this.gold = gold;
    }
}