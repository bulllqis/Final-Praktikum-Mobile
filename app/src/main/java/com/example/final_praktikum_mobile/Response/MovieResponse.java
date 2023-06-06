package com.example.final_praktikum_mobile.Response;

import com.example.final_praktikum_mobile.Model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<MovieModel> movies;

    public List<MovieModel> getMovies() {
        return movies;
    }
}
