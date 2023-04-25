package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.EligibilityDiscipline;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.Model.PostJobModel;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.ViewModel.EligibilityDisciplineViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateJob extends AppCompatActivity {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    boolean[] checkedItems;
    CharSequence[] items;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    EditText jobTitleEditText, jobLocationEditText, jobSectorEditText, jobCompanyNameEditText, jobSalaryEditText, jobLastDateToApplyEditText, jobDescriptionEditText, eligibilityDisciplinesEditText;
    Spinner spinnerGender, spinnerExperience, spinnerEmploymentType, spinnerRequiredGpa;
    final Calendar myCalendar = Calendar.getInstance();
    EligibilityDisciplineViewModel viewModel;
    Button saveButton;
    ArrayList<Integer> selectedDisciplineIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerExperience = findViewById(R.id.spinnerExperience);
        spinnerEmploymentType = findViewById(R.id.spinnerEmploymentType);
        spinnerRequiredGpa = findViewById(R.id.spinnerRequiredGpa);
        jobLastDateToApplyEditText = findViewById(R.id.jobLastDateToApplyEditText);
        eligibilityDisciplinesEditText = findViewById(R.id.spinnerEligibilityDisciplines);
        saveButton = findViewById(R.id.saveButton);
        jobTitleEditText = findViewById(R.id.jobTitleEditText);
        jobLocationEditText = findViewById(R.id.jobLocationEditText);
        jobSectorEditText = findViewById(R.id.jobSectorEditText);
        jobCompanyNameEditText = findViewById(R.id.jobCompanyNameEditText);
        jobSalaryEditText = findViewById(R.id.jobSalaryEditText);
        jobDescriptionEditText = findViewById(R.id.jobDescriptionEditText);



        viewModel = ViewModelProviders.of(this).get(EligibilityDisciplineViewModel.class);

        viewModel.getEligibilityDisciplines().observe(this, new Observer<List<EligibilityDiscipline>>() {
            @Override
            public void onChanged(List<EligibilityDiscipline> eligibilityDisciplines) {
                String size = String.valueOf(eligibilityDisciplines.size());
                items = new CharSequence[eligibilityDisciplines.size()];
                checkedItems = new boolean[eligibilityDisciplines.size()];
                for (int i = 0; i < eligibilityDisciplines.size(); i++) {
                    items[i] = eligibilityDisciplines.get(i).getName();
                }


                eligibilityDisciplinesEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreateJob.this);
                        mBuilder.setTitle("Select Eligibility Disciplines");
                        mBuilder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                                if (isChecked) {
                                    if (!mUserItems.contains(position)) {
                                        mUserItems.add(position);
                                        selectedDisciplineIds.add(eligibilityDisciplines.get(position).getDiscipline_id());
                                    }

                                }
                                else {
                                    mUserItems.remove(mUserItems.indexOf(position));
                                    selectedDisciplineIds.remove((Integer) eligibilityDisciplines.get(position).getDiscipline_id());
                                }
                            }
                        });

                        mBuilder.setCancelable(false);
                        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String item = "";
                                for (int i = 0; i < mUserItems.size(); i++) {
                                    item += items[mUserItems.get(i)];
                                    if (i != mUserItems.size() - 1) {
                                        item = item + ", ";
                                    }
                                }

                                eligibilityDisciplinesEditText.setText(item);
                            }
                        });
                        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });
            }
        });


        List<String> genderList = Arrays.asList(getResources().getStringArray(R.array.genderOptions));
        List<String> experienceList = Arrays.asList(getResources().getStringArray(R.array.experienceOptions));
        List<String> employmentTypeList = Arrays.asList(getResources().getStringArray(R.array.employmentTypeOptions));
        List<String> requiredGpaList = Arrays.asList(getResources().getStringArray(R.array.requiredGpaOptions));

        ArrayAdapter<CharSequence> adapterGender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position, getContext(), parent, super.getDropDownView(position, null, parent));
                return view;
            }
        };
        ArrayAdapter<CharSequence> adapterExperience = new ArrayAdapter(this, android.R.layout.simple_spinner_item, experienceList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position, getContext(), parent, super.getDropDownView(position, null, parent));
                return view;
            }
        };
        ArrayAdapter<CharSequence> adapterEmploymentType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, employmentTypeList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position, getContext(), parent, super.getDropDownView(position, null, parent));
                return view;
            }
        };
        ArrayAdapter<CharSequence> adapterRequiredGpa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, requiredGpaList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = returnView(position, getContext(), parent, super.getDropDownView(position, null, parent));
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

        jobLastDateToApplyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateJob.this, enddate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jobTitleEditText.getText().toString().isEmpty() || jobLocationEditText.getText().toString().isEmpty() || jobSectorEditText.getText().toString().isEmpty() || jobCompanyNameEditText.getText().toString().isEmpty() || eligibilityDisciplinesEditText.getText().toString().isEmpty()
                || spinnerExperience.getSelectedItem().toString().isEmpty() || spinnerEmploymentType.getSelectedItem().toString().isEmpty() || jobLastDateToApplyEditText.getText().toString().isEmpty()){
                    Toast.makeText(CreateJob.this, "Incomplete Information", Toast.LENGTH_SHORT).show();
                }
                else {
                    createJobPost();
                    returnBack();
                }


            }
        });
    }

    private View returnView(int position, Context context, ViewGroup parent, View superView) {

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

    private void updateLabelJobLastDateToApplyEditText() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        jobLastDateToApplyEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void createJobPost(){

        PostJobModel postJobModel = new PostJobModel(jobTitleEditText.getText().toString(),jobLastDateToApplyEditText.getText().toString(),jobSectorEditText.getText().toString()
        ,jobLocationEditText.getText().toString(),jobCompanyNameEditText.getText().toString(),spinnerGender.getSelectedItem().toString(),spinnerExperience.getSelectedItem().toString()
        ,spinnerEmploymentType.getSelectedItem().toString(),jobDescriptionEditText.getText().toString(),jobSalaryEditText.getText().toString(),spinnerRequiredGpa.getSelectedItem().toString(),selectedDisciplineIds);

        Call<PostJobModel> call = RetrofitClient.getUserService().createJobPost(retrieveToken(),postJobModel);
        call.enqueue(new Callback<PostJobModel>() {
            @Override
            public void onResponse(Call<PostJobModel> call, Response<PostJobModel> response) {
                Toast.makeText(CreateJob.this, "Job Post Created", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostJobModel> call, Throwable t) {

            }
        });

    }
    public String retrieveToken(){
        SharedPreferences sharedPreferences = CreateJob.this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
    public void returnBack(){
        Intent intent = new Intent(CreateJob.this, JobListActivity.class);
        startActivity(intent);
    }
}