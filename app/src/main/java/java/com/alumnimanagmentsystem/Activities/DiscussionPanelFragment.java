package java.com.alumnimanagmentsystem.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.com.alumnimanagmentsystem.Model.DiscussionPanelModel;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.com.alumnimanagmentsystem.RVAdapter.DiscussionPanelRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.JobListRVAdapter;
import java.com.alumnimanagmentsystem.ViewModel.DiscussionPanelViewModel;
import java.com.alumnimanagmentsystem.ViewModel.JobListViewModel;
import java.util.ArrayList;
import java.util.List;

public class DiscussionPanelFragment extends Fragment {

    ArrayList<DiscussionPanelModel> discussionPanelModelArrayList;
    RecyclerView recyclerView;
    DiscussionPanelRVAdapter discussionPanelRVAdapter;
    List<SlideModel> slideModelList;
    FloatingActionButton fab;
    DiscussionPanelViewModel discussionPanelViewModel;
    ProgressBar progressBar;
    LinearLayoutManager manager;
    int currentItems, scrollOutItems, totalItems;
    Boolean isScrolling = false;

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
        recyclerView = view.findViewById(R.id.discussionPanelRecyclerView);
        discussionPanelViewModel = ViewModelProviders.of(getActivity()).get(DiscussionPanelViewModel.class);
        progressBar = view.findViewById(R.id.progressBar);
        manager = new LinearLayoutManager(getContext());


        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        progressBar.setVisibility(View.VISIBLE);

        discussionPanelViewModel.getPosts().observe(getActivity(), new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModels) {
                if (discussionPanelRVAdapter == null) {
                    putDataIntoRecyclerView((ArrayList<PostsModel>) postsModels);
                } else {
                    discussionPanelRVAdapter.addItems((ArrayList<PostsModel>) postsModels);
                }
               progressBar.setVisibility(View.GONE);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {

                    Log.i("reached end", "reached end");
                    isScrolling = false;
                    discussionPanelViewModel.offset += discussionPanelViewModel.limit;
                  discussionPanelViewModel.makeApiCall();
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

    private void putDataIntoRecyclerView(ArrayList<PostsModel> postsModelList){

        Log.i("putDataIntoRecyclerView", "putDataIntoRecyclerView");
        discussionPanelRVAdapter = new DiscussionPanelRVAdapter(postsModelList,getContext());
        recyclerView.setAdapter(discussionPanelRVAdapter);
    }
}