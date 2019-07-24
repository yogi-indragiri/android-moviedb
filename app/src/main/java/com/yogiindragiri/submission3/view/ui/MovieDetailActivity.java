package com.yogiindragiri.submission3.view.ui;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yogiindragiri.submission3.R;
import com.yogiindragiri.submission3.service.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title, overview, original_language, popularity, release_date;

    public static final String MOVIE_EXTRA = "movie_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title = findViewById(R.id.tv_title_movie);
        poster = findViewById(R.id.iv_detail_movie);
        overview = findViewById(R.id.tv_overview_txt_movie);
        original_language = findViewById(R.id.tv_ori_languange_txt_movie);
        popularity = findViewById(R.id.tv_popularity_txt_movie);
        release_date = findViewById(R.id.tv_release_date_txt_movie);

        Movie movie = getIntent().getParcelableExtra(MOVIE_EXTRA);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        original_language.setText(movie.getOriginalLanguage());
        popularity.setText(Float.toString(movie.getPopularity()));
        release_date.setText(movie.getReleaseDate());

        String imgPath = "https://image.tmdb.org/t/p/w780" +movie.getPosterPath();

        Glide.with(this)
                .load(imgPath)
                .apply(new RequestOptions().override(300, 300))
                .into(poster);


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
}
