package java.com.alumnimanagmentsystem.Model;

import com.google.gson.annotations.SerializedName;

public class Poster {

    @SerializedName("type")
    private String type;

    @SerializedName("data")
    private int[] data;

    public String getType() {
        return type;
    }

    public int[] getData() {
        return data;
    }
}
