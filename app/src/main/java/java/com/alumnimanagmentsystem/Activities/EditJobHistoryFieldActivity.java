package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditJobHistoryFieldActivity extends AppCompatActivity {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

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


        AlumniJobHistories alumniJobHistories = new AlumniJobHistories(jobTitle.getText().toString(),
                fromDate.getText().toString(), toDate.getText().toString(), companyName.getText().toString(), jobDescription.getText().toString());

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
}