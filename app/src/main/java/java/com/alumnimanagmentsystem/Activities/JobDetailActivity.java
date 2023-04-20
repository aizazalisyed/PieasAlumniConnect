package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.R;
import java.util.Objects;

public class JobDetailActivity extends AppCompatActivity {


    TextView jobTitle;
    TextView jobCompanyName;
    TextView jobLocation;
    TextView jobSalary;
    TextView employmentType;
    TextView createdOnTextView;
    TextView lastDateToApplyTextView;
    TextView requiredGpaTextView;
    TextView genderTextView;
    TextView eligibilityDisciplinesTextView;
    TextView descriptionTextView;
   Toolbar toolBarJobDetail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        Bundle extra = getIntent().getExtras();
        jobTitle = findViewById(R.id.jobTitle);
        jobCompanyName =findViewById(R.id.jobCompanyName);
        jobLocation = findViewById(R.id.jobLocation);
        jobSalary = findViewById(R.id.jobSalary);
        employmentType = findViewById(R.id.employmentType);
        createdOnTextView = findViewById(R.id.createdOnTextView);
        lastDateToApplyTextView = findViewById(R.id.lastDateToApplyTextView);
        requiredGpaTextView = findViewById(R.id.requiredGpaTextView);
        genderTextView = findViewById(R.id.genderTextView);
        eligibilityDisciplinesTextView = findViewById(R.id.eligibilityDisciplinesTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        toolBarJobDetail = findViewById(R.id.toolBarJobDetail);

        setSupportActionBar(toolBarJobDetail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        jobTitle.setText(extra.getString("job_title"));
        jobCompanyName.setText(extra.getString("company_name"));
        jobLocation.setText(extra.getString("location"));
        jobSalary.setText(extra.getString("salary"));
        employmentType.setText(extra.getString("employment_type"));
        createdOnTextView.setText(extra.getString("created_on"));
        lastDateToApplyTextView.setText(extra.getString("last_date_to_apply"));
        requiredGpaTextView.setText(extra.getString("required_gpa"));
        genderTextView.setText(extra.getString("gender"));
        eligibilityDisciplinesTextView.setText(extra.getString("eligibility_disciplines"));
        descriptionTextView.setText(extra.getString("description"));



    }
}