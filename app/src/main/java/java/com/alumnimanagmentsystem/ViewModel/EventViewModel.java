package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.EventModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends AndroidViewModel {

    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    MutableLiveData<List<EventModel>> listEventModelMutableLiveData = new MutableLiveData<>();
    public int offset = 0;
    public int limit = 5;
    private boolean hasMoreData = true;

    public EventViewModel(@NonNull Application application) {
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

      Call<List<EventModel>> call = RetrofitClient.getUserService().getEvents(retrieveToken(),limit,offset);
        call.enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {

                if (!hasMoreData) {
                    return;
                }
                if (response.isSuccessful() && response.body() != null) {
                    List<EventModel> items;
                    items = response.body();
                    if (items.size() < limit) {
                        hasMoreData = false;
                    }
                    List<EventModel> currentList = listEventModelMutableLiveData.getValue();
                    if (currentList == null) {
                        currentList = new ArrayList<>();
                    }
                    currentList.addAll(items);
                   listEventModelMutableLiveData.setValue(currentList);
                }
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<EventModel>> getEvents(){
        return listEventModelMutableLiveData;
    }
}
