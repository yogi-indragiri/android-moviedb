package com.yogiindragiri.submission3.view.adapter;

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
import com.yogiindragiri.submission3.service.model.TvShow;
import java.util.List;

public class TvShowListAdapter extends RecyclerView.Adapter<TvShowListAdapter.TvShowViewHolder> {

    private List<TvShow> listTvShow;
    private TvShowListAdapter.OnItemClickCallback onItemClickCallback;

    public TvShowListAdapter(List<TvShow> list) {
        this.listTvShow = list;
    }

    public void setOnItemClickCallback(TvShowListAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_tvshow, parent, false);
        return new TvShowListAdapter.TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder tvShowViewHolder, int position) {
        TvShow tvshow = listTvShow.get(position);
        String imgUrl = "https://image.tmdb.org/t/p/w780"  + tvshow.getPosterPath();
        Glide.with(tvShowViewHolder.itemView.getContext())
                .load(imgUrl)
                .apply(new RequestOptions().override(200, 200))
                .into(tvShowViewHolder.imgPhoto);
        tvShowViewHolder.tvTitle.setText(tvshow.getOriginalName());
        tvShowViewHolder.tvDescription.setText(tvshow.getOverview());

        tvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTvShow.get(tvShowViewHolder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvDescription;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_tvshow_photo);
            tvTitle = itemView.findViewById(R.id.tv_tvshow_name);
            tvDescription = itemView.findViewById(R.id.tv_tvshow_desc);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow data);
    }
}
