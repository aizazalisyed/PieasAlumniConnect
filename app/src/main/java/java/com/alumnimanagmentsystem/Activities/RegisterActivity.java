package java.com.alumnimanagmentsystem.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.com.alumnimanagmentsystem.Activities.LoginActivity;
import java.com.alumnimanagmentsystem.R;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView alreadyHaveanAccount;

    String[] department = { "", "DCIS", "DMME", "DEE", "DME", "DP" };
    String[] degree = {"", "BS Computer Science", "MS Computer Science"};

    Spinner spinnerDepartment;
    Spinner getSpinnerDegree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveanAccount = findViewById(R.id.alreadyHaveanAccountTextView);
        alreadyHaveanAccount.setOnClickListener(v -> switchActivities());


        // *********************** code for setting spinners for department name and degree name ********************

        spinnerDepartment = findViewById(R.id.spinnerDepartmentNames);
        getSpinnerDegree = findViewById(R.id.spinnerDegreeNames);

        // setting up adapters

        ArrayAdapter adapterDepartment = new ArrayAdapter(this,R.layout.spinner_item_department_name,department){

            //disappearing first element
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = null;
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }
                return v;
            }
        };
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter adapterDegree = new ArrayAdapter(this,R.layout.spinner_item_degree_name,degree){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = null;
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }
                return v;
            }
        };
        adapterDegree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerDepartment.setAdapter(adapterDepartment);
        getSpinnerDegree.setAdapter(adapterDegree);

        spinnerDepartment.setOnItemSelectedListener(this);
        getSpinnerDegree.setOnItemSelectedListener(this);

        // spinners and adapters till here...



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        switch (adapterView.getId()){

            case R.id.spinnerDepartmentNames:
            {
                if(i >0)
                {
                    Toast.makeText(getApplicationContext(),department[i], Toast.LENGTH_LONG).show();
                }
                break;}
            case R.id.spinnerDegreeNames:
            {
                if(i > 0){
                    Toast.makeText(getApplicationContext(),degree[i], Toast.LENGTH_LONG).show();
                }

                break;
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }
}