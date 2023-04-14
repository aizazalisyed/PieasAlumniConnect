package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.SpecialRequest;
import java.com.alumnimanagmentsystem.Model.SpecialRequests;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.SpecialRequestHistoryRVAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialRequestHistoryActivity extends AppCompatActivity {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<SpecialRequest> specialRequestArrayList;
    SpecialRequestHistoryRVAdapter specialRequestHistoryRVAdapter;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request_history);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.requestHistoryRecyclerView);
        warning = findViewById(R.id.warningHistory);

        specialRequestArrayList = new ArrayList<>();

        recyclerView.setVisibility(View.GONE);
        warning.setVisibility(View.VISIBLE);

       makeApiCallForRecyclerView();

            recyclerView.setVisibility(View.GONE);
            warning.setVisibility(View.VISIBLE);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialRequestHistoryActivity.this, SendSpecialRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    public void  makeApiCallForRecyclerView(){

        Call<SpecialRequests> call = RetrofitClient.getUserService().getSpecialRequests(retrieveToken());

        call.enqueue(new Callback<SpecialRequests>() {
            @Override
            public void onResponse(Call<SpecialRequests> call, Response<SpecialRequests> response) {
                SpecialRequests specialRequests = response.body();
                specialRequestArrayList = new ArrayList<>(Arrays.asList(specialRequests.getSpecial_requests()));
                putDataIntoRecyclerView(specialRequestArrayList);
                if(specialRequests.getSpecial_requests().length==0){
                    warning.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SpecialRequests> call, Throwable t) {

            }
        });


    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    private void putDataIntoRecyclerView(List<SpecialRequest> specialRequests){
        specialRequestHistoryRVAdapter = new SpecialRequestHistoryRVAdapter((ArrayList<SpecialRequest>) specialRequests, this);
       recyclerView.setAdapter(specialRequestHistoryRVAdapter);
       specialRequestHistoryRVAdapter.notifyDataSetChanged();
        warning.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switchToMainActivity();
    }
    private void switchToMainActivity(){

        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}