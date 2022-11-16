package com.example.reeadigital.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {

    @SerializedName("page")
    private Integer pageNo;
    @SerializedName("results")
    private List<Movie> movieList = null;

    public Integer getPageNo() {
        return pageNo;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
