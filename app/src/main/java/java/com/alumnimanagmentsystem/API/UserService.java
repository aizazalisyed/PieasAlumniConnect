package java.com.alumnimanagmentsystem.API;

import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import okhttp3.internal.http1.HeadersReader;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface UserService {


    @POST("/alumni/login")
    Call<LoginResponse>  userLogin(@Body LoginRequest loginRequest);

    @GET("/alumni/avatar/me")
    Call<ResponseBody> fetchCaptcha(@Header("Authorization") String authToken);

    @GET("/alumni/me")
    Call<Alumnus> getAlumnus(@Header("Authorization") String authToken);

    @POST("/alumni/job-history")
        Call<AlumniJobHistories> postJobHistroy(@Header("Authorization") String authToken,@Body AlumniJobHistories alumniJobHistories);

    @POST("/alumni/achievements")
    Call<AlumniAchievements> postAchievement(@Header("Authorization") String authToken,@Body AlumniAchievements alumniAchievements);

    @Multipart
    @POST("/alumni/me/avatar")
    Call<ResponseBody> postImage(@Header("Authorization") String authToken , @Part MultipartBody.Part photo);

    @PATCH("/alumni/job-history/{id}")
    Call<AlumniJobHistories> patchJobHistories(@Header("Authorization") String authToken, @Body AlumniJobHistories alumniJobHistories,@Path("id") int id);

    @DELETE("/alumni/job-history/{id}")
    Call<AlumniJobHistories> deleteJobHistories(@Header("Authorization") String authToken,@Path("id") int id);

    @DELETE("/alumni/achievements/{id}")
    Call<AlumniAchievements> deleteAchievement(@Header("Authorization") String authToken,@Path("id") int id);

    @PATCH("/alumni/achievements/{id}")
    Call<AlumniAchievements> patchAchievement(@Header("Authorization") String authToken, @Body AlumniAchievements alumniAchievements,@Path("id") int id);





}
