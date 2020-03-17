public class Comment {
    private int commentID;
    private String content;
    private SMDate date;
    
    public Comment (int commentID, String content, SMDate date) {
        this.commentID = commentID;
        this.content = content;
        this.date = date;
    }

    public int getCommentID() {
        return commentID;
    }

    public String getContent () {
        return content;
    }

    public SMDate getDate () {
        return date;
    }
}