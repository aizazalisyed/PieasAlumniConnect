package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.RVAdapter.JobHistoryRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AchievementRVAdapter;
import java.util.ArrayList;

public class AlumniDetailActivity extends AppCompatActivity {

    ArrayList<AlumniJobHistories> jobHistoryModelArrayList;
    ArrayList<AlumniAchievements> achievementModelArrayList;
    RecyclerView recyclerViewJobHistory;
    JobHistoryRVAdapter jobHistoryRVAdapter;
    RecyclerView recyclerViewAchievement;
    AchievementRVAdapter achievementRVAdapter;
    Button jobHistoryButton;
    Button achievementButton;
    Button emailButton;
    RelativeLayout achievementView;
    RelativeLayout jobHistoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_detail);
        recyclerViewAchievement = findViewById(R.id.achievementRecyclerView);
        recyclerViewAchievement.setNestedScrollingEnabled(false);
        recyclerViewJobHistory = findViewById(R.id.jobHistoryRecyclerView);
        recyclerViewJobHistory.setNestedScrollingEnabled(false);
        emailButton = findViewById(R.id.emailButton);


//        jobHistoryRVAdapter = new JobHistoryRVAdapter(jobHistoryModelArrayList, this);
//        recyclerViewJobHistory.setAdapter(jobHistoryRVAdapter);
//        jobHistoryRVAdapter.notifyDataSetChanged();
//
//        achievementRVAdapter = new AchievementRVAdapter(achievementModelArrayList, this);
//        recyclerViewAchievement.setAdapter(achievementRVAdapter);
//        achievementRVAdapter.notifyDataSetChanged();

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","email@email.com", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }
}