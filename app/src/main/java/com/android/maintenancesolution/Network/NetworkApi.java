package com.android.maintenancesolution.Network;

import com.android.maintenancesolution.Models.CustomerRequest;
import com.android.maintenancesolution.Models.Job;
import com.android.maintenancesolution.Models.Order;
import com.android.maintenancesolution.Models.PostLocationRequest;
import com.android.maintenancesolution.Models.PostLocationResponse;
import com.android.maintenancesolution.Models.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @POST("/api/token/")
    Call<Token> authNetwork(@Field("username") String username, @Field("password") String password);

    @GET("/api/job_types/")
    Call<List<Job>> getJobTypes();

    @POST("/api/verify/")
    Call<Token> verify(@Body Token token);

    @POST("/api/request/")
    Call<Token> customerFormSubmit(@Body CustomerRequest customerRequest);
   /*
    @Headers("Content-Type: application/json")
    @POST("/api/users_register/")
    Call<SignupResponse> userRegisterNetwork(@Body SignupRequest signupRequest);

    @POST("/api/students_register/")
    Call<SignupResponse> userRegisterNetwork(@Header("Authorization") String accessToken, @Body SignupRequest signupRequest);*/

    @GET("/api/user_works/")
    Call<List<Order>> getUserWorks(@Header("Authorization") String header);

    @POST("/api/works/{id}/")
    Call<PostLocationResponse> postLocation(
            @Header("Authorization") String header
            , @Path("id") String resource_id
            , @Body PostLocationRequest postLocationRequest);
}
