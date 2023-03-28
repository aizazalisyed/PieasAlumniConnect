package java.com.alumnimanagmentsystem.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.com.alumnimanagmentsystem.Model.AlumniModel;
import java.com.alumnimanagmentsystem.RVAdapter.AlumniRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumniListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumniListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ArrayList<AlumniModel> alumniModelArrayList;
    RecyclerView recyclerView;
    AlumniRVAdapter alumniRVAdapter;
    MainActivity mainActivity;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlumniListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlumniFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlumniListFragment newInstance(String param1, String param2) {
        AlumniListFragment fragment = new AlumniListFragment();
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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_alumni_list, container, false);

        mainActivity = (MainActivity) getActivity();

        return view;

    }

    private void hideToolBar(){

        mainActivity.toolbar.setVisibility(View.GONE);
    }
    private void showToolBar(){
        mainActivity.toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeData();
        recyclerView = view.findViewById(R.id.alumniRecyclerView);
        alumniRVAdapter = new AlumniRVAdapter(alumniModelArrayList, getContext());
        recyclerView.setAdapter(alumniRVAdapter);
        alumniRVAdapter.notifyDataSetChanged();

        final int[] state = new int[1];

    }

    private void initializeData(){

        String name[] = new String[]{
                "Syed Aizaz Ali",
                "Ahmed Raza",
                "Abdul Raafy",
                "Ahsan Junaid",
                "Syed Aizaz Ali",
                "Ahmed Raza",
                "Abdul Raafy",
                "Ahsan Junaid","Syed Aizaz Ali",
                "Ahmed Raza",
                "Abdul Raafy",
                "Ahsan Junaid"
        };
        String department[] = new String[]{
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences"
                ,"Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",
                "Bs Computer and Information Sciences",


        };

        int imageId[] = new int[]{
                R.drawable.user_photo,
                R.drawable.ahmedraza,
                R.drawable.abdulrafy,
                R.drawable.ahsanjunaid,
                R.drawable.user_photo,
                R.drawable.ahmedraza,
                R.drawable.abdulrafy,
                R.drawable.ahsanjunaid,
                R.drawable.user_photo,
                R.drawable.ahmedraza,
                R.drawable.abdulrafy,
                R.drawable.ahsanjunaid,

        };

        alumniModelArrayList = new ArrayList<>();

        for (int i = 0; i < imageId.length; i++){
            AlumniModel alumniModel = new AlumniModel(imageId[i], name[i], department[i] );
            alumniModelArrayList.add(alumniModel);
        }

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