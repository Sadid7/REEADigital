package com.example.reeadigital.list.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reeadigital.list.model.Movie;

import java.util.List;
/** Simple view model for movie list view*/
public class MovieListViewModel extends ViewModel {
    MovieListRepository movieListRepository;

    public MovieListViewModel() {
        movieListRepository = new MovieListRepository();
    }

    public void fetchMovieList(String language) {
        movieListRepository.requestMovieListData(language);
    }

    public MutableLiveData<List<Movie>> getAvailableMovieList() {
        return movieListRepository.getAllMovieListData();
    }
}
