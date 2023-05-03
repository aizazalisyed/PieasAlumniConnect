package java.com.alumnimanagmentsystem.Model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class PostThreadModel implements Parcelable {

    public int thread_id;
    public String content;
    public String status;
    public String created_on;
    public int post_id;
    public Integer creater_id_alumni;
    public Integer creater_id_operator;
    public Alumnus alumni;

    protected PostThreadModel(Parcel in) {
        thread_id = in.readInt();
        content = in.readString();
        status = in.readString();
        created_on = in.readString();
        post_id = in.readInt();
        if (in.readByte() == 0) {
            creater_id_alumni = null;
        } else {
            creater_id_alumni = in.readInt();
        }
        if (in.readByte() == 0) {
            creater_id_operator = null;
        } else {
            creater_id_operator = in.readInt();
        }
        alumni = in.readParcelable(Alumnus.class.getClassLoader());
    }

    public static final Creator<PostThreadModel> CREATOR = new Creator<PostThreadModel>() {
        @Override
        public PostThreadModel createFromParcel(Parcel in) {
            return new PostThreadModel(in);
        }

        @Override
        public PostThreadModel[] newArray(int size) {
            return new PostThreadModel[size];
        }
    };

    public int getThread_id() {
        return thread_id;
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

    public int getPost_id() {
        return post_id;
    }

    public Integer getCreater_id_alumni() {
        return creater_id_alumni;
    }

    public Integer getCreater_id_operator() {
        return creater_id_operator;
    }

    public Alumnus getAlumni() {
        return alumni;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(thread_id);
        dest.writeString(content);
        dest.writeString(status);
        dest.writeString(created_on);
        dest.writeInt(post_id);
        if (creater_id_alumni == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creater_id_alumni);
        }
        if (creater_id_operator == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creater_id_operator);
        }
        dest.writeParcelable((Parcelable) alumni, flags);
    }
}
