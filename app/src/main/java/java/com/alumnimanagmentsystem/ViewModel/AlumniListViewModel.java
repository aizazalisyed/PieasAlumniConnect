package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.Department;
import java.com.alumnimanagmentsystem.Model.ProfilePicture;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumniListViewModel extends AndroidViewModel {

    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    MutableLiveData<List<Alumnus>> alumnusList = new MutableLiveData<>();
    MutableLiveData<List<Department>> departmentList = new MutableLiveData<>();
    public int offset = 0;
    int limit = 15;

    public AlumniListViewModel(@NonNull Application application) {
        super(application);

        context = application;
        fetchDepartments();
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
    private void fetchDepartments(){
        Call<List<Department>> call = RetrofitClient.getUserService().getDepartments(retrieveToken());
        call.enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                if(response.isSuccessful()){
                    departmentList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {

            }
        });
    }
     public LiveData<List<Department>> getDepartment(){
        return departmentList;
     }
}
