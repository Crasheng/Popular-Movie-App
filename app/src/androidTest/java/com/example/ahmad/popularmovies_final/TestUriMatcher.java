package com.example.ahmad.popularmovies_final;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.ahmad.popularmovies_final.data.MovieProvider;
import com.example.ahmad.popularmovies_final.data.MoviesContract;
import com.example.ahmad.popularmovies_final.data.MoviesContract.MoviesEntry;

/**
 * Created by Ahmad on 8/3/2015.
 */
public class TestUriMatcher extends AndroidTestCase {

    static final String LOG_TAG = "babe";
    private static final long test_id = 10;
    private final static Uri TEST_MOVIE_URI = MoviesEntry.CONTENT_URI;
    private final static Uri TEST_MOVIE_SORT_URI = MoviesEntry.buildMovieWithSortUri(MoviesEntry.MOV_COL_POPULARITY);
    private final static Uri TEST_MOVIE_DETAIL_URI = MoviesEntry.buildMovieWithUniIdUri(test_id);
    private final static Uri TEST_REVIEW_URI = MoviesContract.ReviewsEntry.CONTENT_URI;

    public void testUriMatcher() {
        Log.d(LOG_TAG, String.valueOf(TEST_MOVIE_URI));
        Log.d(LOG_TAG, String.valueOf(TEST_REVIEW_URI));
        Log.d(LOG_TAG, TEST_MOVIE_DETAIL_URI.getLastPathSegment());
        Log.d(LOG_TAG, String.valueOf(TEST_MOVIE_SORT_URI));

        UriMatcher matcher = MovieProvider.buildUriMatcher();
        Log.d("babe", String.valueOf(TEST_MOVIE_DETAIL_URI));
        assertEquals("Error in matching movies", matcher.match(TEST_MOVIE_URI), MoviesContract.MOVIES);
        assertEquals("Error in matching movies_stage", matcher.match(TEST_MOVIE_SORT_URI), MoviesContract.MOVIES_STAGE_SORT);
        assertEquals("Error in matching movie details", matcher.match(TEST_MOVIE_DETAIL_URI), MoviesContract.MOVIES);
        assertEquals("THE ID OF the movie", TEST_MOVIE_DETAIL_URI.getQueryParameter(MoviesEntry.MOV_COL_ID), String.valueOf(test_id));
        assertEquals("Error in matching review uri", matcher.match(TEST_REVIEW_URI), MoviesContract.REVIEWS);

    }

}
