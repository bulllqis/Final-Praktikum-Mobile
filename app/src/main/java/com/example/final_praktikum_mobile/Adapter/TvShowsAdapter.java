package com.example.final_praktikum_mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.final_praktikum_mobile.DetailActivity;
import com.example.final_praktikum_mobile.Model.TvShowsModel;
import com.example.final_praktikum_mobile.R;

import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.ViewHolder> {
    Context context;
    private List<TvShowsModel> tvShows;

    public TvShowsAdapter(Context context, List<TvShowsModel> tvShows) {
        this.context = context;
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public TvShowsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv, parent, false);
        return new TvShowsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsAdapter.ViewHolder holder, int position) {

        TvShowsModel tvShowsModel = tvShows.get(position);
        holder.tv_title.setText(tvShowsModel.getName());
        holder.tv_tahun.setText(tvShowsModel.getFirst_air_date().substring(0, 4));
        holder.tv_rating.setText(String.valueOf(tvShowsModel.getRating()));
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + tvShowsModel.getPoster_image())
                .apply(new RequestOptions().override(350,350))
                .into(holder.iv_poster);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_TVSHOWS, tvShowsModel.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_poster;
        TextView tv_title, tv_rating, tv_tahun;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_tahun = itemView.findViewById(R.id.tv_tahun);


        }
    }
}


