package java.com.alumnimanagmentsystem.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.com.alumnimanagmentsystem.Model.DiscussionPanelModel;
import java.com.alumnimanagmentsystem.RVAdapter.DiscussionPanelRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;
import java.util.List;

public class DiscussionPanelFragment extends Fragment {

    ArrayList<DiscussionPanelModel> discussionPanelModelArrayList;
    RecyclerView recyclerView;
    DiscussionPanelRVAdapter discussionPanelRVAdapter;
    List<SlideModel> slideModelList;
    FloatingActionButton fab;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discussion_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = view.findViewById(R.id.fab);
        initializeData();

        recyclerView = view.findViewById(R.id.discussionPanelRecyclerView);
        discussionPanelRVAdapter = new DiscussionPanelRVAdapter(discussionPanelModelArrayList, getContext());
        recyclerView.setAdapter(discussionPanelRVAdapter);
        discussionPanelRVAdapter.notifyDataSetChanged();

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
                Intent intent = new Intent(getContext(), UploadPostActivity.class );
                startActivity(intent);
            }
        });
    }

    private void initializeData(){

        slideModelList = new ArrayList<SlideModel>();
        slideModelList.add( new SlideModel(R.drawable.a, ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.b, ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.c, ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.d, ScaleTypes.CENTER_CROP));

        String name[] = new String[]{
                "Ahmed Raza",
                "Abdul Rafy",
                "Syed Aizaz Ali",
                "Ahsand Junaid"};

        String caption[] = new String[]{
                "keep mati clean please!!",
                "When is the result coming......",
                "we have achieved it",
                "how was the mushaira event?? kfndslknfdlk" +
                        "sknslknsklnlskn" +
                        "ksmlkslksnklsnlks" +
                        "snslknslknslkns" +
                        "lsnlksnlsknslknslknslknslnslkns" +
                        "lsnlsknlsknlsknlsnlksn"
        };
        String commentCount []= new String[]{
                "2",
                "10",
                "6",
                "12"
        };

        String Degree [] = new String[]{
                "Bs Computer and Information Sciences",
                "Bs Electrical Engineering",
                "Ms Cyber Security",
                "Bs Electrical Engineering"
        };

        int dpID[] = new int[]{
                R.drawable.user_photo,
                R.drawable.ahmedraza,
                R.drawable.abdulrafy,
                R.drawable.ahsanjunaid,
        };
        List<List<SlideModel>> images= new ArrayList<>();
        images.add(slideModelList);
        images.add(slideModelList);
        images.add(slideModelList);
        images.add(slideModelList);


        discussionPanelModelArrayList = new ArrayList<>();

        for (int i = 0; i < dpID.length; i++){
            DiscussionPanelModel discussionPanelModel = new DiscussionPanelModel(name[i], Degree[i], caption[i], images.get(i), commentCount[i], dpID[i]);
            discussionPanelModelArrayList.add(discussionPanelModel);
        }

    }
}