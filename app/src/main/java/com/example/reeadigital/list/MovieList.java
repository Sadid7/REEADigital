package com.example.reeadigital.list;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.reeadigital.R;
import com.example.reeadigital.Utils;

import java.util.List;

public class MovieList extends Fragment {

    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.fragment_movie_list, container, false);
        progressDialog = Utils.getProgreesDialog(getContext(), getString(R.string.data_fetch_message));
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    private void setupViewModel() {
        //MainViewModelFactory factory = new MainViewModelFactory(this);
        MovieListViewModel viewModel = new MovieListViewModel(getContext());
                //ViewModelProviders.of(this,factory)
                //.get(MainViewModel.class);
        viewModel.getTasks().observe(this, movies -> {
            if(movies.isEmpty()){
                progressDialog.dismiss();
            }
            else{
                progressDialog.dismiss();
                generateMovieList( movies );
            }
        });
    }

    private void generateMovieList(List<Movie> movies) {

    }
}
