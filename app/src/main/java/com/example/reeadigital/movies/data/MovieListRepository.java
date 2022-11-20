package com.example.reeadigital.movies.data;

import android.util.Log;

import com.example.reeadigital.Utils;
import com.example.reeadigital.api.RetrofitClientInstance;
import com.example.reeadigital.api.GetMovieLIstService;
import com.example.reeadigital.movies.model.MovieApiResponse;
import com.example.reeadigital.movies.viewmodel.MovieListApiListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListRepository implements Callback<MovieApiResponse>{
    MovieApiResponse currentMovieApiResponse;
    MovieListApiListener movieListApiListener;

    public MovieListRepository(MovieListApiListener movieListApiListener) {
        this.movieListApiListener = movieListApiListener;
    }
    /**Exposed method to be called from outside the class
     *enqueues api call to create initial data
     *enqueues api calls until all the pages data is retireved
     *through listview scroll
     *ignores all the other requests if all the pages are retrieved */
    public void requestMovieListData(String language) {
        if (currentMovieApiResponse == null) {
            enqueueMovieListDataRequest(language,Integer.toString(Utils.INITIAL_PAGE_NO));
        } else if ( currentMovieApiResponse.getPageNo() <= getTotalPageNo()) {
            int nextPageNo = currentMovieApiResponse.getPageNo() + 1;
            enqueueMovieListDataRequest(language,Integer.toString(nextPageNo));
        }
    }
    /**Enqueues api call for movie api call*/
    private void enqueueMovieListDataRequest(String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList(Utils.MOVIE_LIST_API_KEY,
                language,pageNo);
        call.enqueue(this);
    }
    public Integer getTotalPageNo() {
        if (currentMovieApiResponse.getTotalPages() != null) {
            return currentMovieApiResponse.getTotalPages();
        } else return 0;
    }
    /** On successful response passes movie data to view model
     * and keeps the latest response for reference*/
    @Override
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        //sets latest api response as currentMovieApiResponse
        currentMovieApiResponse = response.body();
        movieListApiListener.onMovieListRetrieved(response.body().getMovieList());
    }
    @Override
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e("MovieListRepository", t.getLocalizedMessage());
    }
}
