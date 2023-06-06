package com.example.final_praktikum_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class TvShowsModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("backdrop_path")
    private String backdrop_image;
    @SerializedName("poster_path")
    private String poster_image;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("vote_average")
    private Float rating;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("original_language")
    private String language;
    @SerializedName("popularity")
    private Number popularity;

    public TvShowsModel(int id, String name, String backdrop_image, String poster_image, String first_air_date, Float rating, String synopsis, String language, Number popularity) {
        this.id = id;
        this.name = name;
        this.backdrop_image = backdrop_image;
        this.poster_image = poster_image;
        this.first_air_date = first_air_date;
        this.rating = rating;
        this.synopsis = synopsis;
        this.language = language;
        this.popularity = popularity;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getLanguage() {
        return language;
    }

    public Number getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBackdrop_image() {
        return backdrop_image;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public Float getRating() {
        return rating;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
