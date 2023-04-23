package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.AlumniRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.JobListRVAdapter;
import java.com.alumnimanagmentsystem.ViewModel.AlumniListViewModel;
import java.com.alumnimanagmentsystem.ViewModel.JobListViewModel;
import java.util.List;
import java.util.Objects;
public class JobListActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayoutManager manager;
    ProgressBar progressBar;
    ProgressBar mainProgressbar;
    TextView warning;
    RecyclerView recyclerView;
    JobListRVAdapter jobListRVAdapter;
    JobListViewModel jobListViewModel;
    int currentItems, scrollOutItems, totalItems;
    Boolean isScrolling = false;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        toolbar = findViewById(R.id.toolBarJobList);
        warning = findViewById(R.id.warning);
        progressBar = findViewById(R.id.progressBar);
        mainProgressbar = findViewById(R.id.mainProgressbar);
        manager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.JobsRecyclerView);
        jobListViewModel = ViewModelProviders.of(this).get(JobListViewModel.class);
        fab = findViewById(R.id.fab);


        progressBar.setVisibility(View.GONE);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mainProgressbar.setVisibility(View.VISIBLE);

        jobListViewModel.getJobList().observe(this, new Observer<List<JobModel>>() {
            @Override
            public void onChanged(List<JobModel> jobModels) {
                if (jobListRVAdapter == null) {
                    putDataIntoRecyclerView(jobModels);
                } else {
                    jobListRVAdapter.addItems(jobModels);
                }
                mainProgressbar.setVisibility(View.GONE);
            }
        });

        // Set up scroll listener to implement pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;

                    Log.i(" isScrolling","True");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.i(" onScrolled","calling from inside");

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {

                    Log.i("reached end", "reached end");
                    isScrolling = false;
                    jobListViewModel.offset += jobListViewModel.limit;
                    jobListViewModel.makeApiCall();
                }
            }
        });

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

        fab.setOnClickListener(v -> {
            SwitchToCreateJobActivity();
        });
    }

    private void putDataIntoRecyclerView(List<JobModel> jobModelList){

        Log.i("putDataIntoRecyclerView", "putDataIntoRecyclerView");
        jobListRVAdapter = new JobListRVAdapter(this, jobModelList);
        recyclerView.setAdapter(jobListRVAdapter);
    }

    private void SwitchToCreateJobActivity(){
    Intent intent = new Intent(this, CreateJob.class);
    startActivity(intent);
    }
}