package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class InsertAchievementActivity extends AppCompatActivity {

    Context context;
    Button saveButton;
    EditText AchievementTitleEditText;
    EditText jobDescriptionEditText;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_achievement);


        AchievementTitleEditText = findViewById(R.id.AchievementTitleEditText);
        jobDescriptionEditText = findViewById(R.id.jobDescriptionEditText);
        context = this;



        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(AchievementTitleEditText.getText().toString().isEmpty())
               {
                   Toast.makeText(InsertAchievementActivity.this, "Information incomplete", Toast.LENGTH_SHORT).show();
               }
               else {
                   sendDataViaAPI();
                   SwitchToUserProfileActivity();
               }
            }
        });
    }

    private void SwitchToUserProfileActivity(){

        Intent switchActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(switchActivityIntent);
    }

    private void sendDataViaAPI(){

        Log.i("api", "before before calling api");

        String achievementTitleString = AchievementTitleEditText.getText().toString();
        String achievementDescriptionString = jobDescriptionEditText.getText().toString();
        String achievementTypeString = "Award";

        AlumniAchievements alumniAchievements = new AlumniAchievements(achievementTypeString,achievementTitleString, achievementDescriptionString);

        Call<AlumniAchievements> call = RetrofitClient.getUserService().postAchievement(retrieveToken(),alumniAchievements);
        call.enqueue(new Callback<AlumniAchievements>() {
            @Override
            public void onResponse(Call<AlumniAchievements> call, Response<AlumniAchievements> response) {
                if(response.isSuccessful()){
                    Log.i("api", "calling api success");
                    Toast.makeText(InsertAchievementActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AlumniAchievements> call, Throwable t) {

                Log.i("api", "calling api failurea");


            }
        });
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}