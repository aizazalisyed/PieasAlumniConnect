package java.com.alumnimanagmentsystem.RVAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.Model.JobHistoryModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class JobHistoryRVAdapter extends RecyclerView.Adapter<JobHistoryRVAdapter.ViewHolder> {
    ArrayList<JobHistoryModel> jobHistoryModelArrayList;
    Context context;

    public JobHistoryRVAdapter(ArrayList<JobHistoryModel> jobHistoryModelArrayList, Context context) {
        this.jobHistoryModelArrayList = jobHistoryModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public JobHistoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_history_recycler_view_item, parent, false);
        return new JobHistoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobHistoryRVAdapter.ViewHolder holder, int position) {
        JobHistoryModel jobHistoryModel = jobHistoryModelArrayList.get(position);
        holder.jobTitle.setText(jobHistoryModel.getTitle());
        holder.jobDescription.setText(jobHistoryModel.getDescription());
        holder.companyName.setText(jobHistoryModel.getCompanyName());

    }

    @Override
    public int getItemCount() {
        return jobHistoryModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle;
        TextView companyName;
        TextView jobDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            companyName = itemView.findViewById(R.id.companyName);
            jobDescription = itemView.findViewById(R.id.jobDiscription);
        }
    }

}
