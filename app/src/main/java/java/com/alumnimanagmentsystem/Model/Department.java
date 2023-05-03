package java.com.alumnimanagmentsystem.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Department implements Parcelable {
    public int department_id;
    public String department_name;


    protected Department(Parcel in) {
        department_id = in.readInt();
        department_name = in.readString();
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

    public int getDepartment_id() {
        return department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(department_id);
        dest.writeString(department_name);
    }
}
