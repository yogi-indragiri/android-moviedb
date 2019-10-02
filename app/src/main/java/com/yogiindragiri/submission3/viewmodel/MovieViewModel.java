package com.yogiindragiri.submission3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import com.yogiindragiri.submission3.service.api.Api;
import com.yogiindragiri.submission3.service.database.FavoriteMovieDatabase;
import com.yogiindragiri.submission3.service.database.FavoriteMovieEntry;
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.service.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends AndroidViewModel {
    private static final String TAG = MovieViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> movieFilterList;

    private MutableLiveData<List<String>> queryFilter;

    public LiveData<String> getFruitList() {
        if (queryFilter == null) {
            queryFilter = new MutableLiveData<>();
//            queryFilter();
        }
        return queryFilter;
    }


    private LiveData<List<FavoriteMovieEntry>> favorite;


    public LiveData<List<Movie>> getMoviesFilter(String query) {
        if (movieFilterList == null) {
            movieFilterList = new MutableLiveData<>();
            loadFilteredMovies(query);
        }

        return movieFilterList;
    }

    private void loadFilteredMovies(String filter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Result> call = api.getFilteredMovies(Api.API_KEY, filter);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result results = response.body();

                movieFilterList.postValue(results.getResults());
                movieFilterList.setValue(results.getResults());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("Error :", t.getMessage());
            }
        });
    }

    public MovieViewModel(Application application) {
        super(application);
        FavoriteMovieDatabase database = FavoriteMovieDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favorite = database.favoriteMovieDao().loadAllFavorite();
    }

    public LiveData<List<FavoriteMovieEntry>> getFavorite() {
        return favorite;
    }
}
