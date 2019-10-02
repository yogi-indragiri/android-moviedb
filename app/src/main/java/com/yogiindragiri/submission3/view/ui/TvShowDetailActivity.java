package com.yogiindragiri.submission3.view.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.yogiindragiri.submission3.R;
import com.yogiindragiri.submission3.service.database.FavoriteTvShowDatabase;
import com.yogiindragiri.submission3.service.database.FavoriteTvShowEntry;
import com.yogiindragiri.submission3.service.model.TvShow;
import com.yogiindragiri.submission3.viewmodel.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class TvShowDetailActivity extends AppCompatActivity {

    public static final String TVSHOW_EXTRA = "tvshow_ekstra";
    private TvShow tvShow;
    private FavoriteTvShowDatabase mDb;
    List<FavoriteTvShowEntry> entries = new ArrayList<>();

    private Integer tvshowid;
    private String name;
    private String originalName;
    private Float popularity;
    private Integer voteCount;
    private String firstAirDate;
    private String backdropPath;
    private String originalLanguage;
    private Float voteAverage;
    private String overview;
    private String posterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView ivPoster;
        TextView tvTitle, tvOverview, tvOriginalLanguage, tvPopularity, tvFirstAirDate;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        mDb = FavoriteTvShowDatabase.getInstance(getApplicationContext());

        ivPoster = findViewById(R.id.iv_detail_tvshow);
        tvTitle = findViewById(R.id.tv_title_tvshow);
        tvOverview = findViewById(R.id.tv_overview_txt_tvshow);
        tvOriginalLanguage = findViewById(R.id.tv_original_language_txt_tvshow);
        tvPopularity = findViewById(R.id.tv_popularity_txt_tvshow);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date_txt_tvshow);

        tvShow = getIntent().getParcelableExtra(TVSHOW_EXTRA);

        tvshowid = tvShow.getId();
        name = tvShow.getName();
        originalName = tvShow.getOriginalName();
        popularity = tvShow.getPopularity();
        voteCount = tvShow.getVoteCount();
        firstAirDate = tvShow.getFirstAirDate();
        backdropPath = tvShow.getBackdropPath();
        originalLanguage = tvShow.getOriginalLanguage();
        voteAverage = tvShow.getVoteAverage();
        overview = tvShow.getOverview();
        posterPath = tvShow.getPosterPath();

        tvTitle.setText(name);
        tvOverview.setText(overview);
        tvOriginalLanguage.setText(originalLanguage);
        tvPopularity.setText(Float.toString(popularity));
        tvFirstAirDate.setText(firstAirDate);

        String imgPath = "https://image.tmdb.org/t/p/w780" + tvShow.getPosterPath();

        Glide.with(this)
                .load(imgPath)
                .apply(new RequestOptions().override(300, 300))
                .into(ivPoster);

        checkStatus(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFavorite() {
        final FavoriteTvShowEntry favoriteEntry = new FavoriteTvShowEntry(tvshowid, name, originalName, popularity, voteCount, firstAirDate,
                backdropPath, originalLanguage, voteAverage, overview, posterPath);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteTvShowDao().insertTvShowFavorite(favoriteEntry);
            }
        });
    }

    private void deleteFavorite(final int movie_id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteTvShowDao().deleteTvShowFavoriteWithId(movie_id);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void checkStatus(final String movieName) {
        final MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                entries.clear();
                entries = mDb.favoriteTvShowDao().loadAll(movieName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (entries.size() > 0) {
                    materialFavoriteButton.setFavorite(true);
                    materialFavoriteButton.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, R.string.added_favorite,
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        deleteFavorite(tvshowid);
                                        Snackbar.make(buttonView, R.string.remove_favorite,
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    materialFavoriteButton.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, R.string.added_favorite,
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        deleteFavorite(tvshowid);
                                        Snackbar.make(buttonView, R.string.remove_favorite,
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        }.execute();
    }

}
