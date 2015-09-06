package com.example.ahmad.popularmovies_final;


import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.example.ahmad.popularmovies_final.Data.MoviesContract;
import com.example.ahmad.popularmovies_final.Data.MoviesContract.MoviesEntry;
import com.example.ahmad.popularmovies_final.POJOs.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmad on 7/9/2015.
 * This Class to Load the Data into the MovieData Object
 */
public class UtilityMovieData {

    public static final int REQUEST_MOVIES = 1;
    public static final int REQUEST_REVIEWS = 2;
    public static final int REQUEST_MOVIE_VIDEO = 3;

    public static final String VIDEO_KEY = "key";
    public static final String VIDEO_NAME = "name";
    public static final String VIDEO_WEBSITE = "site";


    public static ContentValues[] prepareMoviesBulkData(String received_json)
    {
        JSONArray movies;
        ContentValues[] movies_values = null;
        try {
            movies = new JSONObject(received_json).getJSONArray("results");
            int length = movies.length();
            movies_values = new ContentValues[length];
            for (int i = 0; i < length; i++) {
                JSONObject movie = movies.getJSONObject(i);
                movies_values[i] = new ContentValues();
                movies_values[i].put(MoviesEntry.MOV_COL_ID, movie.getInt("id"));
                movies_values[i].put(MoviesEntry.MOV_COL_TITLE, movie.getString("title"));
                movies_values[i].put(MoviesEntry.MOV_COL_ORIGINAL_TITLE, movie.getString("original_title"));
                movies_values[i].put(MoviesEntry.MOV_COL_POSTER, movie.getString("poster_path"));
                movies_values[i].put(MoviesEntry.MOV_COL_BACKDROP, movie.getString("backdrop_path"));
                movies_values[i].put(MoviesEntry.MOV_COL_OVERVIEW, movie.getString("overview"));
                movies_values[i].put(MoviesEntry.MOV_COL_RELEASE_DATE, movie.getString("release_date"));
                movies_values[i].put(MoviesEntry.MOV_COL_POPULARITY, movie.getDouble("popularity"));
                movies_values[i].put(MoviesEntry.MOV_COL_VOTE_COUNTS, movie.getInt("vote_count"));
                movies_values[i].put(MoviesEntry.MOV_COL_VOTE_AVE, movie.getDouble("vote_average"));
                movies_values[i].put(MoviesEntry.MOV_COL_FAVORITE, 0);
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return movies_values;
    }

    public static ContentValues[] prepareReviewsBulkData(String received_json)
    {
        JSONArray reviews;
        ContentValues[] reviews_values = null;
        try {
            JSONObject incoming_data = new JSONObject(received_json);
            long movie_id = incoming_data.getLong("id");
            Log.d("review", String.valueOf(movie_id));
            reviews = incoming_data.getJSONArray("results");
            Log.d("review length", reviews.toString());
            int length = reviews.length();
            Log.d("review", "prepareReviewsBulkData length"+ length);
            reviews_values = new ContentValues[length];
            for (int i = 0; i < length; i++) {
                JSONObject review = reviews.getJSONObject(i);
                Log.d("review", "prepareReviewsBulkData " + review.toString());
                reviews_values[i] = new ContentValues();
                reviews_values[i].put(MoviesContract.ReviewsEntry.RELATED_MOVIE, movie_id);
                reviews_values[i].put(MoviesContract.ReviewsEntry.REVIEW_UNI_ID, review.getString("id"));
                Log.d("review", String.valueOf(review.getString("author")));
                reviews_values[i].put(MoviesContract.ReviewsEntry.REV_COL_AUTHOR, review.getString("author"));
                Log.d("review", String.valueOf(review.getString("content")));
                reviews_values[i].put(MoviesContract.ReviewsEntry.REV_COL_CONTENT, review.getString("content"));
            }


        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return reviews_values;
    }

    public static ContentValues[] prepareVideosData(String received_json) {
        ContentValues[] video_data = null;

        try {
            JSONArray results = new JSONObject(received_json).getJSONArray("results");
            int length =  results.length();
            video_data = new ContentValues[length];
            for (int i = 0; i <length ; i++) {
                JSONObject video = results.getJSONObject(i);
                video_data[i] = new ContentValues();
                //Log.d("", "prepareVideosData KEY  : "+ video.getString(VIDEO_KEY));
                video_data[i].put(VIDEO_KEY, video.getString(VIDEO_KEY));
                //Log.d(TAG, "prepareVideosData KEY  : " + video.getString(VIDEO_NAME));
                video_data[i].put(VIDEO_NAME, video.getString(VIDEO_NAME));
                //Log.d(TAG, "prepareVideosData KEY  : " + video.getString(VIDEO_WEBSITE));
                video_data[i].put(VIDEO_WEBSITE, video.getString(VIDEO_WEBSITE));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return video_data;
    }


    public static Uri buildUriForTheVideo(String s)
    {
        return Uri.parse("https://www.youtube.com/watch?").buildUpon().appendQueryParameter("v", s).build();
    }


//this piece of code belongs to making the movie's data parcelables and moving it around!
/*
    private static String LOG_TAG = "utility";
    private static int length;
    private static JSONArray results;
    private static MovieData[] movie_data = null;
    private static ArrayList<MovieData> movies_datas = new ArrayList<>();



    public static int getNumerOfMovies(String recieved_json) {
        try {
            results = new JSONObject(recieved_json).getJSONArray("results");
            length = results.length();

        } catch (JSONException e) {
            length = 0;
            e.printStackTrace();
        }
        return length;
    }

    public static void fillEachMovieData(ArrayList<MovieData> movies_data) {
        JSONObject mJson;
        Log.d("parsedata", String.valueOf(length));
        for (int i = 0; i < length; i++) {
            try {

                //Get JSON Object to refer to each movie data easily.
                mJson = results.getJSONObject(i);
                movies_data.add(i, new MovieData().set_id(mJson.getInt("id"))
                        .originaltitle(mJson.getString("original_title"))
                        .title(mJson.getString("title")).averagevote((float) mJson.getDouble("vote_average"))
                        .backdropPath(mJson.getString("backdrop_path"))
                        .overview(mJson.getString("overview")).posterPath(mJson.getString("poster_path"))
                        .releaseDate(mJson.getString("release_date")));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("parsedata", String.valueOf(movies_data));
    }

*/

}
