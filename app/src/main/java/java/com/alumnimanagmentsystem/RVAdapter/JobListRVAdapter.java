package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.w3c.dom.Text;

import java.com.alumnimanagmentsystem.Activities.JobDetailActivity;
import java.com.alumnimanagmentsystem.Model.EligibilityDiscipline;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;
import java.util.List;

public class JobListRVAdapter extends RecyclerView.Adapter<JobListRVAdapter.ViewHolder> {

    Context context;
    List<JobModel> jobModelList;

    public JobListRVAdapter(Context context, List<JobModel> jobModelList) {
        this.context = context;
        this.jobModelList = jobModelList;
    }

    @NonNull
    @Override
    public JobListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jobs_recycler_view_iteam, parent, false);
        return new ViewHolder(view);
    }

    public void addItems(ArrayList<JobModel> items) {
        int startPosition = jobModelList.size();
        jobModelList = new ArrayList<>();
        jobModelList.addAll(items);
        notifyItemRangeInserted(startPosition, items.size());
    }

    @Override
    public void onBindViewHolder(@NonNull JobListRVAdapter.ViewHolder holder, int position) {

        JobModel jobModel = jobModelList.get(position);

        holder.jobTitle.setText(jobModel.getJob_title());
        holder.jobCompanyName.setText(jobModel.getCompany_name());
        holder.jobLocation.setText(jobModel.getLocation());
        holder.jobSalary.setText(jobModel.getSalary());
        holder.employmentType.setText(jobModel.getEmployment_type());
        holder.createdOnTextView.setText(jobModel.getCreated_on());
        holder.lastDateToApplyTextView.setText(jobModel.getLast_date_to_apply());

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, JobDetailActivity.class);
            intent.putExtra("job_title", jobModel.getJob_title());
            intent.putExtra("company_name", jobModel.getCompany_name());
            intent.putExtra("location", jobModel.getLocation());
            intent.putExtra("salary", jobModel.getSalary());
            intent.putExtra("employment_type", jobModel.getEmployment_type());
            intent.putExtra("created_on", jobModel.getCreated_on());
            intent.putExtra("last_date_to_apply", jobModel.getLast_date_to_apply());
            intent.putExtra("experience", jobModel.getExperience());
            intent.putExtra("gender", jobModel.getGender());
            intent.putExtra("description", jobModel.getDescription());
            intent.putExtra("required_gpa", jobModel.getRequired_gpa());

            String eligibility_disciplines = stringBuilder(jobModel.getEligibility_disciplines());
            intent.putExtra("eligibility_disciplines", eligibility_disciplines);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return jobModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView jobTitle;
        TextView jobCompanyName;
        TextView jobLocation;
        TextView jobSalary;
        TextView employmentType;
        TextView createdOnTextView;
        TextView lastDateToApplyTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobCompanyName = itemView.findViewById(R.id.jobCompanyName);
            jobLocation = itemView.findViewById(R.id.jobLocation);
            jobSalary = itemView.findViewById(R.id.jobSalary);
            employmentType = itemView.findViewById(R.id.employmentType);
            createdOnTextView = itemView.findViewById(R.id.createdOnTextView);
            lastDateToApplyTextView = itemView.findViewById(R.id.lastDateToApplyTextView);

        }
    }

    private String stringBuilder(EligibilityDiscipline[] eligibilityDisciplines){

        String string = "";

        for (int i = 0; i < eligibilityDisciplines.length; i++){
            string += eligibilityDisciplines[i].getName()+"\n";
        }
        return string;
    }
}
