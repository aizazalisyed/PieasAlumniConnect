package java.com.alumnimanagmentsystem.Model;

import java.util.ArrayList;

public class PostJobModel {


    public String job_title;
    public String last_date_to_apply;
    public String sector;
    public String location;
    public String company_name;
    public String gender;
    public String experience;
    public String employment_type;
    public String description;
    public String salary;
    public String required_gpa;;
    public ArrayList<Integer> eligibility;

    public PostJobModel(String job_title, String last_date_to_apply, String sector, String location, String company_name, String gender, String experience, String employment_type, String description, String salary, String required_gpa, ArrayList<Integer> eligibility) {
        this.job_title = job_title;
        this.last_date_to_apply = last_date_to_apply;
        this.sector = sector;
        this.location = location;
        this.company_name = company_name;
        this.gender = gender;
        this.experience = experience;
        this.employment_type = employment_type;
        this.description = description;
        this.salary = salary;
        this.required_gpa = required_gpa;
        this.eligibility = eligibility;
    }
}
