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
import com.yogiindragiri.submission3.service.model.TvShow;

public class TvShowDetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title, overview, original_language, popularity, first_air_date;

    public static final String TVSHOW_EXTRA = "tvshow_ekstra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        poster = findViewById(R.id.iv_detail_tvshow);
        title = findViewById(R.id.tv_title_tvshow);
        overview = findViewById(R.id.tv_overview_txt_tvshow);
        original_language = findViewById(R.id.tv_original_language_txt_tvshow);
        popularity = findViewById(R.id.tv_popularity_txt_tvshow);
        first_air_date = findViewById(R.id.tv_first_air_date_txt_tvshow);

        TvShow tvShow = getIntent().getParcelableExtra(TVSHOW_EXTRA);

        title.setText(tvShow.getName());
        overview.setText(tvShow.getOverview());
        original_language.setText(tvShow.getOriginalLanguage());
        popularity.setText(Float.toString(tvShow.getPopularity()));
        first_air_date.setText(tvShow.getFirstAirDate());

        String imgPath = "https://image.tmdb.org/t/p/w780" +tvShow.getPosterPath();

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
