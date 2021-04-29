package cat.itb.redditapp.model;

public class Comment {
    private String commentId;
    private String picture;
    private String user;
    private String fecha;
    private String contentText;
    private int votes;

    public Comment(String commentId, String picture, String user, String fecha, String contentText, int votes) {
        this.commentId = commentId;
        this.picture = picture;
        this.user = user;
        this.fecha = fecha;
        this.contentText = contentText;
        this.votes = votes;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}