package java.com.alumnimanagmentsystem.Activities;

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

import java.com.alumnimanagmentsystem.Model.EventModel;
import java.com.alumnimanagmentsystem.Model.NewsModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.com.alumnimanagmentsystem.RVAdapter.DiscussionPanelRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.EventsRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.ViewModel.DiscussionPanelViewModel;
import java.com.alumnimanagmentsystem.ViewModel.EventViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerViewNews;
    EventsRVAdapter eventsRVAdapter;
    EventViewModel eventViewModel;
    LinearLayoutManager manager;
    int currentItems, scrollOutItems, totalItems;
    Boolean isScrolling = false;
    ProgressBar progressBar;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerViewNews = view.findViewById(R.id.newsEventsRecyclerView);
        eventViewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
        progressBar = view.findViewById(R.id.progressBar);
        manager = new LinearLayoutManager(getContext());

        recyclerViewNews.setLayoutManager(manager);
        recyclerViewNews.setHasFixedSize(true);

        progressBar.setVisibility(View.VISIBLE);

        eventViewModel.getEvents().observe(getActivity(), new Observer<List<EventModel>>() {
            @Override
            public void onChanged(List<EventModel> eventModels) {
                if (eventsRVAdapter == null) {
                    putDataIntoRecyclerView((ArrayList<EventModel>) eventModels);
                } else {
                   eventsRVAdapter.addItems((ArrayList<EventModel>) eventModels);
                }
                progressBar.setVisibility(View.GONE);
            }
        });


        recyclerViewNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {

                    isScrolling = false;
                   eventViewModel.offset += eventViewModel.limit;
                    eventViewModel.makeApiCall();
                }
            }
        });

    }

    private void putDataIntoRecyclerView(ArrayList<EventModel> eventModelArrayList) {

        Log.i("putDataIntoRecyclerView", "putDataIntoRecyclerView");
        eventsRVAdapter = new EventsRVAdapter(eventModelArrayList, getContext());
        recyclerViewNews.setAdapter(eventsRVAdapter);
    }

}