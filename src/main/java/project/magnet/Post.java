package project.magnet;
import project.utilities.*;

/**
 * This is the Post class
 */

public class Post {
    public int postId;
    public SMDate dateTime;
    public String content;

    /**
     * Constructs a specified Profile object
     * @param postId the PostID of the post
     * @param content  the content of the post
     * @param dateTime the date that the post was created on
     */
    public Post (int postId, String content, SMDate dateTime) {
        this.postId = postId;
        this.dateTime = dateTime;
        this.content = content;
    }

    /**
     * Gets the PostID
     * @return the PostID
     */
    public int getPostId () {
        return postId;
    }

    /**
     * Gets the dateTime post was created on
     * @return the dateTime
     */
    public SMDate getDateTime () {
        return dateTime;
    }

    /**
     * Gets the content of post
     * @return the content
     */
    public String getContent () {
        return content;
    }
}