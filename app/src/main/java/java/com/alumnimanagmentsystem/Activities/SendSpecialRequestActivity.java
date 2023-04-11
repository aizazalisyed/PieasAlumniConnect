package java.com.alumnimanagmentsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.SpecialRequest;
import java.com.alumnimanagmentsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendSpecialRequestActivity extends AppCompatActivity {

    Button submitButton;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    EditText titleEditTextSpecialRequest;
    EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request);

        submitButton = findViewById(R.id.submitButton);
        titleEditTextSpecialRequest= findViewById(R.id.titleEditTextSpecialRequest);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        submitButton.setOnClickListener(v -> {
            if(titleEditTextSpecialRequest.getText().toString().isEmpty() || descriptionEditText.getText().toString().isEmpty()){
                Toast.makeText(this, "Incomplete Information", Toast.LENGTH_SHORT).show();
            }
            else makeApiCall();
        });
    }

    private void switchToRequestHistoryActivity(){

        Intent switchActivityIntent = new Intent(this, SpecialRequestHistoryActivity.class);
        startActivity(switchActivityIntent);
    }

    private void makeApiCall(){

        SpecialRequest specialRequest = new SpecialRequest(titleEditTextSpecialRequest.getText().toString(),descriptionEditText.getText().toString());
        Call<SpecialRequest> call = RetrofitClient.getUserService().postSpecialRequest(retrieveToken(),specialRequest);

        call.enqueue(new Callback<SpecialRequest>() {
            @Override
            public void onResponse(Call<SpecialRequest> call, Response<SpecialRequest> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SendSpecialRequestActivity.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                    switchToRequestHistoryActivity();

                }
            }

            @Override
            public void onFailure(Call<SpecialRequest> call, Throwable t) {

            }
        });
    }
    public String retrieveToken(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}