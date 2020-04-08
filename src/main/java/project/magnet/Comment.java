package project.magnet;

import project.utilities.*;

/**
 * This is the Comment class
 */

public class Comment {
    private int commentId;
    private String content;
    private SMDate date;
    
    /**
     * Constructs a specified Comment object
     * @param commentId the CommentID of the Comment
     * @param content  the content of the Comment
     * @param date the date that the Comment was made on
     */
    public Comment (int commentId, String content, SMDate date) {
        this.commentId = commentId;
        this.content = content;
        this.date = date;
    }

    /**
     * Gets the commentId
     * @return the commentId
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Gets the content
     * @return the content
     */
    public String getContent () {
        return content;
    }

     /**
     * Gets the date
     * @return the date
     */
    public SMDate getDateTime () {
        return date;
    }
}