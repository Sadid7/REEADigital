package com.example.reeadigital.list.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.reeadigital.Utils;
import com.example.reeadigital.api.RetrofitClientInstance;
import com.example.reeadigital.api.GetMovieLIstService;
import com.example.reeadigital.list.model.Movie;
import com.example.reeadigital.list.model.MovieApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListRepository implements Callback<MovieApiResponse>{
    MutableLiveData<List<Movie>> allMovieListData;
    MovieApiResponse currentMovieApiResponse;
    int currentPageNo;

    public MovieListRepository() {
        this.allMovieListData = new MutableLiveData<>();
        this.currentMovieApiResponse = new MovieApiResponse();
        this.currentPageNo = 0;
    }

    public void requestMovieListData(String language) {
        if (currentPageNo == 0) {
            currentPageNo++;
            enqueueMovieListDataRequest(language,Integer.toString(currentPageNo));
        } else if ( currentPageNo <= getTotalPageNo()) {
            currentPageNo++;
            enqueueMovieListDataRequest(language,Integer.toString(currentPageNo));
        }
    }

    private void enqueueMovieListDataRequest(String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList(Utils.MOVIE_LIST_API_KEY,
                language,pageNo);
        call.enqueue(this);
    }

    public MutableLiveData<List<Movie>> getAllMovieListData() {
        return allMovieListData;
    }

    public Integer getTotalPageNo() {
        if (currentMovieApiResponse.getTotalPages() != null) {
            return currentMovieApiResponse.getTotalPages();
        } else return 0;

    }

    @Override
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        //sets latest api response as currentMovieApiResponse
        currentMovieApiResponse = response.body();
        if (allMovieListData.getValue() == null) {
            //initial addition to movie list
            allMovieListData.postValue(response.body().getMovieList());
        } else {
            //append new movie list data with earliar movie data
           appendMovieListData(response.body().getMovieList());
        }
    }

    @Override
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e("MovieListRepository", t.getLocalizedMessage());
    }

    private void appendMovieListData(List<Movie> movieList) {
        List<Movie> movies = allMovieListData.getValue();
        movies.addAll(movieList);
        allMovieListData.postValue(movies);
    }
}
