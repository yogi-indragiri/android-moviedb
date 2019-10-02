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
import com.yogiindragiri.submission3.service.database.FavoriteTvShowEntry;
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.service.model.TvShow;
import com.yogiindragiri.submission3.view.adapter.MovieListAdapter;
import com.yogiindragiri.submission3.view.adapter.TvShowListAdapter;
import com.yogiindragiri.submission3.viewmodel.TvShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvShowListAdapter adapter;
    private ProgressBar progressBar;


    public FavoriteTvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progress_bar_tvshow);

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

    private void getAllFavorite() {

        TvShowViewModel viewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        viewModel.getFavorite().observe(this, new Observer<List<FavoriteTvShowEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteTvShowEntry> tvshowEntries) {
                List<TvShow> tvShows = new ArrayList<>();
                for (FavoriteTvShowEntry entry : tvshowEntries) {
                    TvShow tvShow = new TvShow();

                    tvShow.setId(entry.getTvshowid());
                    tvShow.setName(entry.getName());
                    tvShow.setBackdropPath(entry.getBackdropPath());
                    tvShow.setFirstAirDate(entry.getFirstAirDate());
                    tvShow.setOriginalLanguage(entry.getOriginalLanguage());
                    tvShow.setOverview(entry.getOverview());
                    tvShow.setPopularity(entry.getPopularity());
                    tvShow.setPosterPath(entry.getPosterPath());
                    tvShow.setVoteAverage(entry.getVoteAverage());
                    tvShow.setVoteCount(entry.getVoteCount());
                    tvShow.setOriginalName(entry.getOriginalName());

                    tvShows.add(tvShow);
                }

                adapter = new TvShowListAdapter(tvShows);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickCallback(new TvShowListAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(TvShow data) {
                        showSelectedTvShow(data);
                    }
                });

                showLoading(false);
            }
        });
    }

    private void showSelectedTvShow(TvShow tvShow) {
        Toast.makeText(getContext(), tvShow.getName(), Toast.LENGTH_SHORT).show();
        Intent moveToDetail = new Intent(getContext(), TvShowDetailActivity.class);
        moveToDetail.putExtra(TvShowDetailActivity.TVSHOW_EXTRA, tvShow);
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
