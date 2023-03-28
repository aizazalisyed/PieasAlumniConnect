package java.com.alumnimanagmentsystem.Model;

public class AlumniJobHistories {

    int job_history_id;
    String job_title;
    String job_start_date;
    String job_end_date;
    String company_name;
    String description;
    int alumni_id;

    public int getJob_history_id() {
        return job_history_id;
    }

    public void setJob_history_id(int job_history_id) {
        this.job_history_id = job_history_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_start_date() {
        return job_start_date;
    }

    public void setJob_start_date(String job_start_date) {
        this.job_start_date = job_start_date;
    }

    public String getJob_end_date() {
        return job_end_date;
    }

    public void setJob_end_date(String job_end_date) {
        this.job_end_date = job_end_date;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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
