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
import com.example.final_praktikum_mobile.Model.MovieModel;
import com.example.final_praktikum_mobile.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    Context context;
    private List<MovieModel> movies;

    public MoviesAdapter(Context context, List<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {

        MovieModel movieModel = movies.get(position);
        holder.tv_title.setText(movieModel.getTitle());
        holder.tv_tahun.setText(movieModel.getRelease_date().substring(0, 4));
        holder.tv_rating.setText(String.valueOf(movieModel.getRating()));
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_image())
                .apply(new RequestOptions().override(350,350))
                .into(holder.iv_poster);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movieModel.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
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

