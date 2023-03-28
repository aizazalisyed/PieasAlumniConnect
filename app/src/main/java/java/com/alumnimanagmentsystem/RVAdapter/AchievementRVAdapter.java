package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.Model.AchievementModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class AchievementRVAdapter extends RecyclerView.Adapter<AchievementRVAdapter.ViewHolder>{

    ArrayList<AchievementModel> achievementModelArrayList;
    Context context;

    public AchievementRVAdapter(ArrayList<AchievementModel> achievementModelArrayList, Context context) {
        this.achievementModelArrayList = achievementModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AchievementRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.achievement_recycler_view_item, parent, false);
        return new AchievementRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementRVAdapter.ViewHolder holder, int position) {
        AchievementModel achievementModel = achievementModelArrayList.get(position);
        holder.title.setText(achievementModel.getTitle());
        holder.description.setText(achievementModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return achievementModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.achievementTitle);
            description = itemView.findViewById(R.id.achievementDescription);
        }
    }
}
