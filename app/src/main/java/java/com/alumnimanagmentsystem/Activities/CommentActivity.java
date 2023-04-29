package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.com.alumnimanagmentsystem.Model.CommentActivityRVModel;
import java.com.alumnimanagmentsystem.RVAdapter.CommentRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userDegree;
    private TextView userCaption;
    private ImageView userDp;
    private ImageView userImagePost;
    private EditText commentInputEditText;
    private ImageView sendButtonImageView;
    TextView comment_count;
    BottomNavigationView bottomNavigationView;
    TextView post_time;


    ArrayList<CommentActivityRVModel> commentActivityRVModelArrayList;
    RecyclerView recyclerView;
    CommentRVAdapter commentRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);


        userName = findViewById(R.id.panel_user_name);
        userDegree = findViewById(R.id.panel_user_department);
        userCaption = findViewById(R.id.panel_discription_text_view);
        userDp = findViewById(R.id.panel_user_photo);
        userImagePost = findViewById(R.id.panel_image_view);
        commentInputEditText = findViewById(R.id.comment_input_edit_text);
        sendButtonImageView = findViewById(R.id.sendButtonImageView);
        bottomNavigationView = findViewById(R.id.bottom_nav_panel);
        post_time = findViewById(R.id.post_time);
        comment_count = findViewById(R.id.comment_count);


        Bundle extras = getIntent().getExtras();

        userName.setText(extras.getString("userName"));
        if (extras.get("userDegree").toString().isEmpty()) {
            userDegree.setVisibility(View.GONE);
            String urlAlumniProfile = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000" + "/alumni/" + extras.getInt("userId") + "/avatar";
            GlideUrl glideUrl = new GlideUrl(urlAlumniProfile,
                    new LazyHeaders.Builder()
                            .addHeader("Authorization", extras.getString("Token"))
                            .build());
            Glide.with(this)
                    .load(glideUrl)
                    .placeholder(R.drawable.default_user)
                    .into(userDp);
        } else {
            userDegree.setText(extras.getString("userDegree"));
        }
        post_time.setText(extras.getString("post_time"));
        if (extras.get("userCaption").toString().isEmpty()) {
            userCaption.setVisibility(View.GONE);
        } else {
            userCaption.setText(extras.getString("userCaption"));
        }
        comment_count.setText(String.valueOf(extras.getInt("commentCount")));

        String url = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000/postimages/" + extras.getInt("postId");

        GlideUrl glideUrl = new GlideUrl(url,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", extras.getString("Token"))
                        .build());
        Glide.with(this)
                .load(glideUrl)
                .listener(new RequestListener<Drawable>() {
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
                })
                .into(userImagePost);

        sendButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CommentActivity.this, "comment posted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                Animatoo.INSTANCE.animateFade(CommentActivity.this);
            }
        });

        recyclerView = findViewById(R.id.comment_activity_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setVisibility(View.GONE);

//        commentRVAdapter = new CommentRVAdapter(commentActivityRVModelArrayList, this);
//        recyclerView.setAdapter(commentRVAdapter);
//        commentRVAdapter.notifyDataSetChanged();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id;
                id = item.getItemId();
                if (id == R.id.nav_comment) {
                    commentInputEditText.requestFocus();
                    showSoftKeyboard(commentInputEditText);

                } else {
                    reportComment();
                }
                return false;
            }
        });
    }

    private void reportComment() {
        new AlertDialog.Builder(this)
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

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}