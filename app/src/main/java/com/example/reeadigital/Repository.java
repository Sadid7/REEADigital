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

    public Repository() {
        this.allMovieListData = new MutableLiveData<>();
        this.currentMovieApiResponse = new MovieApiResponse();
    }

    public MutableLiveData<List<Movie>> requestMovieListData(String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList(Utils.apiKey,
                "en-US","4");
        call.enqueue(this);
        return allMovieListData;
    }

    public MutableLiveData<List<Movie>> getAllMovieListData() {
        return allMovieListData;
    }

    public Integer getTotalPageNo() {
        return currentMovieApiResponse.getTotalPages();
    }
    public Integer getCurrentPageNo() {
        return currentMovieApiResponse.getPageNo();
    }

/*    private String getApiKeyFromSharedPref() {
        SharedPreferences sharedPref = context.getSharedPreferences(Utils.sharedPrefFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getString(Utils.apiKeyName,"");
    }*/

    @Override
    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
        Log.e("Sabid", response.code()+"");
        Log.e("Sabid",    response.body().getMovieList().get(0).getMovieDetails());
        currentMovieApiResponse = response.body();
        if (allMovieListData.getValue() == null) {
            allMovieListData.setValue(response.body().getMovieList());
        } else {
            allMovieListData.getValue().addAll(response.body().getMovieList());
        }
    }

    @Override
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e("Sabid", t.getLocalizedMessage());
        allMovieListData.setValue(null);
        //Failure Messsage
    }
}
