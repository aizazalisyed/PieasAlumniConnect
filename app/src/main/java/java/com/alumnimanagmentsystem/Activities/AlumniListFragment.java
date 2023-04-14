package java.com.alumnimanagmentsystem.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.RVAdapter.AchievementRVAdapter;
import java.com.alumnimanagmentsystem.RVAdapter.AlumniRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.ViewModel.AlumniListViewModel;
import java.com.alumnimanagmentsystem.ViewModel.AlumnusViewModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlumniListFragment extends Fragment {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    List<Alumnus> alumnusList;
    RecyclerView recyclerView;
    AlumniRVAdapter alumniRVAdapter;
    MainActivity mainActivity;
    TextView warning;
    Boolean isScrolling = false;
    int currentItems, scrollOutItems, totalItems;
    LinearLayoutManager manager;
    ProgressBar progressBar;
    ProgressBar mainProgressbar;
    AlumniListViewModel alumniListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alumniListViewModel = ViewModelProviders.of(requireActivity()).get(AlumniListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_alumni_list, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        warning = view.findViewById(R.id.warning);
        progressBar = view.findViewById(R.id.progressBar);
        mainProgressbar = view.findViewById(R.id.mainProgressbar);
        manager = new LinearLayoutManager(getContext());

        recyclerView = view.findViewById(R.id.alumniRecyclerView);
        recyclerView.setLayoutManager(manager);

        mainProgressbar.setVisibility(View.VISIBLE);




        alumniListViewModel.getAlumniList().observe(getActivity(), new Observer<List<Alumnus>>() {
           @Override
           public void onChanged(List<Alumnus> alumnusList) {

               putDataIntoRecyclerView(alumnusList);
               mainProgressbar.setVisibility(View.GONE);
           }
       });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)){

                    alumniListViewModel.offset++;
                    progressBar.setVisibility(View.VISIBLE);
                   // todo: instead of calling alumniListViewModel.makeApiCall() create a functoin in viewModel which append the new item into the alumniList;

                    alumniListViewModel.getAlumniList().observe(requireActivity(), new Observer<List<Alumnus>>() {
                        @Override
                        public void onChanged(List<Alumnus> alumnusList) {

                            putDataIntoRecyclerView(alumnusList);
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_item, menu);

        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Alumni");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                alumniRVAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }


    private void putDataIntoRecyclerView(List<Alumnus> alumnusList){

        alumniRVAdapter = new AlumniRVAdapter(alumnusList, getContext());
        recyclerView.setAdapter(alumniRVAdapter);
        alumniRVAdapter.notifyDataSetChanged();

    }
}