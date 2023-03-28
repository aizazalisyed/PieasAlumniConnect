package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.com.alumnimanagmentsystem.R;

public class SpecialRequest extends AppCompatActivity {

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request);

        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            switchToMainActivity();
            Toast.makeText(SpecialRequest.this, "Request Submitted", Toast.LENGTH_SHORT).show();
        });
    }

    private void switchToMainActivity(){

        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}