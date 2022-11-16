package com.example.reeadigital.list.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MovieListFragment extends Fragment implements Observer<List<Movie>>{

    ProgressDialog progressDialog;
    MovieListViewModel movieListViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.fragment_movie_list_layout, container, false);
        progressDialog = Utils.getProgreesDialog(getContext(), getString(R.string.data_fetch_message));
        //return super.onCreateView(inflater, container, savedInstanceState);
        if (movieListViewModel == null) {
            setupViewModel();
        }
        return view;
    }

    private void setupViewModel() {

        /*//MainViewModelFactory factory = new MainViewModelFactory(this);
        MovieListViewModel viewModel = new MovieListViewModel(getContext());
                ViewModelProvider.(this,factory)
                .get(MainViewModel.class);*/
        progressDialog.show();
        movieListViewModel = new ViewModelProvider(this)
                        .get(MovieListViewModel.class);
        movieListViewModel.getMovieList().observe(this,this);
        movieListViewModel.loadMovieList("","","");
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

        Log.e("MovieLIstFrag", movieApiResponse.get(0).getMovieDetails());
    }

}
