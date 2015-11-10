package com.example.ahmad.popularmovies_final.Intenet;

import com.example.ahmad.popularmovies_final.POJOs.Movies.MovieResponse;
import com.example.ahmad.popularmovies_final.POJOs.Reviews.ReviewsResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by crasheng on 9/26/15.
 */
public interface InternetRequests {


    @GET("/3/discover/movie")
    Call<MovieResponse> getMoviesToStage(@Query("sort_by") String sort_type,
                                               @Query("api_key") String api_key);

    @GET("/3/movie/{id}/reviews")
    Call<ReviewsResponse> getReviewsAboutMovie(@Path("id") Integer id,
                                               @Query("api_key") String api_key);


    @GET("/3/movie/{id}/videos")
    Call<com.example.ahmad.popularmovies_final.POJOs.Videos.VideosResponse> getVideosOfMovie(@Path("id") Integer id,
                                          @Query("api_key") String api_key);


}
