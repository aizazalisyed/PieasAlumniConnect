package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AlumniListRVAdapter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlumniSearchedListActivity extends AppCompatActivity {

    ProgressBar progressbar;
    RecyclerView recyclerView;
    AlumniListRVAdapter alumniListRVAdapter;
    TextView warningMessage;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    private List<Alumnus> alumniList = new ArrayList<>();
    Integer departmentID;
    String searchBy;
    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_searched_list);
        Bundle extra = getIntent().getExtras();

        recyclerView = findViewById(R.id.recyclerView);
        progressbar = findViewById(R.id.progressbar);
        warningMessage = findViewById(R.id.warningMessage);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        alumniListRVAdapter = new AlumniListRVAdapter(alumniList, this);
        recyclerView.setAdapter(alumniListRVAdapter);

        departmentID = extra.getInt("departmentID");
        searchBy = extra.getString("searchBy").trim();
        searchTerm = extra.getString("search");

        if(searchTerm.isEmpty()){
            searchTerm = null;
        }
        if(searchBy.isEmpty())
        {
            searchBy = null;
        }

        if(departmentID == 0){
            departmentID = null;
        }


        getAlumniList(searchTerm,searchBy,departmentID);
    }

    private void getAlumniList(String searchTerm, String searchBy, Integer department_id)
    {
        Call<List<Alumnus>> call = RetrofitClient.getUserService().getAlumniList(retrieveToken(),searchTerm,searchBy,department_id);

        call.enqueue(new Callback<List<Alumnus>>() {
            @Override
            public void onResponse(Call<List<Alumnus>> call, Response<List<Alumnus>> response) {

                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Alumnus> alumniList = response.body();
                    if (alumniList.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        warningMessage.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        warningMessage.setVisibility(View.GONE);
                        alumniListRVAdapter = new AlumniListRVAdapter(alumniList, AlumniSearchedListActivity.this);
                        recyclerView.setAdapter(alumniListRVAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Alumnus>> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                warningMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}