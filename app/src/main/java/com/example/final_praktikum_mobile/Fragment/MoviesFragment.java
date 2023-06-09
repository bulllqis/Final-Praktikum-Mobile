package com.example.final_praktikum_mobile.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_praktikum_mobile.Adapter.MoviesAdapter;
import com.example.final_praktikum_mobile.Api.ApiConfig;
import com.example.final_praktikum_mobile.Database.DBHelper;
import com.example.final_praktikum_mobile.MainActivity;
import com.example.final_praktikum_mobile.Model.MovieModel;
import com.example.final_praktikum_mobile.R;
import com.example.final_praktikum_mobile.Response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesFragment extends Fragment {
    private RecyclerView rv_movies;
    private List<MovieModel> movies = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private ProgressBar progressBar;
    private TextView tv_internet;
    private ImageView btn_retry;
    private Handler handler;
    MoviesAdapter moviesAdapter;
    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tv_internet = view.findViewById(R.id.tvinternet);
        btn_retry = view.findViewById(R.id.btn_retry);
        searchView = view.findViewById(R.id.searchView);
        rv_movies = view.findViewById(R.id.rv_movies);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Movies");

        rv_movies.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2);

        handler = new Handler(Looper.getMainLooper());
        rv_movies.setVisibility(View.GONE);
        searchView.setVisibility(View.GONE);
        tv_internet.setVisibility(View.GONE);
        btn_retry.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        getData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                DBHelper dbHelper = new DBHelper(getContext());
                if (query.isEmpty()) {
                    moviesAdapter = new MoviesAdapter(getActivity(), movies);

                } else {
                    List<MovieModel> searchMovie = dbHelper.searchMoviesByTitle(query);
                    moviesAdapter = new MoviesAdapter(getActivity(), searchMovie);

                }
                rv_movies.setLayoutManager(layoutManager);
                rv_movies.setAdapter(moviesAdapter);
                return true;
            }
        });

    }

    private void getData() {
        if(isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);

            Call<MovieResponse> movie = ApiConfig.getApiService().getMovies("2ee1c153c74e27879c557e354c5163c4", 1);
            movie.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    rv_movies.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            movies.clear();
                            movies.addAll(response.body().getMovies());
                            moviesAdapter = new MoviesAdapter(getActivity(), movies);
                            rv_movies.setLayoutManager(layoutManager);
                            rv_movies.setAdapter(moviesAdapter);

                            DBHelper dbHelper = new DBHelper(getContext());
                            for (MovieModel movie : movies) {
                                MovieModel movieModel = new MovieModel(
                                        movie.getId(),
                                        movie.getTitle(),
                                        movie.getBackdrop_image(),
                                        movie.getPoster_image(),
                                        movie.getRelease_date(),
                                        movie.getRating(),
                                        movie.getSynopsis(),
                                        movie.getLanguage(),
                                        movie.getPopularity()
                                );
                                dbHelper.insertMovie(movieModel);
                            }
                        }

                    }else {
                        if (response.body() != null){
                            Toast.makeText(getActivity(), "Gagal memuat movies", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal memuat movies", Toast.LENGTH_SHORT).show();

                }
            });


        }else {
            handler.postDelayed(() -> showRetryButton(), 100);
        }
    }

    private void showRetryButton() {
        tv_internet.setVisibility(View.VISIBLE);
        btn_retry.setVisibility(View.VISIBLE);

        btn_retry.setOnClickListener(view -> {
            tv_internet.setVisibility(View.GONE);
            btn_retry.setVisibility(View.GONE);
            getData();
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }


}