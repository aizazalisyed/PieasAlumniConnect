package java.com.alumnimanagmentsystem.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.API.LoginRequest;
import java.com.alumnimanagmentsystem.API.LoginResponse;
import java.com.alumnimanagmentsystem.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView signUpTextView;
    Button loginButton;
    EditText userEmail;
    EditText userPassword;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpTextView = findViewById(R.id.signUpTextView);
        loginButton = findViewById(R.id.loginButton);
        userEmail = findViewById(R.id.inputEmail);
        userPassword = findViewById(R.id.inputPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(userEmail.getText().toString()) || TextUtils.isEmpty(userPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Email / Password Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    //proceed to login
                    login();
                }

            }
        });


        signUpTextView.setOnClickListener(view -> {
                    switchToRegisterActivity();

                }

        );
    }

    private void login(){

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userEmail.getText().toString());
        loginRequest.setPassword(userPassword.getText().toString());

        Call<LoginResponse> loginResponseCall  = RetrofitClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    saveToken(response.body().getToken());
                    switchToMainActivity();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful : Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("login failed", t.getLocalizedMessage());
            }
        });

    }

    private void switchToRegisterActivity() {
        Intent switchActivityIntent = new Intent(this, RegisterActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchToMainActivity(){

        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
    public void saveToken(String token){
        SharedPreferences sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, token);
        editor.apply();
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}
