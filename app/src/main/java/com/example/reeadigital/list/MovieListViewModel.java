package com.example.reeadigital.list;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reeadigital.Repository;

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
        return repository.getMovieListData();
    }
}
