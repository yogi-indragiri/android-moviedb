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
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.view.adapter.MovieListAdapter;
import com.yogiindragiri.submission3.viewmodel.MovieViewModel;

import java.util.List;

public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private ProgressBar progressBar;
    private MovieViewModel viewModel;

    public MovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progress_bar_movie);


        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        viewModel.getMoviesFilter("spiderman").observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> results) {
                adapter = new MovieListAdapter(results);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                showLoading(true);

                viewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);

                viewModel.getMoviesFilter(query).observe(getActivity(), new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        adapter = new MovieListAdapter(movies);
                        adapter.notifyDataSetChanged();

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

    private void showSelectedMovie(Movie movie) {
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
