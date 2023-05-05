package java.com.alumnimanagmentsystem.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.Department;
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

    AlumniListViewModel alumniListViewModel;
    EditText departmentEditText;
    String[] departmentNames;
    String selectedDepartment;
    EditText searchByEditText;

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

        departmentEditText = view.findViewById(R.id.departmentEditText);
        searchByEditText = view.findViewById(R.id.searchByEditText);
        alumniListViewModel.getDepartment().observe(getActivity(), new Observer<List<Department>>() {
            @Override
            public void onChanged(List<Department> departments) {
                // Populate the array with department names
                departmentNames = new String[departments.size()];
                for (int i = 0; i < departments.size(); i++) {
                    departmentNames[i] = departments.get(i).getDepartment_name();
                }
            }
        });

        departmentEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDepartmentDialog();
            }
        });
        searchByEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchByDialog();
            }
        });
    }
    // Method to display the dialog box
    private void showDepartmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select department");

        // Add the list of check options
        builder.setSingleChoiceItems(departmentNames, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedDepartment = departmentNames[which];
            }
        });

        // Set click listener for the positive button to save the selected department to the EditText
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                departmentEditText.setText(selectedDepartment);
            }
        });

        // Show the dialog box
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSearchByDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Search by");

        // Retrieve the string array resource
        String[] searchByArray = getResources().getStringArray(R.array.searchBy);

        // Add the list of check options
        builder.setSingleChoiceItems(searchByArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = searchByArray[which];
                searchByEditText.setText(selectedOption);
            }
        });

        // Set click listener for the positive button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the dialog box
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}