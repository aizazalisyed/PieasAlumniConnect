package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.com.alumnimanagmentsystem.Model.CommentActivityRVModel;
import java.com.alumnimanagmentsystem.RVAdapter.CommentRVAdapter;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userDegree;
    private TextView usercaption;
    private ImageView userDp;
    private ImageView userImagePost;
    private EditText commentInputEditText;
    private ImageView sendButtonImageView;
    BottomNavigationView bottomNavigationView;


    private int userProfileID;
    private int imagePost;
    private String name, degree, caption;

    ArrayList<CommentActivityRVModel> commentActivityRVModelArrayList;
    RecyclerView recyclerView;
    CommentRVAdapter commentRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);


        userName = findViewById(R.id.panel_user_name);
        userDegree = findViewById(R.id.panel_user_department);
        usercaption = findViewById(R.id.panel_discription_text_view);
        userDp = findViewById(R.id.panel_user_photo);
//        userImagePost = findViewById(R.id.panel_image_view);
        commentInputEditText = findViewById(R.id.comment_input_edit_text);
        sendButtonImageView = findViewById(R.id.sendButtonImageView);
        bottomNavigationView = findViewById(R.id.bottom_nav_panel);

        name = getIntent().getStringExtra("userName");
        degree = getIntent().getStringExtra("userDegree");
        userProfileID = getIntent().getIntExtra("userDp",0);

        //Bundle extras = getIntent().getExtras();
        //int extrasInt = extras.getInt("imagePost");
        caption = getIntent().getStringExtra("userCaption");

        userDp.setImageResource(userProfileID);
        userName.setText(name);
        userDegree.setText(degree);
        usercaption.setText(caption);
      //  userImagePost.setImageResource(extrasInt);

        /*if (extrasInt == 0){
            userImagePost.setVisibility(View.GONE);
        }*/

        Context context = this;

    sendButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CommentActivity.this, "comment posted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                Animatoo.INSTANCE.animateFade(context);
            }
        });


        initializeData();

        recyclerView = findViewById(R.id.comment_activity_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        commentRVAdapter = new CommentRVAdapter(commentActivityRVModelArrayList, this);
        recyclerView.setAdapter(commentRVAdapter);
        commentRVAdapter.notifyDataSetChanged();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id;
                id = item.getItemId();
                if(id == R.id.nav_comment){
                    commentInputEditText.requestFocus();
                    showSoftKeyboard(commentInputEditText);

                }
                else{
                    reportComment();
                }
                return false;
            }
        });
            }

    private void initializeData(){
        String name[] = new String[]{
                "Ahmed Raza",
                "Abdul Rafy",
                "Syed Aizaz Ali",
                "Ahsand Junaid"};

        int dpID[] = new int[]{
                R.drawable.user_photo,
                R.drawable.ahmedraza,
                R.drawable.abdulrafy,
                R.drawable.ahsanjunaid,
        };

        String Degree [] = new String[]{
                "Bs Computer and Information Sciences",
                "Bs Electrical Engineering",
                "Ms Cyber Security",
                "Bs Electrical Engineering"
        };
        String caption[] = new String[]{
                "keep mati clean please!!",
                "When is the result coming......",
                "we have achieved it",
                "how was the mushaira event?? kfndslknfdlk" +
                        "sknslknsklnlskn" +
                        "ksmlkslksnklsnlks" +
                        "snslknslknslkns" +
                        "lsnlksnlsknslknslknslknslnslkns" +
                        "lsnlsknlsknlsknlsnlksn"
        };

        commentActivityRVModelArrayList = new ArrayList<>();

        for (int i = 0; i < dpID.length; i++){
            CommentActivityRVModel commentActivityRVModel = new CommentActivityRVModel(dpID[i], name[i], Degree[i], caption[i]);
            commentActivityRVModelArrayList.add(commentActivityRVModel);
        }

    }
    private void reportComment(){
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