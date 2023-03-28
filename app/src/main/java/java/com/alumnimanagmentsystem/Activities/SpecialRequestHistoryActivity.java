package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.SpecialRequestHistoryRVAdapter;
import java.com.alumnimanagmentsystem.Model.SpecialRequestHistoryRVModel;
import java.util.ArrayList;

public class SpecialRequestHistoryActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<SpecialRequestHistoryRVModel> specialRequestHistoryRVModelArrayList;
    SpecialRequestHistoryRVAdapter specialRequestHistoryRVAdapter;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request_history);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.requestHistoryRecyclerView);
        warning = findViewById(R.id.warningHistory);
        specialRequestHistoryRVModelArrayList = new ArrayList<>();


        //initializeData();


        specialRequestHistoryRVAdapter= new SpecialRequestHistoryRVAdapter(specialRequestHistoryRVModelArrayList, this);
        recyclerView.setAdapter(specialRequestHistoryRVAdapter);
        specialRequestHistoryRVAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);

        if(specialRequestHistoryRVModelArrayList.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            warning.setVisibility(View.VISIBLE);
        }



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
                Intent intent = new Intent(SpecialRequestHistoryActivity.this, SpecialRequest.class);
                startActivity(intent);
            }
        });
    }

    private void initializeData(){

        String[] title = new String[]{
                "title",
                "title",
                "title",
                "title",
                "title",
                "title",
                "title",
                "title",
                "title",
                "title"
        };

        String[] description = new String[]{
                "description",
                "description",
                "description",
                "description",
                "description",
                "description",
                "description",
                "description",
                "description",
                "description",
        };


        for (int i = 0; i < title.length; i++){
            SpecialRequestHistoryRVModel specialRequestHistoryRVModel= new SpecialRequestHistoryRVModel(title[i],description[i]);
            specialRequestHistoryRVModelArrayList.add(specialRequestHistoryRVModel);
        }
    }

}