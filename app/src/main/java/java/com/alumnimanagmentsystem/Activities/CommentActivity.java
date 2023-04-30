package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.com.alumnimanagmentsystem.Model.Comment;
import java.com.alumnimanagmentsystem.Model.CommentActivityRVModel;
import java.com.alumnimanagmentsystem.Model.PostThreadModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.com.alumnimanagmentsystem.RVAdapter.CommentRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RVAdapter.DiscussionPanelRVAdapter;
import java.com.alumnimanagmentsystem.ViewModel.DiscussionPanelViewModel;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userDegree;
    private TextView userCaption;
    private ImageView userDp;
    private ImageView userImagePost;
    private ImageView sendButtonImageView;
    TextView comment_count;
    BottomNavigationView bottomNavigationView;
    TextView post_time;
    DiscussionPanelViewModel discussionPanelViewModel;
    RecyclerView recyclerView;
    CommentRVAdapter commentRVAdapter;
    TextView comment_input_edit_text;
    ImageView user_photo_comment_edit_text;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    int postID;
    int fragmentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);


        userName = findViewById(R.id.panel_user_name);
        userDegree = findViewById(R.id.panel_user_department);
        userCaption = findViewById(R.id.panel_discription_text_view);
        userDp = findViewById(R.id.panel_user_photo);
        userImagePost = findViewById(R.id.panel_image_view);
        sendButtonImageView = findViewById(R.id.sendButtonImageView);
        bottomNavigationView = findViewById(R.id.bottom_nav_panel);
        post_time = findViewById(R.id.post_time);
        comment_count = findViewById(R.id.comment_count);
        comment_input_edit_text = findViewById(R.id. comment_input_edit_text);
        user_photo_comment_edit_text = findViewById(R.id.user_photo_comment_edit_text);
        discussionPanelViewModel = ViewModelProviders.of(this).get(DiscussionPanelViewModel.class);
        int fragmentType = getIntent().getIntExtra("fragment_type", -1);



        recyclerView = findViewById(R.id.comment_activity_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        postID = extras.getInt("postId");

        discussionPanelViewModel.getPosts().observe(this, new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModelList) {
                if(postsModelList.get(extras.getInt("position")).getThreads().size() !=0){

                    putDataIntoRecyclerView(postsModelList.get(extras.getInt("position")).getThreads());
                    recyclerView.setVisibility(View.VISIBLE);
                }
                comment_count.setText(Integer.toString(postsModelList.get(extras.getInt("position")).getThreads().size()));
            }
        });

        String myProfileUrl = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000"+"/alumni/avatar/me";
        GlideUrl myPicGlideUrl = new GlideUrl(myProfileUrl, new LazyHeaders.Builder().addHeader("Authorization", extras.getString("Token")).build());
        Glide.with(this).load(myPicGlideUrl).placeholder(R.drawable.default_user).into(user_photo_comment_edit_text);




        userName.setText(extras.getString("userName"));
        if (extras.get("userDegree").toString().isEmpty()) {
            userDegree.setVisibility(View.GONE);
            String urlAlumniProfile = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000" + "/alumni/" + extras.getInt("userId") + "/avatar";
            GlideUrl glideUrl = new GlideUrl(urlAlumniProfile, new LazyHeaders.Builder().addHeader("Authorization", extras.getString("Token")).build());
            Glide.with(this).load(glideUrl).placeholder(R.drawable.default_user).into(userDp);
        } else {
            userDegree.setText(extras.getString("userDegree"));
        }
        post_time.setText(extras.getString("post_time"));
        if (extras.get("userCaption").toString().isEmpty()) {
            userCaption.setVisibility(View.GONE);
        } else {
            userCaption.setText(extras.getString("userCaption"));
        }
        String url = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000/postimages/" + extras.getInt("postId");

        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Authorization", extras.getString("Token")).build());
        Glide.with(this).load(glideUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                userImagePost.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                userImagePost.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(userImagePost);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id;
                id = item.getItemId();
                if (id == R.id.nav_comment) {
                   comment_input_edit_text.requestFocus();
                    showSoftKeyboard(comment_input_edit_text);

                } else {
                    reportComment();
                }
                return false;
            }
        });


            sendButtonImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!comment_input_edit_text.getText().toString().isEmpty()){
                        postComment(comment_input_edit_text.getText().toString());
                    }
                }
            });

    }

    private void reportComment() {
        new AlertDialog.Builder(this).setTitle("Report Post").setMessage("Are you sure you want to report this post?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    private void putDataIntoRecyclerView(ArrayList<PostThreadModel> postThreadModelArrayList){

       commentRVAdapter = new CommentRVAdapter(postThreadModelArrayList, this);
        recyclerView.setAdapter(commentRVAdapter);
    }
    private void postComment(String commentString){

        Comment comment = new Comment(commentString);
        Call<ResponseBody> call = RetrofitClient.getUserService().postComment(retrieveToken(), comment, postID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(CommentActivity.this, "comment posted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                Animatoo.INSTANCE.animateFade(CommentActivity.this);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CommentActivity.this, "comment not posted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String retrieveToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        if (fragmentType == 1) {
            // Create a new instance of the fragment
            DiscussionPanelFragment discussionPanelFragment = new DiscussionPanelFragment();

            // Start a new FragmentTransaction to add the fragment to the container view
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, discussionPanelFragment)
                    .commit();
        }
    }
}