package com.example.reeadigital.list.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {

    @SerializedName("page")
    private Integer pageNo;
    @SerializedName("results")
    private List<Movie> movieList = null;
    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPageNo() {
        return pageNo;
    }
    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
