package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.EditJobHistoryRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.JobHistoryRVAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditJobHistoryActivity extends AppCompatActivity {

    RecyclerView editJobHistoryRecyclerView;
    EditJobHistoryRVAdapter editJobHistoryRVAdapter;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    List<AlumniJobHistories> jobHistoryModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job_history);

        editJobHistoryRecyclerView = findViewById(R.id.editJobHistoryRecyclerView);

        makeApiCallForRecyclerView();
    }

    public void  makeApiCallForRecyclerView() {


        Call<Alumnus> alumnusCall = RetrofitClient.getUserService().getAlumnus(retrieveToken());
        alumnusCall.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
                if (response.body() != null) {
                    Alumnus alumnus = response.body();
                    jobHistoryModelArrayList = new ArrayList<>(Arrays.asList(alumnus.getAlumni_job_histories()));
                    putDataIntoJobHistoryRecyclerView(jobHistoryModelArrayList);

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
        editJobHistoryRVAdapter = new EditJobHistoryRVAdapter(alumniJobHistoriesList, this);
        editJobHistoryRecyclerView.setAdapter(editJobHistoryRVAdapter);
        editJobHistoryRVAdapter.notifyDataSetChanged();
    }
}