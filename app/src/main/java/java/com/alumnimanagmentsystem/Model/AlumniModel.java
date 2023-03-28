package java.com.alumnimanagmentsystem.Model;

public class AlumniModel {

    private int imageID;
    private String alumniName;
    private String alumniDepartment;

    public AlumniModel(int imageID, String alumniName, String alumniDepartment) {
        this.imageID = imageID;
        this.alumniName = alumniName;
        this.alumniDepartment = alumniDepartment;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getAlumniDepartment() {
        return alumniDepartment;
    }

    public void setAlumniDepartment(String alumniDepartment) {
        this.alumniDepartment = alumniDepartment;
    }
}
