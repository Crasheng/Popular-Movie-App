package com.example.ahmad.popularmovies_final.Intenet;

import com.example.ahmad.popularmovies_final.POJOs.Movies.MoviesResponse;
import com.example.ahmad.popularmovies_final.POJOs.Reviews.ReviewsResponse;
import com.example.ahmad.popularmovies_final.POJOs.Videos.VideosResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by crasheng on 9/26/15.
 */
public interface InternetRequests {


    @GET("/3/discover/movie")
    Call<MoviesResponse> getMoviesToStage(@Query("sort_by") String sort_type,
                                               @Query("api_key") String api_key);

    @GET("/3/movie/{id}/reviews")
    Call<ReviewsResponse> getReviewsAboutMovie(@Path("id") Integer id);


    @GET("/3/movie/{id}/videos")
    Call<VideosResponse> getVideosOfMovie(@Path("id") Integer id);



}
