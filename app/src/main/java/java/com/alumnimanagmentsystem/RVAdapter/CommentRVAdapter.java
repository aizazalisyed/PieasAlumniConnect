package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.CommentActivityRVModel;
import java.com.alumnimanagmentsystem.Model.PostThreadModel;
import java.com.alumnimanagmentsystem.Model.ReportPost;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.ViewHolder>{

    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    ArrayList<PostThreadModel> postThreadModelArrayList;
    Context context;

    public CommentRVAdapter(ArrayList<PostThreadModel> postThreadModelArrayList, Context context) {
        this.postThreadModelArrayList = postThreadModelArrayList;
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

        PostThreadModel postThreadModel = postThreadModelArrayList.get(position);


        if(postThreadModel.getAlumni() != null)
        {
            holder.userName.setText(postThreadModel.getAlumni().getName());
            holder.userDegree.setText(postThreadModel.getAlumni().getDegree().getDegree_name());

            String urlAlumniProfile = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000" + "/alumni/"+postThreadModel.getAlumni().getAlumni_id()+"/avatar";
            GlideUrl glideUrl = new GlideUrl(urlAlumniProfile,
                    new LazyHeaders.Builder()
                            .addHeader("Authorization", retrieveToken())
                            .build());
            Glide.with(context)
                    .load(glideUrl)
                    .placeholder(R.drawable.user_photo)
                    .into(holder.userImage);
        }
        else {
            holder.userName.setText("Admin");
            holder.userDegree.setVisibility(View.GONE);
        }

        holder.comment.setText(postThreadModel.getContent());
        holder.post_time.setText(postThreadModel.getCreated_on());
        holder.reportComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportComment(postThreadModel.thread_id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postThreadModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView userImage;
        TextView userName;
        TextView userDegree;
        TextView comment;
        TextView post_time;
        ImageView reportComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.comment_user_photo);
            userName = itemView.findViewById(R.id.comment_user_name);
            userDegree = itemView.findViewById(R.id.comment_user_department);
            comment = itemView.findViewById(R.id.comment_thread_text_view);
            post_time = itemView.findViewById(R.id.post_time);
            reportComment = itemView.findViewById(R.id.reportComment);
        }
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
    private void reportComment(int id){
        new AlertDialog.Builder(context)
                .setTitle("Report This Comment")
                .setMessage("Are you sure you want to report this comment?")

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
    private void reportPost(int id){

        ReportPost reportPost = new ReportPost();

        Call<ResponseBody> call = RetrofitClient.getUserService().reportPatchThread(retrieveToken(),reportPost,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "Comment Reported", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
