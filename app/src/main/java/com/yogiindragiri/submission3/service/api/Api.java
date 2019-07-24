package com.yogiindragiri.submission3.service.api;

import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.service.model.Result;
import com.yogiindragiri.submission3.service.model.ResultTv;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://api.themoviedb.org/3/discover/";
    String API_KEY = "8da45fa62701ad2c3f1a8c5b51eff482";

    @GET("movie")
    public Call<Result> getMovies(@Query("api_key") String api_key);

    @GET("tv")
    public Call<ResultTv> getTvShow(@Query("api_key") String api_key);
}
