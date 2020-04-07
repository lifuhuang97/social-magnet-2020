// package main.java;

public class Comment {
    private int commentID;
    private String content;
    private SMDate date;
    
    public Comment (int commentID, String content, SMDate date) {
        this.commentID = commentID;
        this.content = content;
        this.date = date;
    }

    public int getCommentId() {
        return commentID;
    }

    public String getContent () {
        return content;
    }

    public SMDate getDateTime () {
        return date;
    }
}