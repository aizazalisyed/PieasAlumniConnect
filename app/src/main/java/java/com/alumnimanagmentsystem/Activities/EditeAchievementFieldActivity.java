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

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditeAchievementFieldActivity extends AppCompatActivity {

    EditText AchievementTitleEditText, DescriptionEditText;

    String AchievementTitleEditTextString, DescriptionEditTextString;

    int Achievement_id;

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_acvhievement_field);

        AchievementTitleEditText = findViewById(R.id.AchievementTitleEditText);
        DescriptionEditText = findViewById(R.id.jobDescriptionEditText);
        saveButton = findViewById(R.id.saveButton);


        Bundle extras = getIntent().getExtras();

        AchievementTitleEditTextString = extras.getString("achievementTitle");
        DescriptionEditTextString = extras.getString("achievementDescription");
        Achievement_id = extras.getInt("Achievement_id");

        AchievementTitleEditText.setText(AchievementTitleEditTextString);
        DescriptionEditText.setText(DescriptionEditTextString);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AchievementTitleEditText.getText().toString().isEmpty()){
                    Toast.makeText(EditeAchievementFieldActivity.this, "Information incomplete", Toast.LENGTH_SHORT).show();
                }
                else {
                    MakeApiCall();
                }
            }
        });


    }

    public void MakeApiCall() {

        AlumniAchievements alumniAchievements = new AlumniAchievements("award", AchievementTitleEditText.getText().toString(),DescriptionEditText.getText().toString());
        Call<AlumniAchievements> call = RetrofitClient.getUserService().patchAchievement(retrieveToken(),alumniAchievements,Achievement_id);

        call.enqueue(new Callback<AlumniAchievements>() {
            @Override
            public void onResponse(Call<AlumniAchievements> call, Response<AlumniAchievements> response) {
                SwitchToUserProfileActivity();
                Toast.makeText(EditeAchievementFieldActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AlumniAchievements> call, Throwable t) {

            }
        });


    }

    public String retrieveToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    private void SwitchToAchievementEditActivity() {
        Intent switchActivityIntent = new Intent(this, EditAchievementActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SwitchToAchievementEditActivity();
    }

    private void SwitchToUserProfileActivity() {
        Intent switchActivityIntent = new Intent(EditeAchievementFieldActivity.this, UserProfileActivity.class);
        startActivity(switchActivityIntent);
    }
}