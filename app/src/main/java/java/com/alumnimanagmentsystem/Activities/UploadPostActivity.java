package java.com.alumnimanagmentsystem.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.denzcoskun.imageslider.ImageSlider;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.com.alumnimanagmentsystem.R;

public class UploadPostActivity extends AppCompatActivity {
    ImageView cameraButton;
    EditText descriptionEditText;
    ImageSlider imageSlider;
    ImageView imageView;
    ImageView galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);


        cameraButton = findViewById(R.id.cameraButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        imageSlider = findViewById(R.id.image_view);
        imageView = findViewById(R.id.image_view_single);
        galleryButton = findViewById(R.id.galleryButton);

        descriptionEditText.requestFocus();
        showSoftKeyboard(descriptionEditText);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UploadPostActivity.this)
                        .cameraOnly()
                        .start();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FishBun.with(UploadPostActivity.this).setImageAdapter(new GlideAdapter())
                        .setMaxCount(10)
                        .setMinCount(1)
                        .startAlbumWithOnActivityResult(v.hashCode());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //todo: api sy connect karni ha
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}