package java.com.alumnimanagmentsystem.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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

import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.RVAdapter.AlumniRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;


public class AlumniListFragment extends Fragment {

    ArrayList<Alumnus> alumniModelArrayList;
    RecyclerView recyclerView;
    AlumniRVAdapter alumniRVAdapter;
    MainActivity mainActivity;
    TextView warning;
    Boolean isScrolling = false;
    int currentItems, scrollOutItems, totalItems;
    LinearLayoutManager manager;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_alumni_list, container, false);

        mainActivity = (MainActivity) getActivity();

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        warning = view.findViewById(R.id.warning);
        progressBar = view.findViewById(R.id.progressBar);
        manager = new LinearLayoutManager(getContext());

        recyclerView = view.findViewById(R.id.alumniRecyclerView);
        alumniRVAdapter = new AlumniRVAdapter(alumniModelArrayList, getContext());
        recyclerView.setAdapter(alumniRVAdapter);
        recyclerView.setLayoutManager(manager);
        alumniRVAdapter.notifyDataSetChanged();


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
                    //data fatch;
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
}