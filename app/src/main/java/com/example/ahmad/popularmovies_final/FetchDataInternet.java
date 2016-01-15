package com.example.ahmad.popularmovies_final;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.security.auth.callback.Callback;

/*
 * Overriding Asynctask class to create threads for fetching data from internet
 * */
class FetchDataInternet extends AsyncTask<String, Void, String> implements Callback {


    public static final String TAG = "fetching";

    private Exception mexcption = null;
    //API KEY of IMDB Database
    private static final String API_KEY = "b980eff87da0a635d18c8bd29bad78b0";
    private FetchedDataReady listener;
    private final int DATA_TYPE;

    //Constructor.
    FetchDataInternet( FetchedDataReady mr_Callback, int mode)
    {
        //register here for the passed listener in the created instance.
        this.listener = mr_Callback;

        //Assign the type of the data required.
        DATA_TYPE = mode;
    }

    public interface FetchedDataReady {
        void RequestedDataReady(ContentValues[] cv, int mode);
    }

    @Override
    protected void onPostExecute(String s) {

        switch (DATA_TYPE){
            case UtilityMovieData.REQUEST_MOVIES:
                //Prepare the required data.
                ContentValues[] fetched_data = UtilityMovieData.prepareMoviesBulkData(s);
                //Fire the Callback on the listener Fragment.
                listener.RequestedDataReady(fetched_data, UtilityMovieData.REQUEST_MOVIES);
                break;
            case UtilityMovieData.REQUEST_REVIEWS:
                //Prepare the required data.
                ContentValues[] reviews_data = UtilityMovieData.prepareReviewsBulkDat(s);
                //Fire the Callback on the listener Fragment.
                listener.RequestedDataReady(reviews_data, UtilityMovieData.REQUEST_REVIEWS);
                break;
            case UtilityMovieData.REQUEST_MOVIE_VIDEO:
                ContentValues[] video_data  = UtilityMovieData.prepareVideosData(s);
                listener.RequestedDataReady(video_data, UtilityMovieData.REQUEST_MOVIE_VIDEO);
                break;
            default:
                throw new UnsupportedOperationException("Invalid Mode");
        }

    }

    @Override
    protected String doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String fetched_movies = null;

        try {

            //Helper for Uri building
            Uri builderUri;
            //REQUEST URL
            URL url;

            // Construct the URL for the MoviesData query
            final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

            final String api_key_param = "api_key";
            final String sort_param = "sort_by";

            switch (DATA_TYPE){
                case UtilityMovieData.REQUEST_MOVIES:
                    builderUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                            .appendQueryParameter(sort_param, params[0])
                            .appendQueryParameter(api_key_param, API_KEY).build();

                    url = new URL(builderUri.toString());
                    break;
                case UtilityMovieData.REQUEST_MOVIE_VIDEO:
                    builderUri = buildMovieRelatedVideoUri(Integer.parseInt(params[0])).buildUpon()
                            .appendQueryParameter(api_key_param, API_KEY).build();
                    url = new URL(builderUri.toString());
                    break;
                case UtilityMovieData.REQUEST_REVIEWS:
                    builderUri = buildMovieReviewsUri(Integer.parseInt(params[0])).buildUpon()
                            .appendQueryParameter(api_key_param, API_KEY).build();
                    url = new URL(builderUri.toString());
                    break;
                default:
                    throw new ExceptionInInitializerError();
            }

            Log.d(TAG, "before open onto URL:");
            // Create the request to OpenWeatherMap, and open the connection3
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                fetched_movies = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            Log.d(TAG, "after  check inputstreanm :");

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                fetched_movies = null;
            }
            fetched_movies = buffer.toString();
            Log.v("Movies DATA FETCHED", "Fetched_movies" + fetched_movies);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            mexcption = e;
            Log.e("PlaceholderFragment", "Error ", e);
            Log.d("Error", "BEBE");
            Log.d(FetchDataInternet.TAG, "OFFF the internet Closed");
            return null;
            // If the code didn't successfully get the movie data, there's no point in attempting
            // to parse it.
//            fetched_movies = null;

        }
        finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        Log.d("fetcheee", fetched_movies);
        return fetched_movies;
    }

    //this method to help build the url for a movie!.
    private Uri buildMovieRelatedVideoUri(int id)
    {
        return Uri.parse("http://api.themoviedb.org/3/movie/" + String.valueOf(id) + "/videos?");
    }

    private Uri buildMovieReviewsUri(int id)
    {
        return Uri.parse("http://api.themoviedb.org/3/movie/"+String.valueOf(id)+"id/reviews?");
    }
}