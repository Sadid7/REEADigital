package com.example.reeadigital.api;

import com.example.reeadigital.movies.model.MovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**retrofit service interface for api get request*/
public interface GetMovieLIstService {
    @GET("/3/movie/upcoming")
    Call<MovieApiResponse> getMovieList(@Query("api_key") String apiKey,
                                        @Query("language") String language,
                                        @Query("page") String pageNo);
}
