package com.example.reeadigital.list.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reeadigital.Repository;
import com.example.reeadigital.list.model.Movie;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    Repository repository;
    Integer currentPageNo;

    public MovieListViewModel() {
        repository = new Repository();
        currentPageNo = 1;
    }

    public void loadMovieList(String apiKey, String language, String pageNo) {
        repository.requestMovieListData(language,pageNo);
    }

    public MutableLiveData<List<Movie>> getMovieList() {
        return repository.getAllMovieListData();
    }
}
