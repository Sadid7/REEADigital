package com.example.reeadigital;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.reeadigital.list.GetMovieLIstService;
import com.example.reeadigital.list.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Context context;

    public Repository(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Movie>> getTasks() {
        GetMovieLIstService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetMovieLIstService.class);
        Call<List<Movie>> call = service.getMovieList();
        final MutableLiveData<List<Movie>> movieListData = new MutableLiveData<>();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieListData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                movieListData.setValue(null);
                //Failure Messsage
            }
        });
        return movieListData;
    }
}
