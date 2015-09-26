package com.example.ahmad.popularmovies_final.Intenet;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by crasheng on 9/26/15.
 */
public class RESTAdapter {

    public Retrofit retrofit;
    InternetRequests requests;

    public RESTAdapter(String base_url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requests = retrofit.create(InternetRequests.class);
    }

    public InternetRequests getInternetGate(){return requests;}
}
