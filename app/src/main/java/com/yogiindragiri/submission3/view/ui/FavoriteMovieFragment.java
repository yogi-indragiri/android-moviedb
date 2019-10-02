package com.yogiindragiri.submission3.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yogiindragiri.submission3.R;
import com.yogiindragiri.submission3.service.database.FavoriteMovieEntry;
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.view.adapter.MovieListAdapter;
import com.yogiindragiri.submission3.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private ProgressBar progressBar;
    private MovieViewModel viewModel;

    public FavoriteMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progress_bar_movie);

        getAllFavorite();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setQueryHint(getResources().getString(R.string.search_hint));


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllFavorite(){

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        viewModel.getFavorite().observe(this, new Observer<List<FavoriteMovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovieEntry> movieEntries) {
                List<Movie> movies = new ArrayList<>();
                for (FavoriteMovieEntry entry : movieEntries){
                    Movie movie = new Movie();

                    movie.setId(entry.getMovieid());
                    movie.setReleaseDate(entry.getReleaseDate());
                    movie.setOverview(entry.getOverview());
                    movie.setAdult(entry.getAdult());
                    movie.setBackdropPath(entry.getBackdropPath());
                    movie.setOriginalTitle(entry.getOriginalTitle());
                    movie.setOriginalLanguage(entry.getOriginalLanguage());
                    movie.setPosterPath(entry.getPosterPath());
                    movie.setPopularity(entry.getPopularity());
                    movie.setTitle(entry.getTitle());
                    movie.setVoteAverage(entry.getVoteAverage());
                    movie.setVoteCount(entry.getVoteCount());
                    movie.setVideo(entry.getVideo());

                    movies.add(movie);
                }

                adapter = new MovieListAdapter(movies);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickCallback(new MovieListAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(Movie data) {
                        showSelectedMovie(data);
                    }
                });
                showLoading(false);
            }
        });
    }

    private void showSelectedMovie(Movie movie){
        Toast.makeText(getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
        Intent moveToDetail = new Intent(getContext(), MovieDetailActivity.class);
        moveToDetail.putExtra(MovieDetailActivity.MOVIE_EXTRA, movie);
        startActivity(moveToDetail);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
