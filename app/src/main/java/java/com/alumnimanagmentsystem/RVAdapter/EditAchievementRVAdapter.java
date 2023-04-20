package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Activities.EditAchievementActivity;
import java.com.alumnimanagmentsystem.Activities.EditJobHistoryFieldActivity;
import java.com.alumnimanagmentsystem.Activities.EditeAchievementFieldActivity;
import java.com.alumnimanagmentsystem.Activities.UserProfileActivity;
import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.R;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
               deleteAlert(alumniAchievements.getAchievement_id());
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditeAchievementFieldActivity.class);
                i.putExtra("achievementTitle", alumniAchievements.getTitle());
                i.putExtra("achievementDescription", alumniAchievements.getDescription());
                i.putExtra("Achievement_id",alumniAchievements.getAchievement_id());
                context.startActivity(i);
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

    private void deleteAlert(int id){
        new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                       Call<AlumniAchievements> call = RetrofitClient.getUserService().deleteAchievement(retrieveToken(),id);
                       call.enqueue(new Callback<AlumniAchievements>() {
                           @Override
                           public void onResponse(Call<AlumniAchievements> call, Response<AlumniAchievements> response) {
                               Toast.makeText(context, "click on delete button", Toast.LENGTH_SHORT).show();
                               SwitchToUserProfileActivity();
                           }

                           @Override
                           public void onFailure(Call<AlumniAchievements> call, Throwable t) {
                           }
                       });

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public String retrieveToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    private void SwitchToUserProfileActivity(){
        Intent switchActivityIntent = new Intent(context, UserProfileActivity.class);
        context.startActivity(switchActivityIntent);
    }



}
