package java.com.alumnimanagmentsystem.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.com.alumnimanagmentsystem.API.RetrofitClient;
import java.com.alumnimanagmentsystem.Model.EligibilityDiscipline;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EligibilityDisciplineViewModel extends AndroidViewModel {

    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";
    MutableLiveData<List<EligibilityDiscipline>> eligibilityDisciplineList = new MutableLiveData<>();

    public EligibilityDisciplineViewModel(@NonNull Application application) {
        super(application);

        context = application;
        makeApiCall();

    }

    public void makeApiCall(){
        Call<List<EligibilityDiscipline>> call = RetrofitClient.getUserService().getEligibilityDisciplines(retrieveToken());
        call.enqueue(new Callback<List<EligibilityDiscipline>>() {

            @Override
            public void onResponse(Call<List<EligibilityDiscipline>> call, Response<List<EligibilityDiscipline>> response) {


                if(response.isSuccessful()){
                     eligibilityDisciplineList.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<EligibilityDiscipline>> call, Throwable t) {

            }
        });
    }
    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }

    public LiveData<List<EligibilityDiscipline>> getEligibilityDisciplines(){
        return eligibilityDisciplineList;
    }
}
