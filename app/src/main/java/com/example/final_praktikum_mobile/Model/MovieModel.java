package com.example.final_praktikum_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class MovieModel {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdrop_image;
    @SerializedName("poster_path")
    private String poster_image;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("vote_average")
    private Float rating;
    @SerializedName("overview")
    private String synopsis;

    @SerializedName("original_language")
    private String language;
    @SerializedName("popularity")
    private Number popularity;


    public MovieModel(int id, String title, String backdrop_image, String poster_image, String release_date, Float rating, String synopsis, String language, Number popularity) {
        this.id = id;
        this.title = title;
        this.backdrop_image = backdrop_image;
        this.poster_image = poster_image;
        this.release_date = release_date;
        this.rating = rating;
        this.synopsis = synopsis;
        this.language = language;
        this.popularity = popularity;
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

    public String getTitle() {
        return title;
    }

    public String getBackdrop_image() {
        return backdrop_image;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Float getRating() {
        return rating;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
