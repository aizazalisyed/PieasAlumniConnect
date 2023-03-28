package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.com.alumnimanagmentsystem.Model.JobHistoryModel;
import java.com.alumnimanagmentsystem.RVAdapter.JobHistoryRVAdapter;
import java.com.alumnimanagmentsystem.Model.AchievementModel;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AchievementRVAdapter;
import java.util.ArrayList;

public class AlumniDetailActivity extends AppCompatActivity {

    ArrayList<JobHistoryModel> jobHistoryModelArrayList;
    ArrayList<AchievementModel> achievementModelArrayList;
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

        initializeData();

        jobHistoryRVAdapter = new JobHistoryRVAdapter(jobHistoryModelArrayList, this);
        recyclerViewJobHistory.setAdapter(jobHistoryRVAdapter);
        jobHistoryRVAdapter.notifyDataSetChanged();

        achievementRVAdapter = new AchievementRVAdapter(achievementModelArrayList, this);
        recyclerViewAchievement.setAdapter(achievementRVAdapter);
        achievementRVAdapter.notifyDataSetChanged();

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","email@email.com", null));
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }

    private void initializeData() {

        String[] title = new String[]{
                "Junior Programmer",
                "Senior Programmer",
                "Principal Programmer"
        };

        String[] companyName = new String[]{
                "Eziline Software House Pvt Ltd",
                "Coding Souls |Software House in Pakistan|",
                "Eziline Software House Pvt Ltd"
        };

        String[] description = new String[]{
                "Object-Oriented Design (OOD), Written and Verbal Communication, Teamwork",
                "Object-Oriented Design (OOD), Written and Verbal Communication, Teamwork",
                "Object-Oriented Design (OOD), Written and Verbal Communication, Teamwork"
        };

        String[] achievementTitle = new String[]{
                "Conference On Recent Advances In Electrical Engineering And Computer Sciences 2022 (RAEE&CS'22)",
                "Conference On Recent Advances In Electrical Engineering And Computer Sciences 2022 (RAEE&CS'22)"
        };

        String[] achievementDescription = new String[]{

                "The International Conference on Recent Advances in Electrical Engineering and Computer Sciences 2022 (RAEE&CS'22) was organized by jointly by the Department of Electrical Engineering and the Department of Computer Sciences PIEAS from October 18-19, 2022. Very high-quality original papers from both academia and industry have been submitted. The review of papers was done extensively.\n" +
                        "\n" +
                        "Rector, PIEAS was the chief guest for the opening ceremony. The opening ceremony was attended by the Deans, Directors and PIEAS faculty. Speaking to the audience, he appreciated the efforts of both the departments in extending the scope of this conference. He also emphasized on enhancing research collaborations through this platform. Dean research and Chair of the conference, Dr. Naeem Iqbal presented the summary of the review process and its quality. He also briefly summarized the topics covered in the conference and the invited talks in the emerging areas of electrical engineering and computer science. These research areas included, Applied Control, Biomedical Systems, Process monitoring and fault diagnosis, Power Systems, Artificial Intelligence and Machine Learning, and Internet of Things. Invited Talks by 4 foreign experts were delivered. The presentations of the foreign guests were also delivered online this year.",
                "The International Conference on Recent Advances in Electrical Engineering and Computer Sciences 2022 (RAEE&CS'22) was organized by jointly by the Department of Electrical Engineering and the Department of Computer Sciences PIEAS from October 18-19, 2022. Very high-quality original papers from both academia and industry have been submitted. The review of papers was done extensively.\n" +
                        "\n" +
                        "Rector, PIEAS was the chief guest for the opening ceremony. The opening ceremony was attended by the Deans, Directors and PIEAS faculty. Speaking to the audience, he appreciated the efforts of both the departments in extending the scope of this conference. He also emphasized on enhancing research collaborations through this platform. Dean research and Chair of the conference, Dr. Naeem Iqbal presented the summary of the review process and its quality. He also briefly summarized the topics covered in the conference and the invited talks in the emerging areas of electrical engineering and computer science. These research areas included, Applied Control, Biomedical Systems, Process monitoring and fault diagnosis, Power Systems, Artificial Intelligence and Machine Learning, and Internet of Things. Invited Talks by 4 foreign experts were delivered. The presentations of the foreign guests were also delivered online this year."
        };

        achievementModelArrayList = new ArrayList<>();
        jobHistoryModelArrayList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            JobHistoryModel jobHistoryModel = new JobHistoryModel(title[i], companyName[i], description[i]);
            jobHistoryModelArrayList.add(jobHistoryModel);
        }
        for (int i = 0; i < achievementTitle.length; i++) {
            AchievementModel achievementModel = new AchievementModel(achievementTitle[i], achievementDescription[i]);
            achievementModelArrayList.add(achievementModel);
        }
    }
}