package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.com.alumnimanagmentsystem.R;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView newsImageView;
    private TextView newsTitle;
    private TextView newsDescription;

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
        imageView = getIntent().getIntExtra("imageID",0);

        newsImageView.setImageResource(imageView);
        newsTitle.setText(title);
        newsDescription.setText(desc);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateShrink(this);
    }
}