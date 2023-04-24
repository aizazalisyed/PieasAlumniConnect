package java.com.alumnimanagmentsystem.API;

import java.com.alumnimanagmentsystem.Model.AlumniAchievements;
import java.com.alumnimanagmentsystem.Model.AlumniJobHistories;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.EligibilityDiscipline;
import java.com.alumnimanagmentsystem.Model.JobModel;
import java.com.alumnimanagmentsystem.Model.PostJobModel;
import java.com.alumnimanagmentsystem.Model.SpecialRequest;
import java.com.alumnimanagmentsystem.Model.SpecialRequests;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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
import retrofit2.http.Query;

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

    @PATCH("/alumni/job-hCall<Alumnus> alumnusCall = RetrofitClient.getUserService().istory/{id}")
    Call<AlumniJobHistories> patchJobHistories(@Header("Authorization") String authToken, @Body AlumniJobHistories alumniJobHistories,@Path("id") int id);

    @DELETE("/alumni/job-history/{id}")
    Call<AlumniJobHistories> deleteJobHistories(@Header("Authorization") String authToken,@Path("id") int id);

    @DELETE("/alumni/achievements/{id}")
    Call<AlumniAchievements> deleteAchievement(@Header("Authorization") String authToken,@Path("id") int id);

    @PATCH("/alumni/achievements/{id}")
    Call<AlumniAchievements> patchAchievement(@Header("Authorization") String authToken, @Body AlumniAchievements alumniAchievements,@Path("id") int id);

    @GET("/me/special-requests")
    Call<SpecialRequests> getSpecialRequests(@Header("Authorization") String authToken);

    @POST("/special-requests")
    Call<SpecialRequest> postSpecialRequest(@Header("Authorization") String authToken, @Body SpecialRequest specialRequest);

    @GET("/alumni")
    Call<List<Alumnus>> getAllAlumni(@Query("limit") Integer limit,
                                     @Query("offset") Integer offset,
                                     @Header("Authorization") String authToken);

    @GET("/jobs")
    Call<List<JobModel>> getAllJobs(@Header("Authorization") String authToken, @Query("limit") Integer limit,
                                    @Query("offset") Integer offset);
    @GET("/job-eligibility")
    Call<List<EligibilityDiscipline>> getEligibilityDisciplines(@Header("Authorization") String authToken);

    @POST("/jobs")
    Call<PostJobModel> createJobPost(@Header("Authorization") String authToken, @Body PostJobModel jobModel);
}
