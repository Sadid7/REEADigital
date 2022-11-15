package com.example.reeadigital.list;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetMovieLIstService {
    @GET("/das")
    Call<List<Movie>> getMovieList();
}
