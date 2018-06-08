package com.maintenancesolution.ems.Network;

import com.maintenancesolution.ems.Models.ActiveClockResponse;
import com.maintenancesolution.ems.Models.Area;
import com.maintenancesolution.ems.Models.Asset;
import com.maintenancesolution.ems.Models.Center;
import com.maintenancesolution.ems.Models.CheckAssetRequest;
import com.maintenancesolution.ems.Models.CountAssetsRequest;
import com.maintenancesolution.ems.Models.CustomerRequest;
import com.maintenancesolution.ems.Models.GenericResponse;
import com.maintenancesolution.ems.Models.Job;
import com.maintenancesolution.ems.Models.MoveAssetsRequest;
import com.maintenancesolution.ems.Models.Order;
import com.maintenancesolution.ems.Models.PostLocationRequest;
import com.maintenancesolution.ems.Models.PostLocationResponse;
import com.maintenancesolution.ems.Models.SignupRequest;
import com.maintenancesolution.ems.Models.SignupResponse;
import com.maintenancesolution.ems.Models.Token;
import com.maintenancesolution.ems.Models.UpdateAssetLocationRequest;
import com.maintenancesolution.ems.Models.UpdateTimeRequest;

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
import retrofit2.http.PUT;
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

    @GET("/gm/job_types/?no_pagination=True")
    Call<List<Job>> getJobTypes();

    @POST("/verify/")
    Call<Token> verify(@Body Token token);

    @POST("/app/users/new/")
    Call<SignupResponse> newUserSignup(@Body SignupRequest signupRequest);

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
            @Part MultipartBody.Part photo2,
            @Part MultipartBody.Part photo3,
            @Part MultipartBody.Part photo4);

    @Multipart
    @POST("/gm/job_requests/")
    Call<Token> customerFormSubmitnew(
            @Header("Authorization") String accessToken,
            @Part("center_id") int center_id,
            @Part("name") String name,
            @Part("email") String email,
            @Part("phone") String phone,
            @Part("address") String address,
            @Part("notes") String description,
            @Part("types") String types,
            @Part("not_to_exceed") int notToExceed,
            @Part("is_approved_from_app") int approvedFromApp,
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2,
            @Part MultipartBody.Part photo3,
            @Part MultipartBody.Part photo4);

    @Multipart
    @POST("/gm/post_job_requests/")
    Call<Token> customerFormSubmit(
            @Header("Authorization") String accessToken,
            @Part("name") String name,
            @Part("address") String address,
            @Part("email") String email,
            @Part("phone") String phone,
            @Part("notes") String description,
            @Part("types") String types,
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2,
            @Part MultipartBody.Part photo3,
            @Part MultipartBody.Part photo4);

    @GET("/app/customers?no_pagination=True")
    Call<List<CustomerRequest>> getCustomerInfoByEmail(@Query("email") String email);

    @GET("/app/customers?no_pagination=True")
    Call<List<CustomerRequest>> getCustomerInfoByPhone(@Query("phone") String phone);

    @GET("/app/customers?no_pagination=True")
    Call<List<CustomerRequest>> getCustomerInfoByName(@Query("name") String name);



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

    @GET("/gm/user_works/?no_pagination=True")
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

    @GET("/gm/centers/?no_pagination=True")
    Call<List<Center>> getCenters(@Header("Authorization") String accessToken);

    @GET("/app/timeclock/active/")
    Call<ActiveClockResponse> getActiveClock(@Header("Authorization") String accessToken);

    @POST("/app/timeclocks/")
    Call<ActiveClockResponse> postTimeClock(@Header("Authorization") String accessToken, @Body UpdateTimeRequest updateTimeRequest);

    @PUT("/app/timeclocks/{id}/")
    Call<ActiveClockResponse> updateTimeClock(@Header("Authorization") String accessToken, @Body UpdateTimeRequest updateTimeRequest, @Path("id") String id);


    @GET("/inv/areas/?no_pagination=True")
    Call<List<Area>> getAreas(@Header("Authorization") String accessToken);

    @GET("/inv/assets/{id}/")
    Call<Asset> getAsset(@Header("Authorization") String accessToken, @Path("id") String id);

    @POST("/inv/assets/{id}/")
    Call<Asset> updateAssetLocation(@Header("Authorization") String accessToken, @Path("id") String id, @Body UpdateAssetLocationRequest updateAssetLocationRequest);


    @POST("/inv/assets_check_current_area/")
    Call<Asset> checkAsset(@Header("Authorization") String accessToken, @Body CheckAssetRequest checkAssetRequest);

    @POST("/inv/assets_counting_details_assets/")
    Call<Asset> countAssets(@Header("Authorization") String accessToken, @Body CountAssetsRequest countAssetsRequest);

    @POST("/inv/assets_moving_assets/")
    Call<Asset> moveAssets(@Header("Authorization") String accessToken, @Body MoveAssetsRequest moveAssetsRequest);

    @GET("/app/users/check/")
    Call<SignupResponse> checkUser(@Header("Authorization") String accessToken);

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
