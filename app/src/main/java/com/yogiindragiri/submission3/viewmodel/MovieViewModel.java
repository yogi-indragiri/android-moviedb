package com.yogiindragiri.submission3.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.yogiindragiri.submission3.service.api.Api;
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.service.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;

    public LiveData<List<Movie>> getMovies() {
        if (movieList == null) {
            movieList = new MutableLiveData<>();
            loadMovies();
        }

        return movieList;
    }

    private void loadMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Result> call = api.getMovies(Api.API_KEY);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result results = response.body();
                movieList.setValue(results.getResults());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
