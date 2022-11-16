package com.example.reeadigital.apiCall;

import com.example.reeadigital.list.MovieApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieLIstService {
    @GET("/3/movie/upcoming")
    Call<MovieApiResponse> getMovieList(@Query("api_key") String apiKey,
                                        @Query("language") String language,
                                        @Query("page") String pageNo);
}
