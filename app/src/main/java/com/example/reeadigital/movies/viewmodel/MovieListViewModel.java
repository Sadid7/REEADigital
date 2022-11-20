package com.example.reeadigital.movies.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reeadigital.movies.data.MovieListRepository;
import com.example.reeadigital.movies.model.Movie;
import java.util.List;
/** Simple view model for movie list view*/
public class MovieListViewModel extends ViewModel implements MovieListApiListener{
    MutableLiveData<List<Movie>> allMovieListData;
    MovieListRepository movieListRepository;

    public MovieListViewModel() {
        movieListRepository = new MovieListRepository(this);
        this.allMovieListData = new MutableLiveData<>();
    }

    public void fetchMovieList(String language) {
        movieListRepository.requestMovieListData(language);
    }
    public MutableLiveData<List<Movie>> getAvailableMovieList() {
        return allMovieListData;
    }
    @Override
    public void onMovieListRetrieved(List<Movie> movieList) {
        if (allMovieListData.getValue() == null) {
            //initial addition to movie list
            allMovieListData.postValue(movieList);
        } else {
            //append new movie list data with movie data retrieved earlier
            appendMovieListData(movieList);
        }
    }
    private void appendMovieListData(List<Movie> movieList) {
        List<Movie> movies = allMovieListData.getValue();
        movies.addAll(movieList);
        allMovieListData.postValue(movies);
    }
}
