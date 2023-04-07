package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.Activities.EditJobHistoryFieldActivity;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.R;
import java.util.List;

public class EditJobHistoryRVAdapter extends RecyclerView.Adapter<EditJobHistoryRVAdapter.ViewHolder>{


    String jobTitle,jobDescription, companyName, fromDate, toDate;
    int jobHistoryID;
    List<AlumniJobHistories> jobHistoryModelArrayList;
    Context context;

    public EditJobHistoryRVAdapter(List<AlumniJobHistories> jobHistoryModelArrayList, Context context) {
        this.jobHistoryModelArrayList = jobHistoryModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public EditJobHistoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.edit_job_history_recycler_view_item, parent, false);
        return new EditJobHistoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditJobHistoryRVAdapter.ViewHolder holder, int position) {

        AlumniJobHistories jobHistoryModel = jobHistoryModelArrayList.get(position);


        // saving data to be sent via Intent

        jobHistoryID = jobHistoryModel.getJob_history_id();
        jobTitle = jobHistoryModel.getJob_title();
        jobDescription = jobHistoryModel.getDescription();
        companyName = jobHistoryModel.getCompany_name();
        fromDate = jobHistoryModel.getJob_start_date();
        toDate = jobHistoryModel.getJob_end_date();

        // binding data

        holder.jobTitle.setText(jobHistoryModel.getJob_title());
        holder.jobDescription.setText(jobHistoryModel.getDescription());
        holder.companyName.setText(jobHistoryModel.getCompany_name());
        holder.fromDate.setText(jobHistoryModel.getJob_start_date());
        holder.toDate.setText(jobHistoryModel.getJob_end_date());


        if( holder.jobDescription.getText().toString().isEmpty()){
            holder.jobDescription.setVisibility(View.GONE);
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, EditJobHistoryFieldActivity.class);
                i.putExtra("jobHistoryID", jobHistoryID);
                i.putExtra("jobTitle", jobTitle);
                i.putExtra("jobDescription",jobDescription);
                i.putExtra("companyName",companyName);
                i.putExtra("fromDate",fromDate);
                i.putExtra("toDate",toDate);
                context.startActivity(i);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobHistoryModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle;
        TextView companyName;
        TextView jobDescription;
        ImageView editButton, deleteButton;
        TextView fromDate, toDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            companyName = itemView.findViewById(R.id.companyName);
            jobDescription = itemView.findViewById(R.id.jobDiscription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            fromDate = itemView.findViewById(R.id.fromDate);
            toDate = itemView.findViewById(R.id.toDate);

        }
    }
    private void deleteAlert(){
        new AlertDialog.Builder(context)
                .setTitle("Report Post")
                .setMessage("Are you sure you want to delete this?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Toast.makeText(context, "click on delete button", Toast.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
