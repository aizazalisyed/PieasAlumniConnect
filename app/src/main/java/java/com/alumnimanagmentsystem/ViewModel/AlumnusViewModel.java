package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnusViewModel extends AndroidViewModel {

    MutableLiveData<Alumnus> alumnus = new MutableLiveData<>();
    MutableLiveData<Bitmap> bitmapMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<AlumniJobHistories>> alumniJobHistories = new MutableLiveData<>();
    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    //constructor

    public AlumnusViewModel(Application application) {
        super(application);
        // alumnusRepository = new AlumnusRepository(application);
        context = application;
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
    public LiveData<Alumnus> getAlumnus(){
        Call<Alumnus> alumnusCall = RetrofitClient.getUserService().getAlumnus(retrieveToken());
        alumnusCall.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
                alumnus.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Alumnus> call, Throwable t) {

            }
        });

        return alumnus;
    }

    public LiveData<Bitmap> getProfilePic(){

        Call<ResponseBody> call = RetrofitClient.getUserService().fetchCaptcha(retrieveToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        bitmapMutableLiveData.postValue(BitmapFactory.decodeStream(response.body().byteStream()));
                    } else {
                        // TODO
                    }
                }
                else {
                    // TODO
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return bitmapMutableLiveData;
    }




}
