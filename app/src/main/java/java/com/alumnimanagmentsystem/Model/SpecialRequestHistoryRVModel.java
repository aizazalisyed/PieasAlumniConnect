package java.com.alumnimanagmentsystem.Model;

public class SpecialRequestHistoryRVModel {

    String title;
    String description;

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

    public SpecialRequestHistoryRVModel(String title, String description) {
        this.title = title;
        this.description = description;
    }


}
