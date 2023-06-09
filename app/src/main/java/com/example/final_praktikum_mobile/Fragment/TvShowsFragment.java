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

import com.example.final_praktikum_mobile.Adapter.TvShowsAdapter;
import com.example.final_praktikum_mobile.Api.ApiConfig;
import com.example.final_praktikum_mobile.Database.DBHelper;
import com.example.final_praktikum_mobile.MainActivity;
import com.example.final_praktikum_mobile.Model.TvShowsModel;
import com.example.final_praktikum_mobile.R;
import com.example.final_praktikum_mobile.Response.TvShowsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment {

    private RecyclerView rv_tvShows;
    private List<TvShowsModel> tvShows = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private ProgressBar progressBar;
    private TextView tv_internet;
    private ImageView btn_retry;
    private Handler handler;
    private SearchView searchView;
    TvShowsAdapter tvShowsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tv_internet = view.findViewById(R.id.tvinternet);
        btn_retry = view.findViewById(R.id.btn_retry);
        searchView = view.findViewById(R.id.searchView);
        rv_tvShows = view.findViewById(R.id.rv_tvShows);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("TV Shows");

        rv_tvShows.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2);

        handler = new Handler(Looper.getMainLooper());
        rv_tvShows.setVisibility(View.GONE);
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
                    tvShowsAdapter = new TvShowsAdapter(getActivity(), tvShows);

                } else {
                    List<TvShowsModel> searchTvShow = dbHelper.searchTvShowByName(query);

                    tvShowsAdapter = new TvShowsAdapter(getActivity(), searchTvShow);
                }
                rv_tvShows.setLayoutManager(layoutManager);
                rv_tvShows.setAdapter(tvShowsAdapter);
                return true;
            }
        });

    }

    private void getData() {
        if(isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);

            Call<TvShowsResponse> tvShow = ApiConfig.getApiService().getTvShows("2ee1c153c74e27879c557e354c5163c4", 1);
            tvShow.enqueue(new Callback<TvShowsResponse>() {
                @Override
                public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    rv_tvShows.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            tvShows.clear();
                            tvShows.addAll(response.body().getTvShows());
                            tvShowsAdapter = new TvShowsAdapter(getActivity(), tvShows);
                            rv_tvShows.setLayoutManager(layoutManager);
                            rv_tvShows.setAdapter(tvShowsAdapter);

                            DBHelper dbHelper = new DBHelper(getContext());
                            for (TvShowsModel tvShow : tvShows) {
                                TvShowsModel tvShowsModel = new TvShowsModel(
                                        tvShow.getId(),
                                        tvShow.getName(),
                                        tvShow.getBackdrop_image(),
                                        tvShow.getPoster_image(),
                                        tvShow.getFirst_air_date(),
                                        tvShow.getRating(),
                                        tvShow.getSynopsis(),
                                        tvShow.getLanguage(),
                                        tvShow.getPopularity()

                                );
                                dbHelper.insertTvShow(tvShowsModel);
                            }

                        }

                    }else {
                        if (response.body() != null){
                            Toast.makeText(getActivity(), "Gagal memuat Tv Show", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<TvShowsResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Gagal memuat Tv Show", Toast.LENGTH_SHORT).show();

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