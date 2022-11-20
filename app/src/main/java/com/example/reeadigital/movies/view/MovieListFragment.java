package com.example.reeadigital.movies.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.reeadigital.movies.viewmodel.MovieListViewModel;
import com.example.reeadigital.movies.model.Movie;

import java.util.List;
/**Simple fragment view fetches movie list from online and shows it on a listview*/
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
        /*initializes viewmodel and other dependent objects*/
        if (movieListViewModel == null) {
            setupInitialOnlineDataCall();
        }
        ListView movieListView = view.findViewById(R.id.lv_movie_list);
        movieListView.setAdapter(movieListViewAdapter);
        movieListView.setOnScrollListener(this);
        return view;
    }

    private void setupInitialOnlineDataCall() {
        progressDialog = Utils.getProgreesDialog(getContext(), getString(R.string.data_fetch_message));
        progressDialog.show();
        initializeViewModel();
        fetchOnlineData();
        this.movieListViewAdapter = new MovieListViewAdapter(getContext(),
                movieListViewModel.getAvailableMovieList().getValue());
    }
    private void initializeViewModel() {
        movieListViewModel = new ViewModelProvider(requireActivity())
                        .get(MovieListViewModel.class);
        movieListViewModel.getAvailableMovieList().observe(this,this);
    }
    /**
    * requests to fetch online data through viewmodel object
     * sends current language locale as parameter
     * after checking internet availibility
     * otherwise shows error dialog*/
    private void fetchOnlineData() {
        if (Utils.isInternetAvailable(getContext())) {
            movieListViewModel.fetchMovieListData(getResources().getConfiguration().locale.toString());
        } else {
            AlertDialog alertDialog = Utils.showErrorDialog(getContext(),
                    getString(R.string.no_internet_message),
                    getString(R.string.turn_on_internet),
                    this);
            alertDialog.show();
        }
    }

    @Override
    public void onChanged(List<Movie> movieList) {
            if(movieList.isEmpty()){
                progressDialog.dismiss();
            }
            else{
                progressDialog.dismiss();
                generateMovieList(movieList);
            }
    }
    /**
     * updates data in view adapter which it shows in list view*/
    private void generateMovieList(List<Movie> movieApiResponse) {
        movieListViewAdapter.setMovieList(movieApiResponse);
        movieListViewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    /**
     * to make api call when user reaches the bottom of the listview*/
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if(lastItem !=0 && lastItem == totalItemCount) {
              fetchOnlineData();
        }
    }
    /**
     * when retry button of error dialog is pressed calls for a retry of api call*/
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
