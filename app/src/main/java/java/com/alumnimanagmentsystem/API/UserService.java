package java.com.alumnimanagmentsystem.API;

import java.com.alumnimanagmentsystem.Model.Alumnus;

import okhttp3.ResponseBody;
import okhttp3.internal.http1.HeadersReader;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface UserService {


    @POST("/alumni/login")
    Call<LoginResponse>  userLogin(@Body LoginRequest loginRequest);

    @GET("/alumni/avatar/me")
    Call<ResponseBody> fetchCaptcha(@Header("Authorization") String authToken);

    @GET("/alumni/me")
    Call<Alumnus> getAlumnus(@Header("Authorization") String authToken);
}
