package com.example.reeadigital.list;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reeadigital.Repository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    Repository repository;

    public MovieListViewModel(Context context) {
        repository = new Repository(context);
    }

    public MutableLiveData<List<Movie>> getTasks() {
        return repository.getTasks();
    }
}
