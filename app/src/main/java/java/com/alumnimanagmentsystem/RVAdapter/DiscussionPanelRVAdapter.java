package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.com.alumnimanagmentsystem.Activities.CommentActivity;
import java.com.alumnimanagmentsystem.Model.DiscussionPanelModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class DiscussionPanelRVAdapter extends RecyclerView.Adapter<DiscussionPanelRVAdapter.ViewHolder>{

    ArrayList<DiscussionPanelModel> discussionPanelModelArrayList;
    Context context;

    public DiscussionPanelRVAdapter(ArrayList<DiscussionPanelModel> discussionPanelModelArrayList, Context context) {
        this.discussionPanelModelArrayList = discussionPanelModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiscussionPanelRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discussion_panel_recycler_view_iteam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionPanelRVAdapter.ViewHolder holder, int position) {

        DiscussionPanelModel discussionPanelModel = discussionPanelModelArrayList.get(position);
        holder.alumniName.setText(discussionPanelModel.getName());
        holder.degreeName.setText(discussionPanelModel.getDegree());
        holder.caption.setText(discussionPanelModel.getCaption());
        if(discussionPanelModel.getImages().isEmpty()){
            holder.imageView.setVisibility(View.GONE);
        }
        else {
            holder.imageView.setImageList(discussionPanelModel.getImages());
        }
        holder.commentCount.setText(discussionPanelModel.getCommentCount());
        holder.dpID.setImageResource(discussionPanelModel.getDpID());

        holder.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id;
                id = item.getItemId();
                if(id == R.id.nav_comment){
                    Intent i = new Intent(context, CommentActivity.class);
                    i.putExtra("userName", discussionPanelModel.getName());
                    i.putExtra("userDegree", discussionPanelModel.getDegree());
                    i.putExtra("userCaption", discussionPanelModel.getCaption());
                    i.putExtra("userDp", discussionPanelModel.getDpID());
                   // i.putExtra("imagePost", (Serializable) discussionPanelModel.getImages());
                    context.startActivity(i);
                    Animatoo.INSTANCE.animateCard(context);
                }
                else{
                    reportComment();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return discussionPanelModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alumniName;
        TextView degreeName;
        TextView caption;
        ImageSlider imageView;
        TextView commentCount;
        ImageView dpID;
        BottomNavigationView bottomNavigationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alumniName = itemView.findViewById(R.id.panel_user_name);
            degreeName = itemView.findViewById(R.id.panel_user_department);
            caption = itemView.findViewById(R.id.panel_discription_text_view);
            imageView = itemView.findViewById(R.id.panel_image_view);
            commentCount = itemView.findViewById(R.id.panel_comment_count);
            dpID = itemView.findViewById(R.id.panel_user_photo);
            bottomNavigationView = itemView.findViewById(R.id.bottom_nav_panel);
        }

    }

    private void reportComment(){
        new AlertDialog.Builder(context)
                .setTitle("Report Post")
                .setMessage("Are you sure you want to report this post?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}