
public class Post {
    public int userID;
    public SMDate dateTime;
    public String content;
    public int likes;
    public int dislikes;

    public Post (int userID, SMDate dateTime, String content, int likes, int dislikes) {
        this.userID = userID;
        this.dateTime = dateTime;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getUserID () {
        return userID;
    }

    public SMDate getDateTime () {
        return dateTime;
    }

    public String getContent () {
        return content;
    }

    public int getLikes () {
        return likes;
    }

    public int getDislikes () {
        return dislikes;
    }
}