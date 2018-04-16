package com.maintenancesolution.ems.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * NetworkService is used to create the network client to make network calls
 **/
public class NetworkService {

    private static NetworkApi service;

    public static NetworkApi getInstance() {

        if (service == null) {
            //Interceptor added for logging network calls.
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging)
                    .readTimeout(60, TimeUnit.SECONDS);

            //Creating the retrofit client.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkContract.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(NetworkApi.class);
            return service;
        } else {

            return service;
        }
    }

}