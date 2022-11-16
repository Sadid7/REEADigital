package com.example.reeadigital;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.reeadigital.list.GetMovieLIstService;
import com.example.reeadigital.list.Movie;
import com.example.reeadigital.list.MovieApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Context context;

    public Repository(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Movie>> getTasks(String apiKey, String language, String pageNo) {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<MovieApiResponse> call = service.getMovieList("15673ce2e17445aae3ae809f818eb6a3",
                "en-US","4");
        final MutableLiveData<List<Movie>> movieListData = new MutableLiveData<>();
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                Log.e("Sabid", response.code()+"");
                Log.e("Sabid",    call.request().url().toString());

                movieListData.setValue(response.body().getMovieList());
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                Log.e("Sabid", t.getLocalizedMessage());
                movieListData.setValue(null);
                //Failure Messsage
            }
        });

        return movieListData;
    }
}
