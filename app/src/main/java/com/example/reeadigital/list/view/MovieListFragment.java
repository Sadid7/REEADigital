package com.example.reeadigital.list.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import java.util.Objects;

public class MovieListFragment extends Fragment implements AbsListView.OnScrollListener,
        Observer<List<Movie>>, DialogInterface.OnClickListener {

    private ProgressDialog progressDialog;
    private MovieListViewModel movieListViewModel;
    private MovieListViewAdapter movieListViewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list_layout, container, false);
        progressDialog = Utils.getProgreesDialog(getContext(), getString(R.string.data_fetch_message));
        ListView movieListView = view.findViewById(R.id.lv_movie_list);
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
        movieListViewModel = new ViewModelProvider(requireActivity())
                        .get(MovieListViewModel.class);
        movieListViewModel.getAvailableMovieList().observe(this,this);
        fetchOnlineData();
    }
    private void initializeViewAdapter() {
        this.movieListViewAdapter = new MovieListViewAdapter(getContext(),
                movieListViewModel.getAvailableMovieList().getValue());
    }
    private void fetchOnlineData() {
        if (Utils.isInternetAvailable(getContext())) {
            movieListViewModel.fetchMovieList(getResources().getConfiguration().locale.toString());
        } else {
            AlertDialog alertDialog = Utils.showErrorDialog(getContext(),
                    getString(R.string.no_internet_message),
                    getString(R.string.turn_on_internet),
                    this);
            alertDialog.show();
        }
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
              fetchOnlineData();
        }
    }
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        fetchOnlineData();
    }
    @Override
    public void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }
}
