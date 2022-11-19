package com.example.reeadigital.list.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.reeadigital.Utils;
import com.example.reeadigital.api.RetrofitClientInstance;
import com.example.reeadigital.api.GetMovieLIstService;
import com.example.reeadigital.list.model.Movie;
import com.example.reeadigital.list.model.MovieApiResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListRepository implements Callback<MovieApiResponse>{
    MovieApiResponse currentMovieApiResponse;
    MovieListApiListener movieListApiListener;
    int currentPageNo;
    public MovieListRepository(MovieListApiListener movieListApiListener) {
        this.currentMovieApiResponse = new MovieApiResponse();
        this.currentPageNo = 0;
        this.movieListApiListener = movieListApiListener;
    }
    public void requestMovieListData(String language) {
        if (currentPageNo == 0) {
            currentPageNo++;
            enqueueMovieListDataRequest(language,Integer.toString(currentPageNo));
        } else if ( currentPageNo <= getTotalPageNo()) {
            currentPageNo++;
            enqueueMovieListDataRequest(language,Integer.toString(currentPageNo));
        }
    }

    private void enqueueMovieListDataRequest(String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList(Utils.MOVIE_LIST_API_KEY,
                language,pageNo);
        call.enqueue(this);
    }
    public Integer getTotalPageNo() {
        if (currentMovieApiResponse.getTotalPages() != null) {
            return currentMovieApiResponse.getTotalPages();
        } else return 0;
    }
    @Override
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        //sets latest api response as currentMovieApiResponse
        currentMovieApiResponse = response.body();
        movieListApiListener.onMovieListRetrieved(response.body().getMovieList());
    }
    @Override
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e("MovieListRepository", t.getLocalizedMessage());
    }
}
