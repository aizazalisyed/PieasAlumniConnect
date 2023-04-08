package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.R;
import java.util.List;

public class EditAchievementRVAdapter extends RecyclerView.Adapter<EditAchievementRVAdapter.ViewHolder> {

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    List<AlumniAchievements> alumniAchievementsList;
    Context context;

    public EditAchievementRVAdapter(List<AlumniAchievements> alumniAchievementsList, Context context) {
        this.alumniAchievementsList = alumniAchievementsList;
        this.context = context;
    }

    @NonNull
    @Override
    public EditAchievementRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.edit_achievement_recycler_view_item, parent, false);
        return new EditAchievementRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditAchievementRVAdapter.ViewHolder holder, int position) {

        AlumniAchievements alumniAchievements = alumniAchievementsList.get(position);

        holder.achievementTitle.setText(alumniAchievements.getTitle());
        holder.achievementDescription.setText(alumniAchievements.getDescription());


        if (alumniAchievements.getDescription().isEmpty()){
            holder.achievementDescription.setVisibility(View.GONE);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click on delete button", Toast.LENGTH_SHORT).show();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click on edit button", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return alumniAchievementsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView achievementTitle, achievementDescription;
        ImageView editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            achievementTitle = itemView.findViewById(R.id.achievementTitle);
            achievementDescription = itemView.findViewById(R.id.achievementDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}
