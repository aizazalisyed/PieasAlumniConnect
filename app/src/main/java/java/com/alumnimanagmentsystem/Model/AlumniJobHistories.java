package java.com.alumnimanagmentsystem.Model;

public class AlumniJobHistories {

    int job_history_id;
    String job_title;
    String job_start_date;
    String job_end_date;
    String company_name;
    String description;
    int alumni_id;

    public AlumniJobHistories(String job_title, String job_start_date, String job_end_date, String company_name, String description) {
        this.job_title = job_title;
        this.job_start_date = job_start_date;
        this.job_end_date = job_end_date;
        this.company_name = company_name;
        this.description = description;
    }

    public int getJob_history_id() {
        return job_history_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getJob_start_date() {
        return job_start_date;
    }

    public String getJob_end_date() {
        return job_end_date;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getDescription() {
        return description;
    }

    public int getAlumni_id() {
        return alumni_id;
    }
}
