package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListViewModel extends AndroidViewModel {

    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    MutableLiveData<List<JobModel>> jobModelList = new MutableLiveData<>();
    public int offset = 0;
    public int limit = 4;
    private boolean hasMoreData = true;

    public JobListViewModel(@NonNull Application application) {
        super(application);

        context = application;
        makeApiCall();
    }

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
    public void makeApiCall(){

        if (!hasMoreData) {
            return;
        }

        Call<List<JobModel>> call = RetrofitClient.getUserService().getAllJobs(retrieveToken(), limit, offset);
        call.enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<JobModel> items;
                    items = response.body();
                    if (items.size() < limit) {
                        hasMoreData = false;
                    }
                    List<JobModel> currentList = jobModelList.getValue();
                    if (currentList == null) {
                        currentList = new ArrayList<>();
                    }
                    currentList.addAll(items);
                    jobModelList.setValue(currentList);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {

            }
        });
    }
    public LiveData<List<JobModel>> getJobList(){
        return jobModelList;
    }

}
