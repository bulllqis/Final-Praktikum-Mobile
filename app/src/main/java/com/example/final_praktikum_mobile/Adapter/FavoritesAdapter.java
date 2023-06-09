package com.example.final_praktikum_mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.final_praktikum_mobile.DetailActivity;
import com.example.final_praktikum_mobile.Model.MovieModel;
import com.example.final_praktikum_mobile.Model.TvShowsModel;
import com.example.final_praktikum_mobile.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    Context context;
    private List<Object> favoritesList;
    private final ActivityResultLauncher<Intent> resultLauncher ;

    public FavoritesAdapter(Context context, List<Object> favoritesList, ActivityResultLauncher<Intent> resultLauncher) {
        this.context = context;
        this.favoritesList = favoritesList;
        this.resultLauncher = resultLauncher;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        Object favorite = favoritesList.get(position);

        if (favorite instanceof MovieModel) {
            MovieModel movie = (MovieModel) favorite;
            holder.tv_title.setText(movie.getTitle());
            holder.tv_year.setText(movie.getRelease_date().substring(0, 4));
            holder.iv_movieOrTv.setImageResource(R.drawable.baseline_movie_24);
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_image())
                    .apply(new RequestOptions().override(350,350))
                    .into(holder.iv_poster);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.getId());
                resultLauncher.launch(intent);
            });
        } else if (favorite instanceof TvShowsModel){
            TvShowsModel tvShow = (TvShowsModel) favorite;
            holder.tv_title.setText(tvShow.getName());
            holder.tv_year.setText(tvShow.getFirst_air_date().substring(0, 4));
            holder.iv_movieOrTv.setImageResource(R.drawable.baseline_tv_24);
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + tvShow.getPoster_image())
                    .apply(new RequestOptions().override(350,350))
                    .into(holder.iv_poster);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TVSHOWS,tvShow.getId());
                resultLauncher.launch(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_year;
        ImageView iv_poster, iv_movieOrTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_year = itemView.findViewById(R.id.tv_releaseDate);
            iv_poster = itemView.findViewById(R.id.iv_poster);
            iv_movieOrTv = itemView.findViewById(R.id.iv_movieOrTv);
        }
    }

}

