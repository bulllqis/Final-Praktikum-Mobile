package com.example.final_praktikum_mobile.Response;

import com.example.final_praktikum_mobile.Model.TvShowsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse {
    @SerializedName("results")
    private List<TvShowsModel> tvShows;

    public List<TvShowsModel> getTvShows() {
        return tvShows;
    }
}
