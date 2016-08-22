package io.github.vipinagrahari.epragati.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vivek on 22/8/16.
 */
public class ServiceGenerator {

    static ApiService apiService;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(ApiService.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    public static ApiService getInstance() {
        if (apiService != null) return apiService;
        Retrofit retrofit = builder.build();
        return retrofit.create(ApiService.class);
    }
}
