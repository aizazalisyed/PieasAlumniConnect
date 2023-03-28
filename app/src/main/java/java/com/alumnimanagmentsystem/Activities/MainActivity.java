package java.com.alumnimanagmentsystem.Activities;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.com.alumnimanagmentsystem.ViewModel.MainActivityViewModel;
import java.com.alumnimanagmentsystem.R;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    ImageView toolBarDrawerIcon;
    MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // setting action bar
        setSupportActionBar(toolbar);

        // setting the toggling effect
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.closeDrawer, R.string.openDrawer);
        toggle.syncState();


        //setting on click listener in the header view;
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(v -> switchToProfileActivity());

       // setting on click listener on the menu items in drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.nav_request){
                switchToSpecialRequestHistoryActivity();
            }
            else if(id == R.id.nav_settings){
                Toast.makeText(this, "click on settings", Toast.LENGTH_SHORT).show();
            }
            else {
                switchToLoginActivity();

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });

       if(!mainActivityViewModel.navigationItemClick) {
           getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
       }
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);

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
}