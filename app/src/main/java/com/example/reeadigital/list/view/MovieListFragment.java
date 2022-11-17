package com.example.reeadigital.list.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.reeadigital.R;
import com.example.reeadigital.Utils;
import com.example.reeadigital.list.viewmodel.MovieListViewModel;
import com.example.reeadigital.list.model.Movie;

import java.util.List;

public class MovieListFragment extends Fragment implements AbsListView.OnScrollListener, Observer<List<Movie>>{

    ProgressDialog progressDialog;
    MovieListViewModel movieListViewModel;
    ListView movieListView;
    MovieListViewAdapter movieListViewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list_layout, container, false);
        progressDialog = Utils.getProgreesDialog(getContext(), getString(R.string.data_fetch_message));
        movieListView = view.findViewById(R.id.lv_movie_list);
        if (movieListViewModel == null) {
            setupViewModel();
            initializeViewAdapter();
        }
        movieListView.setAdapter(movieListViewAdapter);
        movieListView.setOnScrollListener(this);
        return view;
    }
    private void setupViewModel() {
        progressDialog.show();
        movieListViewModel = new ViewModelProvider(this)
                        .get(MovieListViewModel.class);
        movieListViewModel.getAvailableMovieList().observe(this,this);
        movieListViewModel.fetchMovieList("");
    }

    private void initializeViewAdapter() {
        this.movieListViewAdapter = new MovieListViewAdapter(getContext(),
                movieListViewModel.getAvailableMovieList().getValue());
    }

    @Override
    public void onChanged(List<Movie> movieApiResponse) {
            if(movieApiResponse.isEmpty()){
                progressDialog.dismiss();
            }
            else{
                progressDialog.dismiss();
                generateMovieList(movieApiResponse);
            }
    }
    private void generateMovieList(List<Movie> movieApiResponse) {
        movieListViewAdapter.setMovieList(movieApiResponse);
        movieListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if(lastItem !=0 && lastItem == totalItemCount) {
            //progressDialog.show();
            movieListViewModel.fetchMovieList("");
        }
    }
}
