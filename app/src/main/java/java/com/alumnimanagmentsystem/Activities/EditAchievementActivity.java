package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.EditAchievementRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.EditJobHistoryRVAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAchievementActivity extends AppCompatActivity {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    RecyclerView editAchievementRecyclerView;
    EditAchievementRVAdapter editAchievementRVAdapter;
    List<AlumniAchievements> alumniAchievementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_achievement);

        editAchievementRecyclerView = findViewById(R.id.editAchievementRecyclerView);
        makeApiCallForRecyclerView();

    }

    public void makeApiCallForRecyclerView() {


        Call<Alumnus> alumnusCall = RetrofitClient.getUserService().getAlumnus(retrieveToken());
        alumnusCall.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
                if (response.body() != null) {
                    Alumnus alumnus = response.body();
                    alumniAchievementsList = new ArrayList<>(Arrays.asList(alumnus.getAlumni_achievements()));
                    putDataIntoRecyclerView(alumniAchievementsList);

                }
            }

            @Override
            public void onFailure(Call<Alumnus> call, Throwable t) {

            }
        });
    }

    public String retrieveToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    private void putDataIntoRecyclerView(List<AlumniAchievements> alumniAchievements){
        editAchievementRVAdapter = new EditAchievementRVAdapter(alumniAchievements, this);
        editAchievementRecyclerView.setAdapter(editAchievementRVAdapter);
        editAchievementRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SwitchToUserProfileActivity();
    }

    private void SwitchToUserProfileActivity(){
        Intent switchActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(switchActivityIntent);
    }
}