package java.com.alumnimanagmentsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Degree implements Parcelable {
    public int degree_id;
    public String degree_name;
    public int department_id;
    public Department department;


    protected Degree(Parcel in) {
        degree_id = in.readInt();
        degree_name = in.readString();
        department_id = in.readInt();
        department = in.readParcelable(Alumnus.class.getClassLoader());
    }

    public static final Creator<Degree> CREATOR = new Creator<Degree>() {
        @Override
        public Degree createFromParcel(Parcel in) {
            return new Degree(in);
        }

        @Override
        public Degree[] newArray(int size) {
            return new Degree[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(degree_id);
        dest.writeString(degree_name);
        dest.writeInt(department_id);
        dest.writeParcelable(department, flags);
    }
}
