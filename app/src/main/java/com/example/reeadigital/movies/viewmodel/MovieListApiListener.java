package com.example.reeadigital.movies.viewmodel;

import com.example.reeadigital.movies.model.Movie;

import java.util.List;

public interface MovieListApiListener {
    void onMovieListRetrieved(List<Movie> movieList);
}
