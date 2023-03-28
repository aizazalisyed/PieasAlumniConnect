package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.Alumnus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnusViewModel extends AndroidViewModel {

    public MutableLiveData<Alumnus> alumnus;
    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    //constructor

    public AlumnusViewModel(Application application) {
        super(application);
        alumnus = new MutableLiveData<>();
        this.context = application;
        makeApiCall();
    }

    public MutableLiveData<Alumnus> getAlumnusObserver()
    {
        return alumnus;
    }

    public void makeApiCall(){
        Call<Alumnus> alumnusCall = RetrofitClient.getUserService().getAlumnus(retrieveToken());
        alumnusCall.enqueue(new Callback<Alumnus>() {
            @Override
            public void onResponse(Call<Alumnus> call, Response<Alumnus> response) {
                alumnus.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Alumnus> call, Throwable t) {
                alumnus.postValue(null);
            }
        });
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

}
