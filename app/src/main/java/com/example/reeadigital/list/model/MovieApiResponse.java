package com.example.reeadigital.list.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/**Simple POJO class based on the api response*/
public class MovieApiResponse {

    @SerializedName("page")
    private Integer pageNo;
    @SerializedName("results")
    private List<Movie> movieList = null;
    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
