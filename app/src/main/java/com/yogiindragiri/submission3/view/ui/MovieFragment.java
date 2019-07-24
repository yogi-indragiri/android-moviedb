package com.yogiindragiri.submission3.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yogiindragiri.submission3.R;
import com.yogiindragiri.submission3.service.model.Movie;
import com.yogiindragiri.submission3.service.model.Result;
import com.yogiindragiri.submission3.view.adapter.MovieListAdapter;
import com.yogiindragiri.submission3.viewmodel.MovieViewModel;

import java.util.List;

public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private ProgressBar progressBar;

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

        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progress_bar_movie);


        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);

        model.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> results) {
                adapter = new MovieListAdapter(results);

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
