package java.com.alumnimanagmentsystem.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.com.alumnimanagmentsystem.Model.NewsModel;
import java.com.alumnimanagmentsystem.RVAdapter.NewsRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<NewsModel> newsModelArrayList;
    RecyclerView recyclerViewNews;




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


        initializeNewsData();

        recyclerViewNews = view.findViewById(R.id.newsEventsRecyclerView);
        NewsRVAdapter newsRVAdapter = new NewsRVAdapter(newsModelArrayList, getContext());
        recyclerViewNews.setAdapter(newsRVAdapter);
        newsRVAdapter.notifyDataSetChanged();





    }


    private void initializeNewsData(){

        String title [];
        title = new String[]{
                "EHSAAS UG Scholarship Awardees List 2021-25",
                "Café Mushaira 2.0",
                "Closing Ceremony Of PIEAS Student Week 2022",
                "Team Ascensor Bags A Bronze In Teknofest 22",
                "Students Brings Laurels To The Country In 52nd International Physics Olympiad"
        };

        String description[];
        description = new String[]{
            "EHSAAS UG Scholarship Awardees List 2021-25",


                "Cafe Mushaira 2.0', one of the most exciting literary " +
                        "events of the year was arranged by PIEAS Literary " +
                        "Society on November 24, 2022. The students and faculty participated in evening of"
                ,

                "Student Week - Fall semester 2022 was organized successfully by PIEAS Sportics Society (PSS)" +
                " after a gap of 3 years, from 10th - 16th " +
                "October, 2022. Male and female students participated",


                "Team Ascensor is a veteran group of aspiring students from the Pakistan Institute of" +
                        " Engineering and Applied Sciences that has " +
                        "now made Pakistan proud twice by securing positions in Teknofest’21 and ‘22.",
                "The 52nd International Physics Olympiad (IPhO) was organized by" +
                        " Switzerland in online format during the period July 10 - 17, " +
                        "2022. This year 368 students from 78 countries participated in"
        };

        int imagesID[];
        imagesID = new int[]{
          R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e
        };

        newsModelArrayList = new ArrayList<>();

        for (int i = 0; i < title.length; i++ ){

            NewsModel newsModel = new NewsModel(title[i], description[i], imagesID[i]);
            newsModelArrayList.add(newsModel);
        }


    }
    private void initializeEventsData(){

        newsModelArrayList = new ArrayList<>();

    }
    private void initializeAchievementsData(){

        newsModelArrayList = new ArrayList<>();

    }
}