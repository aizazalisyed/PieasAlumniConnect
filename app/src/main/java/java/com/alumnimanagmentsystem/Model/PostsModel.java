package java.com.alumnimanagmentsystem.Model;

import java.util.ArrayList;

public class PostsModel {

    public int post_id;
    public String title;
    public String content;
    public String status;
    public String created_on;
    public int creater_id_alumni;
    public Integer creater_id_operator;
    public Alumnus alumni;
    public ArrayList<PostThreadModel> threads;

    public int getPost_id() {
        return post_id;
    }

    public String getTitle() {
        return title;
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

    public int getCreater_id_alumni() {
        return creater_id_alumni;
    }

    public Integer getCreater_id_operator() {
        return creater_id_operator;
    }

    public Alumnus getAlumni() {
        return alumni;
    }

    public ArrayList<PostThreadModel> getThreads() {
        return threads;
    }
}
