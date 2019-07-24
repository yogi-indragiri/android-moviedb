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
import com.yogiindragiri.submission3.service.model.ResultTv;
import com.yogiindragiri.submission3.service.model.TvShow;
import com.yogiindragiri.submission3.view.adapter.MovieListAdapter;
import com.yogiindragiri.submission3.view.adapter.TvShowListAdapter;
import com.yogiindragiri.submission3.viewmodel.MovieViewModel;
import com.yogiindragiri.submission3.viewmodel.TvShowViewModel;

import java.util.List;

public class TvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvShowListAdapter adapter;
    private ProgressBar progressBar;

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progress_bar_tvshow);

        TvShowViewModel model = ViewModelProviders.of(this).get(TvShowViewModel.class);

        model.getTvhow().observe(this, new Observer<List<TvShow>>() {
            @Override
            public void onChanged(@Nullable List<TvShow> results) {
                adapter = new TvShowListAdapter(results);

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

    private void showSelectedTvShow(TvShow tvShow){
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
