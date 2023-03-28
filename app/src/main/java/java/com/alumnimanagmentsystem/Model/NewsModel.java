package java.com.alumnimanagmentsystem.Model;

public class NewsModel {

    String title, description;
    int imageView;

    public NewsModel(String title, String description, int imageView) {
        this.title = title;
        this.description = description;
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
