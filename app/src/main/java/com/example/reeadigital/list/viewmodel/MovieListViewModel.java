package com.example.reeadigital.list.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reeadigital.Repository;
import com.example.reeadigital.list.model.Movie;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    Repository repository;


    public MovieListViewModel() {
        repository = new Repository();
    }

    public void fetchMovieList(String language) {
        repository.requestMovieListData(language);
    }

    public MutableLiveData<List<Movie>> getAvailableMovieList() {
        return repository.getAllMovieListData();
    }
}
