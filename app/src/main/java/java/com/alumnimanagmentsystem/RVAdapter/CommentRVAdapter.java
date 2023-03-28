package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.com.alumnimanagmentsystem.Model.CommentActivityRVModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.ViewHolder>{

    ArrayList<CommentActivityRVModel> commentActivityRVModelArrayList;
    Context context;

    public CommentRVAdapter(ArrayList<CommentActivityRVModel> commentActivityRVModelArrayList, Context context) {
        this.commentActivityRVModelArrayList = commentActivityRVModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_recycler_view_item, parent, false);
        return new CommentRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRVAdapter.ViewHolder holder, int position) {

        CommentActivityRVModel commentActivityRVModel = commentActivityRVModelArrayList.get(position);
        holder.userName.setText(commentActivityRVModel.getUserName());
        holder.userDegree.setText(commentActivityRVModel.getDegree());
        holder.comment.setText(commentActivityRVModel.getComment());
        holder.userImage.setImageResource(commentActivityRVModel.getImageID());

    }

    @Override
    public int getItemCount() {
        return commentActivityRVModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView userImage;
        TextView userName;
        TextView userDegree;
        TextView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.comment_user_photo);
            userName = itemView.findViewById(R.id.comment_user_name);
            userDegree = itemView.findViewById(R.id.comment_user_department);
            comment = itemView.findViewById(R.id.comment_thread_text_view);

        }
    }

}
