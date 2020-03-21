
public class Post {
    public int postId;
    public SMDate dateTime;
    public String content;

    public Post (int postId, String content, SMDate dateTime) {
        this.postId = postId;
        this.dateTime = dateTime;
        this.content = content;
    }

    public int getPostId () {
        return postId;
    }

    public SMDate getDateTime () {
        return dateTime;
    }

    public String getContent () {
        return content;
    }
}