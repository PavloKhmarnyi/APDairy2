package com.example.pavlo.apdairy2.nasa_apod_api.api;

import com.example.pavlo.apdairy2.nasa_apod_api.models.Data;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pavlo on 08.06.16.
 */
public interface ApiService {

    @GET("planetary/apod")
    Observable<Data> getDataOfDay(@Query("api_key") String apiKey, @Query("date") String date);
}
