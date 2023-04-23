package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.R;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateJob extends AppCompatActivity {


    EditText AchievementTitleEditText, jobLocationEditText, jobSectorEditText
            ,jobCompanyNameEditText, jobSalaryEditText, jobCreatedOnEditText
            ,jobLastDateToApplyEditText, jobDescriptionEditText;

    Spinner spinnerGender, spinnerExperience, spinnerEmploymentType, spinnerRequiredGpa;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerExperience = findViewById(R.id.spinnerExperience);
        spinnerEmploymentType = findViewById(R.id.spinnerEmploymentType);
        spinnerRequiredGpa = findViewById(R.id.spinnerRequiredGpa);
        jobCreatedOnEditText = findViewById(R.id.jobCreatedOnEditText);
        jobLastDateToApplyEditText = findViewById(R.id.jobLastDateToApplyEditText);


        List<String> genderList = Arrays.asList(getResources().getStringArray(R.array.genderOptions));
        List<String> experienceList = Arrays.asList(getResources().getStringArray(R.array.experienceOptions));
        List<String> employmentTypeList = Arrays.asList(getResources().getStringArray(R.array.employmentTypeOptions));
        List<String>requiredGpaList = Arrays.asList(getResources().getStringArray(R.array.requiredGpaOptions));

        ArrayAdapter<CharSequence> adapterGender = new ArrayAdapter(this, android.R.layout.simple_spinner_item,genderList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

               View view = returnView(position,getContext(),parent,super.getDropDownView(position, null, parent));
               return view;
            }
        };
        ArrayAdapter<CharSequence> adapterExperience = new ArrayAdapter(this, android.R.layout.simple_spinner_item,experienceList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position,getContext(),parent,super.getDropDownView(position, null, parent));
                return view;
            }
        };
        ArrayAdapter<CharSequence> adapterEmploymentType = new ArrayAdapter(this, android.R.layout.simple_spinner_item,employmentTypeList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position,getContext(),parent,super.getDropDownView(position, null, parent));
                return view;
            }
        };
        ArrayAdapter<CharSequence> adapterRequiredGpa = new ArrayAdapter(this, android.R.layout.simple_spinner_item,requiredGpaList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position,getContext(),parent,super.getDropDownView(position, null, parent));
                return view;
            }
        };

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterExperience.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEmploymentType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterRequiredGpa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGender.setAdapter(adapterGender);
        spinnerExperience.setAdapter(adapterExperience);
        spinnerEmploymentType.setAdapter(adapterEmploymentType);
        spinnerRequiredGpa.setAdapter(adapterRequiredGpa);

        DatePickerDialog.OnDateSetListener enddate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelJobLastDateToApplyEditText();
            }
        };

        DatePickerDialog.OnDateSetListener startdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelJobCreatedOnEditText();
            }
        };

        jobCreatedOnEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateJob.this, startdate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
       jobLastDateToApplyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateJob.this, enddate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private View returnView(int position, Context context,ViewGroup parent, View superView){

        View v = null;
        if (position == 0) {
            TextView tv = new TextView(context);
            tv.setVisibility(View.GONE);
            v = tv;
        } else {
            v = superView;
        }
        return v;
    }

    private void updateLabelJobCreatedOnEditText() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        jobCreatedOnEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateLabelJobLastDateToApplyEditText(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        jobLastDateToApplyEditText.setText(dateFormat.format(myCalendar.getTime()));
    }
}