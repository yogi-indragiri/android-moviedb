package com.yogiindragiri.submission3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.yogiindragiri.submission3.service.api.Api;
import com.yogiindragiri.submission3.service.database.FavoriteTvShowDatabase;
import com.yogiindragiri.submission3.service.database.FavoriteTvShowEntry;
import com.yogiindragiri.submission3.service.model.ResultTv;
import com.yogiindragiri.submission3.service.model.TvShow;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.support.constraint.Constraints.TAG;

public class TvShowViewModel extends AndroidViewModel {

    private MutableLiveData<List<TvShow>> tvshowList;

    private LiveData<List<FavoriteTvShowEntry>> favorite;

    public LiveData<List<TvShow>> getTvhow() {
        if (tvshowList == null) {
            tvshowList = new MutableLiveData<>();
            loadTvShow();
        }

        return tvshowList;
    }

    private void loadTvShow() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ResultTv> call = api.getTvShow(Api.API_KEY);

        call.enqueue(new Callback<ResultTv>() {
            @Override
            public void onResponse(Call<ResultTv> call, Response<ResultTv> response) {
                ResultTv results = response.body();
                tvshowList.setValue(results.getResults());
            }

            @Override
            public void onFailure(Call<ResultTv> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public TvShowViewModel(Application application) {
        super(application);
        FavoriteTvShowDatabase database = FavoriteTvShowDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favorite = database.favoriteTvShowDao().loadAllFavorite();
    }

    public LiveData<List<FavoriteTvShowEntry>> getFavorite() {
        return favorite;
    }
}
