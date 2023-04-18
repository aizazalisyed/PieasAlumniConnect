package java.com.alumnimanagmentsystem.Model;

public class JobModel {
    public int job_post_id;
    public String job_title;
    public String last_date_to_apply;
    public String sector;
    public String location;
    public String created_on;
    public String company_name;
    public String gender;
    public String experience;
    public String employment_type;
    public String description;
    public String salary;
    public String required_gpa;
    public Object creater_id_alumni;
    public int creater_id_operator;
    public Object alumni;
    public EligibilityDiscipline[] eligibility_disciplines;

    public int getJob_post_id() {
        return job_post_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getLast_date_to_apply() {
        return last_date_to_apply;
    }

    public String getSector() {
        return sector;
    }

    public String getLocation() {
        return location;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getGender() {
        return gender;
    }

    public String getExperience() {
        return experience;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public String getDescription() {
        return description;
    }

    public String getSalary() {
        return salary;
    }

    public String getRequired_gpa() {
        return required_gpa;
    }

    public Object getCreater_id_alumni() {
        return creater_id_alumni;
    }

    public int getCreater_id_operator() {
        return creater_id_operator;
    }

    public Object getAlumni() {
        return alumni;
    }

    public EligibilityDiscipline[] getEligibility_disciplines() {
        return eligibility_disciplines;
    }
}
