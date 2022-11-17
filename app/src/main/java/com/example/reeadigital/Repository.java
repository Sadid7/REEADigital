package com.example.reeadigital;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.reeadigital.apiCall.RetrofitClientInstance;
import com.example.reeadigital.apiCall.GetMovieLIstService;
import com.example.reeadigital.list.model.Movie;
import com.example.reeadigital.list.model.MovieApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository implements Callback<MovieApiResponse>{
    MutableLiveData<List<Movie>> allMovieListData;
    MovieApiResponse currentMovieApiResponse;
    Integer currentPageNo;

    public Repository() {
        this.allMovieListData = new MutableLiveData<>();
        this.currentMovieApiResponse = new MovieApiResponse();
        this.currentPageNo = 0;
    }

    public void requestMovieListData(String language) {
        if (currentPageNo == 0) {
            currentPageNo++;
            enqueueMovieListDataRequest(language,currentPageNo.toString());
        } else if (currentPageNo <= getTotalPageNo()) {
            currentPageNo++;
            enqueueMovieListDataRequest(language,currentPageNo.toString());
        }
    }

    private void enqueueMovieListDataRequest(String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList(Utils.MOVIE_LIST_API_KEY,
                "en-US",pageNo);
        call.enqueue(this);
    }

    public MutableLiveData<List<Movie>> getAllMovieListData() {
        return allMovieListData;
    }
/*pagination*/
    public Integer getTotalPageNo() {
        return currentMovieApiResponse.getTotalPages();
    }
    public Integer getValidPageNo() {
        if (currentMovieApiResponse.getPageNo() == null) {
            return 1;
        } else if (currentMovieApiResponse.getPageNo() <= getTotalPageNo()) {
            return currentMovieApiResponse.getPageNo();
        } else return currentMovieApiResponse.getPageNo();
    }

/*    private String getApiKeyFromSharedPref() {
        SharedPreferences sharedPref = context.getSharedPreferences(Utils.sharedPrefFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getString(Utils.apiKeyName,"");
    }*/

    @Override
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        Log.e("Sabid", response.code()+"");
        //Log.e("Sabid",    response.body().getMovieList().get(0).getMovieDetails());
        currentMovieApiResponse = response.body();
        if (allMovieListData.getValue() == null) {
            allMovieListData.postValue(response.body().getMovieList());
        } else {
           appendMovieListData(response.body().getMovieList());
        }
    }

    @Override
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e("Sabid", t.getLocalizedMessage());
        allMovieListData.setValue(null);
        //Failure Messsage
    }

    private void appendMovieListData(List<Movie> movieList) {
        List<Movie> movies = allMovieListData.getValue();
        movies.addAll(movieList);
        allMovieListData.postValue(movies);
    }
}
