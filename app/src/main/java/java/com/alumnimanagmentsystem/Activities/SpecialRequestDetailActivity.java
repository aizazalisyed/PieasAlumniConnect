package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.com.alumnimanagmentsystem.R;

public class SpecialRequestDetailActivity extends AppCompatActivity {

    TextView title, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request_detail);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        Bundle extra = getIntent().getExtras();

        title.setText(extra.getString("title"));
        description.setText(extra.getString("description"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SpecialRequestHistoryActivity.class);
        startActivity(intent);
    }
}