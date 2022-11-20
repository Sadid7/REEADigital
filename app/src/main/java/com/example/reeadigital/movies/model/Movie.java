package com.example.reeadigital.movies.model;

import com.google.gson.annotations.SerializedName;
/**Simple POJO class based on the api response*/
public class Movie {

    @SerializedName("id")
    private Integer movieId;
    @SerializedName("title")
    private String movieTitle;
    @SerializedName("overview")
    private String movieDetails;
    @SerializedName("poster_path")
    private String thumbnailUrl;

    public Movie(Integer movieId, String movieTitle,
                 String movieDetails, String thumbnailUrl) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieDetails = movieDetails;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieDetails() {
        return movieDetails;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
