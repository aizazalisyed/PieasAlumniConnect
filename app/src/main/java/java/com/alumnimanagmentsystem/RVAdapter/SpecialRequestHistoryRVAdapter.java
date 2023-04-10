package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.Model.SpecialRequest;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class SpecialRequestHistoryRVAdapter extends RecyclerView.Adapter<SpecialRequestHistoryRVAdapter.ViewHolder> {

    ArrayList<SpecialRequest> specialRequestHistoryRVModels;
    Context context;

    public SpecialRequestHistoryRVAdapter(ArrayList<SpecialRequest> specialRequestHistoryRVModels, Context context) {
        this.specialRequestHistoryRVModels = specialRequestHistoryRVModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.special_request_history_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialRequest specialRequest = specialRequestHistoryRVModels.get(position);
        holder.title.setText(specialRequest.getSubject());
        holder.description.setText(specialRequest.getDescription());
    }

    @Override
    public int getItemCount() {
        return specialRequestHistoryRVModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.historyTitle);
            description = itemView.findViewById(R.id.historyDescription);
        }
    }
}
