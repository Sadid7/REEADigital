package com.example.reeadigital.list.viewmodel;

import com.example.reeadigital.list.model.Movie;

import java.util.List;

public interface MovieListApiListener {
    void onMovieListRetrieved(List<Movie> movieList);
}
