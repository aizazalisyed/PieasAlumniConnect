package java.com.alumnimanagmentsystem.Model;

public class CommentActivityRVModel {
    int ImageID;
    String userName;
    String degree;
    String comment;

    public CommentActivityRVModel(int imageID, String userName, String degree, String comment) {
        ImageID = imageID;
        this.userName = userName;
        this.degree = degree;
        this.comment = comment;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
