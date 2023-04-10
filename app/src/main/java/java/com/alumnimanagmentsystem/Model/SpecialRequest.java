package java.com.alumnimanagmentsystem.Model;

public class SpecialRequest {

    public int special_request_id;
    public String timeOfRequest;
    public String subject;
    public String description;
    public int alumni_id;


    public SpecialRequest(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public int getSpecial_request_id() {
        return special_request_id;
    }

    public String getTimeOfRequest() {
        return timeOfRequest;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public int getAlumni_id() {
        return alumni_id;
    }
}
