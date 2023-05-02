package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.com.alumnimanagmentsystem.R;

public class EventDetailActivity extends AppCompatActivity {

    private ImageView newsImageView;
    private TextView newsTitle;
    private TextView newsDescription;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    private String title, desc;
    private int imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newsImageView = findViewById(R.id.newsImageView);
        newsTitle = findViewById(R.id.newsHeadingTextView);
        newsDescription = findViewById(R.id.descriptionTextView);


        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        imageView = getIntent().getIntExtra("eventID",-1);

        String url = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000/event-image/" + imageView;


        GlideUrl glideUrl = new GlideUrl(url,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", retrieveToken())
                        .build());
        Glide.with(this)
                .load(glideUrl)
                .placeholder(R.drawable.default_imag)
                .into(newsImageView);
        newsTitle.setText(title);

        newsDescription.setText(desc);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateShrink(this);
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}