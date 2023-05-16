package java.com.alumnimanagmentsystem.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.com.alumnimanagmentsystem.Model.Department;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.ViewModel.AlumniListViewModel;
import java.util.List;


public class AlumniListFragment extends Fragment {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    Integer departmentID;

    AlumniListViewModel alumniListViewModel;
    EditText departmentEditText;
    String[] departmentNames;
    String selectedDepartment;
    EditText searchByEditText;
    EditText searchEditText;
    private int selectedSearchOption;
    Button searchButton;
    Button clearButton;
    List<Department> departmentsList;

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
        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        clearButton = view.findViewById(R.id.clearButton);


        alumniListViewModel.getDepartment().observe(getActivity(), new Observer<List<Department>>() {
            @Override
            public void onChanged(List<Department> departments) {
                // Populate the array with department names
                departmentNames = new String[departments.size()];
                for (int i = 0; i < departments.size(); i++) {
                    departmentNames[i] = departments.get(i).getDepartment_name();
                }
                departmentsList = departments;
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchByEditText.getText().toString().isEmpty() && searchEditText.getText().toString().isEmpty() && departmentEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Incomplete information", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), AlumniSearchedListActivity.class);
                    intent.putExtra("searchBy", searchByEditText.getText().toString());
                    intent.putExtra("departmentID", departmentID);
                    intent.putExtra("search", searchEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all the EditText views
                departmentEditText.setText("");
                searchByEditText.setText("");
                searchEditText.setText("");
                // Set the hint for searchByEditText to "Search Alumni"
                searchByEditText.setHint("Search Alumni");
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
                departmentID = which + 1;
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
                selectedSearchOption = which;

                searchByEditText.setText(selectedOption);
                String[] searchHints = getResources().getStringArray(R.array.search_hints);
               searchEditText.setHint(searchHints[selectedSearchOption]);
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