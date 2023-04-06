package java.com.alumnimanagmentsystem.Model;

public class Degree{
    public int degree_id;
    public String degree_name;
    public int department_id;
    public Department department;


    public int getDegree_id() {
        return degree_id;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public Department getDepartment() {
        return department;
    }
}
