package java.com.alumnimanagmentsystem.Model;

import com.google.gson.annotations.SerializedName;

public class Alumnus {

    int alumni_id;
    String cnic;
    @SerializedName("name")
    String name;
    String phoneNumber;
    String country;
    String pieasRegId;
    String email;
    String yearOfGrad;
    AlumniJobHistories [] alumni_job_histories;
    AlumniAchievements [] alumni_achievements;

    public int getAlumni_id() {
        return alumni_id;
    }

    public void setAlumni_id(int alumni_id) {
        this.alumni_id = alumni_id;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPieasRegId() {
        return pieasRegId;
    }

    public void setPieasRegId(String pieasRegId) {
        this.pieasRegId = pieasRegId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYearOfGrad() {
        return yearOfGrad;
    }

    public void setYearOfGrad(String yearOfGrad) {
        this.yearOfGrad = yearOfGrad;
    }

    public AlumniJobHistories[] getAlumni_job_histories() {
        return alumni_job_histories;
    }

    public void setAlumni_job_histories(AlumniJobHistories[] alumni_job_histories) {
        this.alumni_job_histories = alumni_job_histories;
    }

    public AlumniAchievements[] getAlumni_achievements() {
        return alumni_achievements;
    }

    public void setAlumni_achievements(AlumniAchievements[] alumni_achievements) {
        this.alumni_achievements = alumni_achievements;
    }
}

