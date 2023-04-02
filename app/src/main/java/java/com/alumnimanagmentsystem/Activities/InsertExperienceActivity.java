package java.com.alumnimanagmentsystem.Activities;

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

import androidx.appcompat.app.AppCompatActivity;

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

public class InsertExperienceActivity extends AppCompatActivity {

    EditText startDate;
    EditText endDate;
    final Calendar myCalendar = Calendar.getInstance();
    CheckBox checkBox;
    EditText presentWorking;
    Boolean checked;
    Button saveButton;
    EditText jobTitle;
    EditText companyName;
    EditText jobDescriptionEditText;
    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_experience);

        startDate = findViewById(R.id.startDateEditText);
        endDate = findViewById(R.id.endDateEditText);
        checkBox = findViewById(R.id.checkbox);
        presentWorking = findViewById(R.id.endDatePresentEditText);
        saveButton = findViewById(R.id.saveButton);
        jobTitle = findViewById(R.id.jobTitleEditText);
        companyName = findViewById(R.id.companyTitleEditText);
        jobDescriptionEditText = findViewById(R.id.jobDescriptionEditText);
        context = this;

        checked = false;
        Context context = this;

        Calendar calendar = Calendar.getInstance();


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

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertExperienceActivity.this, startdate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertExperienceActivity.this, enddate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checked == false) {
                    presentWorking.setVisibility(View.VISIBLE);
                    endDate.setVisibility(View.INVISIBLE);
                    presentWorking.setText("Present");
                    checked = true;
                } else {
                    presentWorking.setVisibility(View.INVISIBLE);
                    endDate.setVisibility(View.VISIBLE);
                    checked = false;
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (companyName.getText().toString().isEmpty() == true || jobTitle.getText().toString().isEmpty() == true || startDate.getText().toString().isEmpty() == true || endDate.getText().toString().isEmpty() == true) {
                    Toast.makeText(context, "Information incomplete", Toast.LENGTH_SHORT).show();
                } else {

                    sendDataViaAPI();
                    SwitchToUserProfileActivity();
                    Animatoo.INSTANCE.animateSlideDown(context);
                }
            }
        });

    }

    private void updateLabelStartDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        startDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateLabelEndDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        endDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void SwitchToUserProfileActivity() {

        Intent switchActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(switchActivityIntent);
    }

    private void sendDataViaAPI(){

        String jobTitleString = jobTitle.getText().toString();
        String startDateString = startDate.getText().toString();
        String endDateString = endDate.getText().toString();
        String companyNameString = companyName.getText().toString();
        String jobDescriptionString = jobDescriptionEditText.getText().toString();

        AlumniJobHistories alumniJobHistories = new AlumniJobHistories(jobTitleString, startDateString, endDateString, companyNameString, jobDescriptionString);

        Call<AlumniJobHistories> call = RetrofitClient.getUserService().postJobHistroy(retrieveToken(),alumniJobHistories);
        call.enqueue(new Callback<AlumniJobHistories>() {
            @Override
            public void onResponse(Call<AlumniJobHistories> call, Response<AlumniJobHistories> response) {
                if(response.isSuccessful()){
                    Toast.makeText(InsertExperienceActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlumniJobHistories> call, Throwable t) {

            }
        });
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}