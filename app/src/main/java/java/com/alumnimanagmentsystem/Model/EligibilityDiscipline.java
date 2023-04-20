package java.com.alumnimanagmentsystem.Model;

public class EligibilityDiscipline {

    public int discipline_id;
    public String name;
    public JobEligibility job_eligibility;

    public JobEligibility getJob_eligibility() {
        return job_eligibility;
    }

    public int getDiscipline_id() {
        return discipline_id;
    }

    public String getName() {
        return name;
    }
}
