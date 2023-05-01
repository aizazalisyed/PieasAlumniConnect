package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.R;
import java.com.alumnimanagmentsystem.RealPathUtil;
import java.com.alumnimanagmentsystem.ViewModel.AlumnusViewModel;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPostActivity extends AppCompatActivity {
    ImageView cameraButton;
    EditText descriptionEditText;
    ImageView imageView;
    ImageView galleryButton;
    String path;
    Button postButton;
    ProgressBar progressBar;
    TextView user_name;
    TextView user_department;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    AlumnusViewModel alumnusViewModel;
    ImageView user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);


        cameraButton = findViewById(R.id.cameraButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        imageView = findViewById(R.id.image_view);
        galleryButton = findViewById(R.id.galleryButton);
        postButton = findViewById(R.id.postButton);
        descriptionEditText.requestFocus();
        showSoftKeyboard(descriptionEditText);
        progressBar = findViewById(R.id.progressBar);
        user_name = findViewById(R.id.user_name);
        user_department = findViewById(R.id.user_department);
        user_photo = findViewById(R.id.user_photo);
        alumnusViewModel = new ViewModelProvider(this).get(AlumnusViewModel.class);



        alumnusViewModel.getAlumnus().observe(this, new Observer<Alumnus>() {
            @Override
            public void onChanged(Alumnus alumnus) {
                user_name.setText(alumnus.getName());
                user_department.setText(alumnus.getDegree().getDegree_name());
            }
        });

        String myProfileUrl = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000"+"/alumni/avatar/me";
        GlideUrl myPicGlideUrl = new GlideUrl(myProfileUrl, new LazyHeaders.Builder().addHeader("Authorization",retrieveToken()).build());
        Glide.with(this).load(myPicGlideUrl).placeholder(R.drawable.default_user).into(user_photo);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(UploadPostActivity.this)
                        .cameraOnly()
                        .start();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 10);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && resultCode == Activity.RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            path = RealPathUtil.getRealPath(UploadPostActivity.this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
           imageView.setImageBitmap(bitmap);
            //sendPicViaApi();
        }
        if(requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            path = RealPathUtil.getRealPath(this, uri);
            imageView.setImageURI(uri);
            //sendPicViaApi();
        }
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void postData(){
        progressBar.setVisibility(View.VISIBLE);
        MultipartBody.Part body = null;
        RequestBody titleBody = null;

        // Check if image path is not null and create MultipartBody.Part
        if(path != null){
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("filename",file.getName(),requestFile);
        }

        // Check if title is not null and create RequestBody
        if(descriptionEditText.getText() != null){
            titleBody = RequestBody.create(MediaType.parse("text/plain"), descriptionEditText.getText().toString());
        }

        Call<ResponseBody> call = RetrofitClient.getUserService().createDiscussionPost(retrieveToken(), body, titleBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(UploadPostActivity.this, "Post Created", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(UploadPostActivity.this, MainActivity.class);
                intent.putExtra("postUploaded", true);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UploadPostActivity.this, "Post Can't be Created", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}