package java.com.alumnimanagmentsystem.Model;

public class AlumniAchievements {
    int achievement_id;
    String type;
    String title;
    String description;
    int alumni_id;

    public AlumniAchievements(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getAlumni_id() {
        return alumni_id;
    }

    public void setAlumni_id(int alumni_id) {
        this.alumni_id = alumni_id;
    }
}
