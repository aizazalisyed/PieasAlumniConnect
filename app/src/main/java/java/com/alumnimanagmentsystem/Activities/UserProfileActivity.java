package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AchievementRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.JobHistoryRVAdapter;
import java.com.alumnimanagmentsystem.RealPathUtil;
import java.com.alumnimanagmentsystem.ViewModel.AlumnusViewModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements EditUserInfoDialog.EditUserInfoDialogListener {

    String path;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    AlumnusViewModel alumnusViewModel;
    Context context;
    List<AlumniJobHistories> jobHistoryModelArrayList;
    List<AlumniAchievements> achievementModelArrayList;
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
    TextView alumniDegreeProgram;
    TextView alumniDepartment;
    TextView emailText;
    TextView cnicText;
    TextView batchDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        context = this;

        addExperienceButton = findViewById(R.id.addExperienceButton);
        addAchievementButton = findViewById(R.id.addAchievementButton);
        editPersonalInfo = findViewById(R.id.editPersonalInformation);
        phoneNumber = findViewById(R.id.phoneText);
        country = findViewById(R.id.countryText);
        addProfilePicButton = findViewById(R.id.addProfilePicButton);
        alumniProfilePic = findViewById(R.id.alumniProfilePic);
        alumniProfileName = findViewById(R.id.alumniProfileName);
        alumniDegreeProgram = findViewById(R.id.alumniDegreeProgram);
        alumniDepartment = findViewById(R.id.alumniDepartment);
        emailText = findViewById(R.id.emailText);
        cnicText = findViewById(R.id.cnicText);
        batchDuration = findViewById(R.id.batchDuration);



        recyclerViewJobHistory = findViewById(R.id.userJobHistoryRecyclerView);
        recyclerViewAchievement = findViewById(R.id.userAchievementRecyclerView);
        MakeapiCallForRecyclerView();


        alumnusViewModel = ViewModelProviders.of(this).get(AlumnusViewModel.class);


        alumnusViewModel.getAlumnus().observe(this, new Observer<Alumnus>() {
          @Override
          public void onChanged(Alumnus alumnus) {
              alumniProfileName.setText(alumnus.getName());
              phoneNumber.setText(alumnus.getPhoneNumber());
              batchDuration.setText(alumnus.getYearOfGrad());
              cnicText.setText(alumnus.getCnic());
              phoneNumber.setText(alumnus.getPhoneNumber());
              country.setText(alumnus.getCountry());
              emailText.setText(alumnus.getEmail());
          }
      });




      alumnusViewModel.getProfilePic().observe(this, bitmap -> {alumniProfilePic.setImageBitmap(bitmap);});

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



        addProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                }
                else {
                    ActivityCompat.requestPermissions(UserProfileActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SwitchToMainActivity();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


           if(requestCode == 10 && resultCode == Activity.RESULT_OK){
               Uri uri = data.getData();
               Context context = UserProfileActivity.this;
               path = RealPathUtil.getRealPath(context, uri);
               Bitmap bitmap = BitmapFactory.decodeFile(path);
               alumniProfilePic.setImageBitmap(bitmap);
               sendPicViaApi();

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
    public void  MakeapiCallForRecyclerView() {


        Call<Alumnus> alumnusCall = RetrofitClient.getUserService().getAlumnus(retrieveToken());
        alumnusCall.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
                if (response.body() != null) {
                   Alumnus alumnus = response.body();
                   jobHistoryModelArrayList = new ArrayList<>(Arrays.asList(alumnus.getAlumni_job_histories()));
                    putDataIntoJobHistoryRecyclerView(jobHistoryModelArrayList);

                    achievementModelArrayList = new ArrayList<>(Arrays.asList(alumnus.getAlumni_achievements()));
                    putDataIntoAchievementHistoryRecyclerView(achievementModelArrayList);

                }
            }

            @Override
            public void onFailure(Call<Alumnus> call, Throwable t) {

            }
        });
    }

    public String retrieveToken(){
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
        achievementRVAdapter = new AchievementRVAdapter((ArrayList<AlumniAchievements>) alumniAchievementsList, this);
        recyclerViewAchievement.setAdapter(achievementRVAdapter);
        achievementRVAdapter.notifyDataSetChanged();
    }
    private void SwitchToMainActivity(){
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void sendPicViaApi(){

        File file = new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/from-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("photo",file.getName(),requestFile);

        Call<ResponseBody> call = RetrofitClient.getUserService().postImage(retrieveToken(),body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}