package com.android.maintenancesolution.Network;

import com.android.maintenancesolution.Models.CustomerRequest;
import com.android.maintenancesolution.Models.GenericResponse;
import com.android.maintenancesolution.Models.Job;
import com.android.maintenancesolution.Models.Order;
import com.android.maintenancesolution.Models.PostLocationRequest;
import com.android.maintenancesolution.Models.PostLocationResponse;
import com.android.maintenancesolution.Models.Token;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * NetworkAPi interface contains the Network API Calls
 **/
public interface NetworkApi {
    /**
     * authNetwork sends username and password to authenticate and get the tokens
     *
     * @param username need to be the username.
     * @param password need to be the password
     **/
    @FormUrlEncoded
    @POST("/token/")
    Call<Token> authNetwork(@Field("username") String username, @Field("password") String password);

    @GET("/gm/job_types/")
    Call<List<Job>> getJobTypes();

    @POST("/verify/")
    Call<Token> verify(@Body Token token);

    @Multipart
    @POST("/gm/post_job_requests/")
    Call<Token> customerFormSubmit(
            @Part("name") String name,
            @Part("address") String address,
            @Part("email") String email,
            @Part("phone") String phone,
            @Part("notes") String description,
            @Part("types") String types,
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2);

    @GET("/app/customers/")
    Call<List<CustomerRequest>> getCustomerInfo(@Query("search") String email);



   /* @POST("/gm/post_job_requests/")
    Call<Token> customerFormSubmit(
            );*/
    /*@POST("/gm/post_job_requests/")
    Call<Token> customerFormSubmit(
            @Body CustomerRequest customerRequest);*/
   /*
    @Headers("Content-Type: application/json")
    @POST("/api/users_register/")
    Call<SignupResponse> userRegisterNetwork(@Body SignupRequest signupRequest);

    @POST("/api/students_register/")
    Call<SignupResponse> userRegisterNetwork(@Header("Authorization") String accessToken, @Body SignupRequest signupRequest);*/

    @GET("/gm/user_works/")
    Call<List<Order>> getUserWorks(@Header("Authorization") String header);

    @POST("/gm/works/{id}/")
    Call<PostLocationResponse> postLocation(
            @Header("Authorization") String header
            , @Path("id") String resource_id
            , @Body PostLocationRequest postLocationRequest);

    //@Multipart
    @POST("/api/works/{id}/")
    Call<GenericResponse> postSignNetwork(@Header("Authorization") String accessToken,
                                          @Path("id") String resource_id,
                                          /*@Part("evaluation") String evaluation,
                                          @Part("end_time") String time,*/
                                          @Part MultipartBody.Part sign);
                                         /*  @Body RequestBody a);*/

    @GET("/gm/works/{id}/")
    Call<Order> getSelectedUserWork(@Header("Authorization") String accessToken,
                                    @Path("id") Integer resource_id);

    @POST("/gm/works/{id}/")
    Call<GenericResponse> postTypesReport(@Header("Authorization") String accessToken,
                                          @Path("id") String resource_id,
                                          @Body Order item
                                          /*@Query("id") String id,
                                          @Query("job_types")String types,
                                          @Query("report")String report)*/);

    @Multipart
    @POST("/gm/works/{id}/")
    Call<GenericResponse> postBeforeImagesNetwork(@Header("Authorization") String accessToken,
                                                  @Path("id") int resource_id,

                                                  @Part MultipartBody.Part photoB1,
                                                  @Part MultipartBody.Part photoB2,
                                                  @Part MultipartBody.Part photoB3,
                                                  @Part MultipartBody.Part photoB4,
                                                  @Part MultipartBody.Part photoB5,
                                                  @Part MultipartBody.Part photoB6,
                                                  @Part MultipartBody.Part photoB,
                                                  @Part MultipartBody.Part photoB8,
                                                  @Part MultipartBody.Part photoA1,
                                                  @Part MultipartBody.Part photoA2,
                                                  @Part MultipartBody.Part photoA3,
                                                  @Part MultipartBody.Part photoA4,
                                                  @Part MultipartBody.Part photoA5,
                                                  @Part MultipartBody.Part photoA6,
                                                  @Part MultipartBody.Part photoA7,
                                                  @Part MultipartBody.Part photoA8);

    /*@Multipart
    @POST("/api/works/{id}/")
    Call<GenericResponse> postAfterImagesNetwork(@Header("Authorization") String accessToken,
                                                       @Path("id") int resource_id,

                                                       @Part MultipartBody.Part photo1,
                                                       @Part MultipartBody.Part photo2,
                                                       @Part MultipartBody.Part photo3,
                                                       @Part MultipartBody.Part photo4,
                                                       @Part MultipartBody.Part photo5,
                                                       @Part MultipartBody.Part photo6,
                                                       @Part MultipartBody.Part photo7,
                                                       @Part MultipartBody.Part photo8);*/
}
