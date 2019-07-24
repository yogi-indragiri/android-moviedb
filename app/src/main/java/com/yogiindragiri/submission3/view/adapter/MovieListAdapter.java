package com.yogiindragiri.submission3.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yogiindragiri.submission3.R;
import com.yogiindragiri.submission3.service.model.Movie;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> listMovie;
    private OnItemClickCallback onItemClickCallback;

    public MovieListAdapter( List<Movie> movieList) {
        this.listMovie = movieList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int position) {

        Movie movie = listMovie.get(position);
        String imgUrl = "https://image.tmdb.org/t/p/w780" +movie.getPosterPath();

        Glide.with(movieViewHolder.itemView.getContext())
                .load(imgUrl)
                .apply(new RequestOptions().override(200, 200))
                .into(movieViewHolder.imgPhoto);

        movieViewHolder.tvTitle.setText(movie.getTitle());
        movieViewHolder.tvDescription.setText(movie.getOverview());

        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickCallback.onItemClicked(listMovie.get(movieViewHolder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvDescription;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_movie_photo);
            tvTitle = itemView.findViewById(R.id.tv_movie_name);
            tvDescription = itemView.findViewById(R.id.tv_movie_desc);

        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}
