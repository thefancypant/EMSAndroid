package com.android.maintenancesolution.Network;

import com.android.maintenancesolution.Models.CustomerRequest;
import com.android.maintenancesolution.Models.Job;
import com.android.maintenancesolution.Models.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
   /* *//**
     * userRegisterNetwork is used to signup a new user
     *
     * @param signupRequest This object takes in different parameters like first name,last name etc.
     **//*
    @Headers("Content-Type: application/json")
    @POST("/api/users_register/")
    Call<SignupResponse> userRegisterNetwork(@Body SignupRequest signupRequest);

    @POST("/api/students_register/")
    Call<SignupResponse> userRegisterNetwork(@Header("Authorization") String accessToken, @Body SignupRequest signupRequest);*/


}
