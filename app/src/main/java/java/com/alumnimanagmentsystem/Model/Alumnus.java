package java.com.alumnimanagmentsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Alumnus implements Parcelable {

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

    protected Alumnus(Parcel in) {
        alumni_id = in.readInt();
        cnic = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        country = in.readString();
        pieasRegId = in.readString();
        email = in.readString();
        yearOfGrad = in.readString();
        degree = in.readParcelable(Alumnus.class.getClassLoader());
    }

    public Alumnus(String phoneNumber, String country) {
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public static final Creator<Alumnus> CREATOR = new Creator<Alumnus>() {
        @Override
        public Alumnus createFromParcel(Parcel in) {
            return new Alumnus(in);
        }

        @Override
        public Alumnus[] newArray(int size) {
            return new Alumnus[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(alumni_id);
        dest.writeString(cnic);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(country);
        dest.writeString(pieasRegId);
        dest.writeString(email);
        dest.writeString(yearOfGrad);
        dest.writeParcelable(degree, flags);
    }
}

