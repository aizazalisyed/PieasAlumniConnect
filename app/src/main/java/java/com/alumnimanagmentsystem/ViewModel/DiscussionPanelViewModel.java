package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionPanelViewModel extends AndroidViewModel {

    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    MutableLiveData<List<PostsModel>> mutableLiveDataPosts = new MutableLiveData<>();
    public int offset = 0;
    public int limit = 5;
    private boolean hasMoreData = true;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    public DiscussionPanelViewModel(@NonNull Application application) {
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
        Call<List<PostsModel>> call = RetrofitClient.getUserService().getPosts(retrieveToken(),limit,offset);
        call.enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PostsModel> items;
                    items = response.body();
                    if (items.size() < limit) {
                        hasMoreData = false;
                    }
                    List<PostsModel> currentList = mutableLiveDataPosts.getValue();
                    if (currentList == null) {
                        currentList = new ArrayList<>();
                    }
                    currentList.addAll(items);
                   mutableLiveDataPosts.setValue(currentList);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {

            }
        });
    }
    public LiveData<List<PostsModel>> getPosts(){
        return mutableLiveDataPosts;
    }
}
