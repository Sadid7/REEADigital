package com.example.reeadigital;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.reeadigital.apiCall.RetrofitClientInstance;
import com.example.reeadigital.apiCall.GetMovieLIstService;
import com.example.reeadigital.list.Movie;
import com.example.reeadigital.list.MovieApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository implements Callback<MovieApiResponse>{
    MutableLiveData<List<Movie>> movieListData;

    public Repository() {
        this.movieListData  = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> requestMovieListData(String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList(Utils.apiKey,
                "en-US","4");
        call.enqueue(this);
        return movieListData;
    }

    public MutableLiveData<List<Movie>> getMovieListData() {
        return movieListData;
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

        movieListData.setValue(response.body().getMovieList());
    }

    @Override
    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
        Log.e("Sabid", t.getLocalizedMessage());
        movieListData.setValue(null);
        //Failure Messsage
    }
}
