package com.yogiindragiri.submission3.service.api;

import com.yogiindragiri.submission3.BuildConfig;
import com.yogiindragiri.submission3.service.model.Result;
import com.yogiindragiri.submission3.service.model.ResultTv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://api.themoviedb.org/3/search/";
    String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    @GET("movie")
    public Call<Result> getMovies(@Query("api_key") String api_key);

    @GET("movie")
    public Call<Result> getFilteredMovies(@Query("api_key") String api_key, @Query("query") String query);

    @GET("tv")
    public Call<ResultTv> getTvShow(@Query("api_key") String api_key);
}
