package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.RVAdapter.JobHistoryRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AchievementRVAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumniDetailActivity extends AppCompatActivity {


    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    RecyclerView recyclerViewJobHistory;
    JobHistoryRVAdapter jobHistoryRVAdapter;
    RecyclerView recyclerViewAchievement;
    AchievementRVAdapter achievementRVAdapter;
    Button emailButton;
    ImageView alumniProfilePic;
    TextView alumniProfileName;
    TextView alumniDegreeProgram;
    TextView alumniDepartment;
    TextView batchDuration;

    Integer alumniID;

    Alumnus alumnus;

    MutableLiveData<String> email = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_detail);
        recyclerViewAchievement = findViewById(R.id.achievementRecyclerView);
        recyclerViewAchievement.setNestedScrollingEnabled(false);
        recyclerViewJobHistory = findViewById(R.id.jobHistoryRecyclerView);
        recyclerViewJobHistory.setNestedScrollingEnabled(false);
        emailButton = findViewById(R.id.emailButton);
        alumniProfilePic = findViewById(R.id.alumniProfilePic);
        alumniProfileName = findViewById(R.id.alumniProfileName);
        alumniDegreeProgram = findViewById(R.id.alumniDegreeProgram);
        alumniDepartment = findViewById(R.id.alumniDepartment);
        batchDuration = findViewById(R.id.batchDuration);

        Bundle extra = getIntent().getExtras();
        alumniID = extra.getInt("alumniID");
        getAlumni();

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",email.getValue(), null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }

    private void getAlumni(){
        Call<Alumnus> call = RetrofitClient.getUserService().getAlumni(retrieveToken(), alumniID);
        call.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
                if(response.isSuccessful()){
                    alumnus = response.body();
                    String urlAlumniProfile = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000" + "/alumni/"+alumnus.getAlumni_id()+"/avatar";
                    GlideUrl glideUrl = new GlideUrl(urlAlumniProfile,
                            new LazyHeaders.Builder()
                                    .addHeader("Authorization", retrieveToken())
                                    .build());
                    Glide.with(AlumniDetailActivity.this)
                            .load(glideUrl)
                            .placeholder(R.drawable.default_user)
                            .into(alumniProfilePic);

                    alumniProfileName.setText(alumnus.getName());
                    alumniDegreeProgram.setText(alumnus.getDegree().getDegree_name());
                    alumniDepartment.setText(alumnus.getDegree().getDepartment().getDepartment_name());
                    batchDuration.setText(alumnus.getYearOfGrad());
                    email.postValue(alumnus.getEmail());

                    putDataIntoAchievementHistoryRecyclerView(Arrays.asList(alumnus.getAlumni_achievements()));
                    putDataIntoJobHistoryRecyclerView(Arrays.asList(alumnus.getAlumni_job_histories()));
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
    private void putDataIntoJobHistoryRecyclerView(List<AlumniJobHistories> alumniJobHistoriesList){
        recyclerViewJobHistory.setNestedScrollingEnabled(false);
        jobHistoryRVAdapter = new JobHistoryRVAdapter(alumniJobHistoriesList, this);
        recyclerViewJobHistory.setAdapter(jobHistoryRVAdapter);
        jobHistoryRVAdapter.notifyDataSetChanged();
    }
    private void putDataIntoAchievementHistoryRecyclerView(List<AlumniAchievements> alumniAchievementsList){
        recyclerViewAchievement.setNestedScrollingEnabled(false);
        achievementRVAdapter = new AchievementRVAdapter(new ArrayList<>(alumniAchievementsList), this);
        recyclerViewAchievement.setAdapter(achievementRVAdapter);
        achievementRVAdapter.notifyDataSetChanged();
    }

}