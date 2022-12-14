package com.example.reeadigital.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**retrofit client for api calls*/
public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
