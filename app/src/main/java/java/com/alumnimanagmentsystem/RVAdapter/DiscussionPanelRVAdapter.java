package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Activities.CommentActivity;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;
import java.util.List;

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
    MutableLiveData<Bitmap> bitmapMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Bitmap> bitmapPostImageMutableLiveData = new MutableLiveData<>();

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

       if(postsModel.getAlumni() != null){
           holder.alumniName.setText(postsModel.getAlumni().getName());
           holder.degreeName.setText(postsModel.getAlumni().getDegree().getDegree_name());

           if(getProfilePic(postsModel.getAlumni().getAlumni_id()) != null) {
               holder.dpID.setImageBitmap(getProfilePic(postsModel.getAlumni().getAlumni_id()));
           }
       }
       else {
           holder.alumniName.setText("Admin");
           holder.degreeName.setVisibility(View.GONE);
       }
       if(postsModel.getContent().isEmpty()){

           holder.caption.setVisibility(View.GONE);
       }
       else{
           holder.caption.setText(postsModel.getContent());

       }

       holder.commentCount.setText(String.valueOf(postsModel.getThreads().size()));

       if(getPostImage(postsModel.getPost_id()) != null){
           holder.imageView.setImageBitmap(getPostImage(postsModel.getPost_id()));
       }
       else {
           holder.imageView.setVisibility(View.GONE);
       }
//todo
//        holder.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id;
//                id = item.getItemId();
//                if(id == R.id.nav_comment){
//                    Intent i = new Intent(context, CommentActivity.class);
//                    i.putExtra("userName", discussionPanelModel.getName());
//                    i.putExtra("userDegree", discussionPanelModel.getDegree());
//                    i.putExtra("userCaption", discussionPanelModel.getCaption());
//                    i.putExtra("userDp", discussionPanelModel.getDpID());
//                   // i.putExtra("imagePost", (Serializable) discussionPanelModel.getImages());
//                    context.startActivity(i);
//                    Animatoo.INSTANCE.animateCard(context);
//                }
//                else{
//                    reportComment();
//                }
//                return false;
//            }
//        });
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

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    public Bitmap getProfilePic(int id){


        Call<ResponseBody> call = RetrofitClient.getUserService().fetchAlumniPic(retrieveToken(),id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        bitmapMutableLiveData.postValue(BitmapFactory.decodeStream(response.body().byteStream()));
                    }
                    else bitmapMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return bitmapMutableLiveData.getValue();
    }
    public Bitmap getPostImage(int id){

    Call<ResponseBody> call = RetrofitClient.getUserService().fetchPostImage(retrieveToken(),id);
    call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()){

                if (response.body() != null) {
                    // display the image data in a ImageView or save it
                    bitmapPostImageMutableLiveData.postValue(BitmapFactory.decodeStream(response.body().byteStream()));
                }
                else bitmapPostImageMutableLiveData.postValue(null);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });
    return bitmapPostImageMutableLiveData.getValue();
    }
}