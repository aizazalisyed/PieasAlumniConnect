package java.com.alumnimanagmentsystem.Model;

public class JobHistoryModel {
    String title, companyName, description;

    public JobHistoryModel(String title, String companyName, String description) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
