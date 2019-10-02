package com.yogiindragiri.submission3.view.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yogiindragiri.submission3.R;
import com.yogiindragiri.submission3.view.adapter.TabAdapter;

public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabAdapter adapter;
        TabLayout tabLayout;
        ViewPager viewPager;

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout =  view.findViewById(R.id.tabLayoutFavorite);

        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new FavoriteMovieFragment(), getResources().getString(R.string.tab_movie));
        adapter.addFragment(new FavoriteTvShowFragment(), getResources().getString(R.string.tab_tv_show));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
