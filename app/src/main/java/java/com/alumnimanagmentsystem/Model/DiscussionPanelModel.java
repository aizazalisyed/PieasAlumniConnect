package java.com.alumnimanagmentsystem.Model;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.List;

public class DiscussionPanelModel {
    private String name;
    private String degree;
    private String caption;
    List<SlideModel> images;
    String commentCount;
    int dpID;

    public DiscussionPanelModel(String name, String degree, String caption, List<SlideModel> images, String commentCount, int dpID) {
        this.name = name;
        this.degree = degree;
        this.caption = caption;
        this.images = images;
        this.commentCount = commentCount;
        this.dpID = dpID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<SlideModel> getImages() {
        return images;
    }

    public void setImages(List<SlideModel> images) {
        this.images = images;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public int getDpID() {
        return dpID;
    }

    public void setDpID(int dpID) {
        this.dpID = dpID;
    }
}