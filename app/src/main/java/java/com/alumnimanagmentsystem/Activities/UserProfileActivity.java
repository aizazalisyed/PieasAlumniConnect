package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AchievementModel;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.JobHistoryModel;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AchievementRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.JobHistoryRVAdapter;
import java.com.alumnimanagmentsystem.ViewModel.AlumnusViewModel;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements EditUserInfoDialog.EditUserInfoDialogListener {

   Alumnus alumnus;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    ArrayList<JobHistoryModel> jobHistoryModelArrayList;
    ArrayList<AchievementModel> achievementModelArrayList;
    RecyclerView recyclerViewJobHistory;
    JobHistoryRVAdapter jobHistoryRVAdapter;
    RecyclerView recyclerViewAchievement;
    AchievementRVAdapter achievementRVAdapter;
    RelativeLayout achievementView;
    RelativeLayout jobHistoryView;
    ImageView addExperienceButton;
    ImageView addAchievementButton;
    ImageView editPersonalInfo;
    TextView phoneNumber;
    TextView country;
    ImageView addProfilePicButton;
    ImageView alumniProfilePic;
    TextView alumniProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Context context = this;

        addExperienceButton = findViewById(R.id.addExperienceButton);
        addAchievementButton = findViewById(R.id.addAchievementButton);
        editPersonalInfo = findViewById(R.id.editPersonalInformation);
        phoneNumber = findViewById(R.id.phoneText);
        country = findViewById(R.id.countryText);
        addProfilePicButton = findViewById(R.id.addProfilePicButton);
        alumniProfilePic = findViewById(R.id.alumniProfilePic);
        alumniProfileName = findViewById(R.id.alumniProfileName);

        makeApiCall();
        setProfilePic();


        addExperienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               SwitchToInsertExperienceActivity();
               Animatoo.INSTANCE.animateSlideUp(context);
            }
        });
        addAchievementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchToInsertAchievementActivity();
                Animatoo.INSTANCE.animateSlideUp(context);
            }
        });

        editPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        initializeData();

        recyclerViewJobHistory = findViewById(R.id.userJobHistoryRecyclerView);
        recyclerViewJobHistory.setNestedScrollingEnabled(false);
        jobHistoryRVAdapter = new JobHistoryRVAdapter(jobHistoryModelArrayList, this);
        recyclerViewJobHistory.setAdapter(jobHistoryRVAdapter);
        jobHistoryRVAdapter.notifyDataSetChanged();

        recyclerViewAchievement = findViewById(R.id.userAchievementRecyclerView);
        recyclerViewAchievement.setNestedScrollingEnabled(false);
        achievementRVAdapter = new AchievementRVAdapter(achievementModelArrayList, this);
        recyclerViewAchievement.setAdapter(achievementRVAdapter);
        achievementRVAdapter.notifyDataSetChanged();

        addProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(UserProfileActivity.this)
                        .crop().
                        start();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        alumniProfilePic.setImageURI(uri);
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

    private void SwitchToInsertExperienceActivity(){

        Intent switchActivityIntent = new Intent(this, InsertExperienceActivity.class);
        startActivity(switchActivityIntent);
    }
    private void SwitchToInsertAchievementActivity(){

        Intent switchActivityIntent = new Intent(this, InsertAchievementActivity.class);
        startActivity(switchActivityIntent);
    }
    private void openDialog(){
         EditUserInfoDialog editUserInfoDialog = new EditUserInfoDialog();
         editUserInfoDialog.show(getSupportFragmentManager(), "edit user information dialog");
    }

    @Override
    public void applyTexts(String phoneNumber, String country) {

        if(phoneNumber.isEmpty()==false){
            this.phoneNumber.setText(phoneNumber);
        }

        if(country.isEmpty()==false) {
            this.country.setText(country);
        }
    }
    private void setProfilePic(){

        Call<ResponseBody> call = RetrofitClient.getUserService().fetchCaptcha(retrieveToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        alumniProfilePic.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                }
                else {
                    // TODO
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public String retrieveToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
    public void makeApiCall(){
        Call<Alumnus> alumnusCall = RetrofitClient.getUserService().getAlumnus(retrieveToken());
        alumnusCall.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
               alumnus = response.body();
                alumniProfileName.setText(alumnus.getName());
            }

            @Override
            public void onFailure(Call<Alumnus> call, Throwable t) {
                alumnus = null;
            }
        });
    }
}