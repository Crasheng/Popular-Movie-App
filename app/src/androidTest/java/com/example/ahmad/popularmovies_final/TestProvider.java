package com.example.ahmad.popularmovies_final;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.ahmad.popularmovies_final.Data.AppDBHelper;
import com.example.ahmad.popularmovies_final.Data.MoviesContract.*;



/**
 * Created by Ahmad on 8/6/2015.
 */
public class TestProvider extends AndroidTestCase {

    private static final String LOG_TAG = "test_provider";

    public void testQuery() {
        Uri movie_uri = MoviesEntry.CONTENT_URI;
        Uri review_uri = ReviewsEntry.CONTENT_URI;


        AppDBHelper dbHelper = new AppDBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentResolver cr = mContext.getContentResolver();
        int drows = getContext().getContentResolver().delete(MoviesEntry.CONTENT_URI, null, null);
        Log.d(LOG_TAG, String.valueOf(drows));
        int drows_r = getContext().getContentResolver().delete(ReviewsEntry.CONTENT_URI, null, null);
        Log.d(LOG_TAG, String.valueOf(drows_r));

        //Insert Review into movie table
        ContentValues movie_data = TestUtilities.createMoviesValues();
        Uri movie_insert_uri = cr.insert(movie_uri, movie_data);
        Log.d(LOG_TAG, String.valueOf(movie_insert_uri));
        assertEquals("Suppose to be the first entry to the table", movie_insert_uri.getLastPathSegment(), "1");

        //Insert Data into Reviews Table
        ContentValues review_data = TestUtilities.createReviewsValues();
        Uri review_insert_uri = cr.insert(review_uri, review_data);
        //assertEquals("Suppose to be the first entry to the table", movie_insert_uri.getLastPathSegment(), 1);
        Log.d(LOG_TAG, String.valueOf(review_insert_uri));
//        assertTrue(false);
        db.close();

        Cursor c = cr.query(MoviesEntry.buildMovieWithUniIdUri(TestUtilities.TEST_MOVIE_ID), null,
                null,
                null,
                null);

        assertTrue("Failed Query", c.moveToFirst());
        c.close();

        Cursor ac = cr.query(MoviesEntry.buildMovieWithSortUri(MoviesEntry.MOV_COL_POPULARITY),
                new String[]{MoviesEntry.MOV_COL_POSTER},
                null,
                null,
                MoviesEntry.MOV_COL_POPULARITY + MoviesEntry.SORT_ORDER
        );
        assertTrue("Failed Query", ac.moveToFirst());
        Log.d(LOG_TAG, String.valueOf(ac.getColumnCount()));
        ac.close();


    }
}
