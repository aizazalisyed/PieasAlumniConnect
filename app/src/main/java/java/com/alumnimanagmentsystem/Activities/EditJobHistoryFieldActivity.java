package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditJobHistoryFieldActivity extends AppCompatActivity {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    CheckBox checkBox;
    final Calendar myCalendar = Calendar.getInstance();
    String jobTitleString, jobDescriptionString,
            companyNameString,
            fromDateString,
            toDateString;

    int jobHistoryID;

    EditText jobTitle,
            jobDescription,
            companyName,
            fromDate,
            toDate;
    Button saveButton;
    Boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_history_field);

        jobTitle = findViewById(R.id.jobTitleEditText);
        jobDescription = findViewById(R.id.jobDescriptionEditText);
        companyName = findViewById(R.id.companyTitleEditText);
        fromDate = findViewById(R.id.startDateEditText);
        toDate = findViewById(R.id.endDateEditText);
        saveButton = findViewById(R.id.saveButton);
        checkBox = findViewById(R.id.checkbox);

        checked = false;
        Context context = this;
        Bundle extras = getIntent().getExtras();

        jobTitleString = extras.getString("jobTitle");
        jobDescriptionString = extras.getString("jobDescription");
        companyNameString = extras.getString("companyName");
        fromDateString = extras.getString("fromDate");
        toDateString = extras.getString("toDate");
        jobHistoryID = extras.getInt("jobHistoryID");


        jobTitle.setText(jobTitleString);
        jobDescription.setText(jobDescriptionString);
        companyName.setText(companyNameString);
        fromDate.setText(fromDateString);
        toDate.setText(toDateString);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checked) {
                    toDate.setText("Present");
                    checked = true;
                } else {

                   toDate.setText("");
                    checked = false;
                }
            }
        });

        DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEndDate();
            }
        };

        DatePickerDialog.OnDateSetListener startdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStartDate();
            }
        };

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditJobHistoryFieldActivity.this, startdate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditJobHistoryFieldActivity.this, enddate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (companyName.getText().toString().isEmpty() == true || jobTitle.getText().toString().isEmpty() == true || fromDate.getText().toString().isEmpty() == true || toDate.getText().toString().isEmpty() == true) {
                    Toast.makeText(EditJobHistoryFieldActivity.this, "Information incomplete", Toast.LENGTH_SHORT).show();
                } else {
                    MakeApiCall();
                }

            }
        });


    }

    public void MakeApiCall() {

        String jobTitleString = jobTitle.getText().toString();
        String startDateString = fromDate.getText().toString();
        String endDateString = toDate.getText().toString();
        String companyNameString = companyName.getText().toString();
        String jobDescriptionString = jobDescription.getText().toString();

        AlumniJobHistories alumniJobHistories;
        if(endDateString.equals("Present")){
            alumniJobHistories  = new AlumniJobHistories(jobTitleString, startDateString, null, companyNameString, jobDescriptionString);
        }
        else {
            alumniJobHistories  = new AlumniJobHistories(jobTitleString, startDateString, endDateString, companyNameString, jobDescriptionString);
        }

        Call<AlumniJobHistories> call = RetrofitClient.getUserService().patchJobHistories(retrieveToken(), alumniJobHistories, jobHistoryID);
        call.enqueue(new Callback<AlumniJobHistories>() {
            @Override
            public void onResponse(Call<AlumniJobHistories> call, Response<AlumniJobHistories> response) {

                SwitchToUserProfileActivity();
                Toast.makeText(EditJobHistoryFieldActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AlumniJobHistories> call, Throwable t) {

            }
        });

    }

    public String retrieveToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    private void SwitchToJobHistoryEditActivity(){
        Intent switchActivityIntent = new Intent(this, EditJobHistoryActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SwitchToJobHistoryEditActivity();
    }
    private void SwitchToUserProfileActivity(){
        Intent switchActivityIntent = new Intent(EditJobHistoryFieldActivity.this, UserProfileActivity.class);
        startActivity(switchActivityIntent);
    }

    private void updateLabelStartDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        fromDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateLabelEndDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        toDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}