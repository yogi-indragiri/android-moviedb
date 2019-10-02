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
import com.yogiindragiri.submission3.service.database.FavoriteMovieDatabase;
import com.yogiindragiri.submission3.service.database.FavoriteMovieEntry;
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.viewmodel.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie_extra";
    private Movie movie;
    private FavoriteMovieDatabase mDb;
    List<FavoriteMovieEntry> entries = new ArrayList<>();

    private Integer movieid;
    private Integer voteCount;
    private Boolean video;
    private Float voteAverage;
    private String title;
    private Float popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private String backdropPath;
    private Boolean adult;
    private String overview;
    private String releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView ivPoster;
        TextView tvTitle, tvOverview, tvOriginalLanguage, tvPopularity, tvReleaseDate;

        super.onCreate(savedInstanceState);
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);

        setContentView(R.layout.activity_movie_detail);

        mDb = FavoriteMovieDatabase.getInstance(getApplicationContext());

        tvTitle = findViewById(R.id.tv_title_movie);
        ivPoster = findViewById(R.id.iv_detail_movie);
        tvOverview = findViewById(R.id.tv_overview_txt_movie);
        tvOriginalLanguage = findViewById(R.id.tv_ori_languange_txt_movie);
        tvPopularity = findViewById(R.id.tv_popularity_txt_movie);
        tvReleaseDate = findViewById(R.id.tv_release_date_txt_movie);


        movieid = movie.getId();
        voteCount = movie.getVoteCount();
        video = movie.getVideo();
        voteAverage = movie.getVoteAverage();
        title = movie.getTitle();
        popularity = movie.getPopularity();
        posterPath = movie.getPosterPath();
        originalLanguage = movie.getOriginalLanguage();
        originalTitle = movie.getOriginalTitle();
        backdropPath = movie.getBackdropPath();
        adult = movie.getAdult();
        overview = movie.getOverview();
        releaseDate = movie.getReleaseDate();

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvOriginalLanguage.setText(movie.getOriginalLanguage());
        tvPopularity.setText(Float.toString(movie.getPopularity()));
        tvReleaseDate.setText(movie.getReleaseDate());

        String imgPath = "https://image.tmdb.org/t/p/w780" + movie.getPosterPath();

        Glide.with(this)
                .load(imgPath)
                .apply(new RequestOptions().override(300, 300))
                .into(ivPoster);

        checkStatus(title);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
        return super.onOptionsItemSelected(item);
    }

    private void saveFavorite() {
        final FavoriteMovieEntry favoriteEntry = new FavoriteMovieEntry(movieid, voteCount, video, voteAverage, title, popularity,
                posterPath, originalLanguage, originalTitle, backdropPath, adult, overview, releaseDate);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteMovieDao().insertMovieFavorite(favoriteEntry);
            }
        });
    }

    private void deleteFavorite(final int movie_id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteMovieDao().deleteMovieFavoriteWithId(movie_id);
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
                entries = mDb.favoriteMovieDao().loadAll(movieName);
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
                                        deleteFavorite(movieid);
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
                                        movieid = movie.getId();
                                        deleteFavorite(movieid);
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
