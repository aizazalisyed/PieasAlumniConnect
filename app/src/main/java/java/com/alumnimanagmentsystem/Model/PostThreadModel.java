package java.com.alumnimanagmentsystem.Model;

public class PostThreadModel {

    public int thread_id;
    public String content;
    public String status;
    public String created_on;
    public int post_id;
    public Integer creater_id_alumni;
    public Integer creater_id_operator;
    public Alumnus alumni;

    public int getThread_id() {
        return thread_id;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_on() {
        return created_on;
    }

    public int getPost_id() {
        return post_id;
    }

    public Integer getCreater_id_alumni() {
        return creater_id_alumni;
    }

    public Integer getCreater_id_operator() {
        return creater_id_operator;
    }

    public Alumnus getAlumni() {
        return alumni;
    }
}
