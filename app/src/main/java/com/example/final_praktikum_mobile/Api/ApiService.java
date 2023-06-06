package com.example.final_praktikum_mobile.Api;

import com.example.final_praktikum_mobile.Response.MovieResponse;
import com.example.final_praktikum_mobile.Response.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String key, @Query("page") int page);

    @GET("tv/popular")
    Call<TvShowsResponse> getTvShows(@Query("api_key") String key, @Query("page") int page);
}
