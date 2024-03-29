package java.com.alumnimanagmentsystem.Activities;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.ViewModel.AlumnusViewModel;
import java.com.alumnimanagmentsystem.ViewModel.MainActivityViewModel;
import java.com.alumnimanagmentsystem.R;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView userNameHeaderLayout;
    ImageView nav_user_photo;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    ImageView toolBarDrawerIcon;
    MainActivityViewModel mainActivityViewModel;
    AlumnusViewModel alumnusViewModel;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        alumnusViewModel = new ViewModelProvider(this).get(AlumnusViewModel.class);




        // setting action bar
        setSupportActionBar(toolbar);

        // setting the toggling effect
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.closeDrawer, R.string.openDrawer);
        toggle.syncState();


        //setting on click listener in the header view;
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(v -> switchToProfileActivity());

        userNameHeaderLayout = headerView.findViewById(R.id.userNameHeaderLayout);
        nav_user_photo = headerView.findViewById(R.id.nav_user_photo);

        String myProfileUrl = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000"+"/alumni/avatar/me";
        GlideUrl myPicGlideUrl = new GlideUrl(myProfileUrl, new LazyHeaders.Builder().addHeader("Authorization",retrieveToken()).build());
        Glide.with(this).load(myPicGlideUrl).placeholder(R.drawable.default_user).into(nav_user_photo);

        alumnusViewModel.getAlumnus().observe(this, new Observer<Alumnus>() {
            @Override
            public void onChanged(Alumnus alumnus) {
                userNameHeaderLayout.setText(alumnus.getName());
            }
        });

        // setting on click listener on the menu items in drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.nav_request){
                switchToSpecialRequestHistoryActivity();
            }
            else if(id == R.id.nav_jobs){
               switchToJobListActivity();
            }
            else {
                switchToLoginActivity();

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });

        // Load HomeFragment if the app is loaded for the first time
        if (!mainActivityViewModel.isFirstTimeLoad) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
            mainActivityViewModel.isFirstTimeLoad = true;
        }

        boolean postUploaded = getIntent().getBooleanExtra("postUploaded", false);
        if (postUploaded) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new DiscussionPanelFragment()).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Discussion Panel");
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_panel);
        }

        boolean GettingBackCommentActivity = getIntent().getBooleanExtra("GettingBackCommentActivity", false);
        if(GettingBackCommentActivity){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new DiscussionPanelFragment()).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Discussion Panel");
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_panel);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {

            mainActivityViewModel.navigationItemClick = true;

            int id;
            id = item.getItemId();

            if(id == R.id.bottom_nav_home){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            }
            else if(id == R.id.bottom_nav_search_alumni){

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlumniListFragment()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Alumni");
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new DiscussionPanelFragment()).commit();
                Objects.requireNonNull(getSupportActionBar()).setTitle("Discussion Panel");
            }
            return true;
        });

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
            finishAffinity();
            finish();
        }
    }

    private void switchToProfileActivity(){

        Intent switchActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchToSpecialRequestHistoryActivity(){

        Intent switchActivityIntent = new Intent(this, SpecialRequestHistoryActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchToLoginActivity(){

        Intent switchActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchToJobListActivity(){
        Intent switchActivityIntent = new Intent(this, JobListActivity.class);
        startActivity(switchActivityIntent);
    }
    public String retrieveToken(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}