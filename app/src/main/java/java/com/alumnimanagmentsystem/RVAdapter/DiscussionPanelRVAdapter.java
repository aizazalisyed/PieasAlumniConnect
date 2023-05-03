package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Activities.CommentActivity;
import java.com.alumnimanagmentsystem.Model.PostThreadModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.com.alumnimanagmentsystem.Model.ReportPost;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionPanelRVAdapter extends RecyclerView.Adapter<DiscussionPanelRVAdapter.ViewHolder>{

    ArrayList<PostsModel> postsModelArrayList;
    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    public DiscussionPanelRVAdapter(ArrayList<PostsModel> postsModelArrayList, Context context) {
        this.postsModelArrayList = postsModelArrayList;
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

        PostsModel postsModel = postsModelArrayList.get(position);

        if (postsModel.creater_id_operator == null) {
            holder.alumniName.setText(postsModel.getAlumni().getName());
            holder.degreeName.setText(postsModel.getAlumni().getDegree().getDegree_name());
            String urlAlumniProfile = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000" + "/alumni/"+postsModel.getAlumni().getAlumni_id()+"/avatar";
                GlideUrl glideUrl = new GlideUrl(urlAlumniProfile,
                        new LazyHeaders.Builder()
                                .addHeader("Authorization", retrieveToken())
                                .build());
                Glide.with(context)
                        .load(glideUrl)
                        .placeholder(R.drawable.default_user)
                        .into(holder.dpID);

        } else {
            holder.alumniName.setText("Admin");
            holder.degreeName.setVisibility(View.GONE);
            holder.dpID.setImageResource(R.drawable.default_user);
        }
        if (postsModel.getContent().isEmpty()) {

            holder.caption.setVisibility(View.GONE);
        } else {
            holder.caption.setText(postsModel.getContent());

        }

        holder.commentCount.setText(String.valueOf(postsModel.getThreads().size()));
        holder.panel_post_time.setText(postsModel.getCreated_on());

        String url = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000/postimages/" + postsModel.getPost_id();


            GlideUrl glideUrl = new GlideUrl(url,
                    new LazyHeaders.Builder()
                            .addHeader("Authorization", retrieveToken())
                            .build());
            Glide.with(context)
                    .load(glideUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.imageView.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.imageView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .placeholder(R.drawable.default_imag)
                    .into(holder.imageView);


        holder.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id;
                id = item.getItemId();
                if(id == R.id.nav_comment){
                    Intent i = new Intent(context, CommentActivity.class);
                    if (postsModel.getAlumni() != null) {
                        i.putExtra("userName", postsModel.getAlumni().getName());
                        i.putExtra("userDegree", postsModel.getAlumni().getDegree().getDegree_name());
                        i.putExtra("userId",String.valueOf(postsModel.getAlumni().getAlumni_id()));
                    }
                    else{
                        i.putExtra("userName", "Admin");
                        i.putExtra("userDegree", "");
                    }

                    i.putExtra("userCaption", postsModel.getContent());
                    i.putExtra("postId", postsModel.getPost_id());
                    i.putExtra("post_time", postsModel.getCreated_on());
                    i.putExtra("Token",retrieveToken());
                    i.putExtra("commentCount",postsModel.getThreads().size());
                    i.putExtra("position", holder.getAdapterPosition());

                    if (postsModel.getThreads() != null && !postsModel.getThreads().isEmpty()) {
                        i.putExtra("pthreadList", postsModel.getThreads());
                    } else {
                        i.putExtra("pthreadList", (ArrayList<PostThreadModel>) null);
                    }

                    context.startActivity(i);
                    Animatoo.INSTANCE.animateCard(context);
                }
                else{
                    reportComment(postsModel.getPost_id());
                }
                return false;
            }
        });
    }

    public void addItems(ArrayList<PostsModel> items) {
        int startPosition = postsModelArrayList.size();
        postsModelArrayList = new ArrayList<>();
        postsModelArrayList.addAll(items);
        notifyItemRangeInserted(startPosition, items.size());
    }

    @Override
    public int getItemCount() {
        return postsModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView alumniName;
        TextView degreeName;
        TextView caption;
        TextView panel_post_time;
        ImageView imageView;
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
            panel_post_time = itemView.findViewById(R.id.panel_post_time);
        }

    }

    private void reportComment(int id){
        new AlertDialog.Builder(context)
                .setTitle("Report Post")
                .setMessage("Are you sure you want to report this post?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // todo: report api
                        reportPost(id);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    private void reportPost(int id){

        ReportPost reportPost = new ReportPost();
        Call<ResponseBody> call = RetrofitClient.getUserService().reportPatchPost(retrieveToken(),reportPost,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "Post Reported", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}