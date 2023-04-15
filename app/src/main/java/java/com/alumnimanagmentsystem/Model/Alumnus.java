package java.com.alumnimanagmentsystem.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    AlumniJobHistories[] alumni_job_histories;
    AlumniAchievements[] alumni_achievements;
    Degree degree;
    ProfilePicture profilePicture;

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public Degree getDegree() {
        return degree;
    }

    public int getAlumni_id() {
        return alumni_id;
    }

    public String getCnic() {
        return cnic;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getPieasRegId() {
        return pieasRegId;
    }

    public String getEmail() {
        return email;
    }

    public String getYearOfGrad() {
        return yearOfGrad;
    }

    public AlumniJobHistories[] getAlumni_job_histories() {
        return alumni_job_histories;
    }

    public AlumniAchievements[] getAlumni_achievements() {
        return alumni_achievements;
    }
}

