package com.example.ahmad.popularmovies_final;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.ahmad.popularmovies_final.data.MoviesContract;

import java.util.Map;
import java.util.Set;

/**
 * Created by Ahmad on 7/25/2015.
 */
public class TestUtilities extends AndroidTestCase {

    public static final Integer TEST_MOVIE_ID = 49049;

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createMoviesValues() {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_ID, TEST_MOVIE_ID);
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_TITLE, "baby");
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_ORIGINAL_TITLE, "baby_begad");
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_POSTER, "poster_link");
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_BACKDROP, "backdrop_link");
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_OVERVIEW, "overview");
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_RELEASE_DATE, "2015");
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_POPULARITY, 78.9456);
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_VOTE_COUNTS, 12345);
        weatherValues.put(MoviesContract.MoviesEntry.MOV_COL_VOTE_AVE, 9.0);
        return weatherValues;


    }

    static ContentValues createReviewsValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.ReviewsEntry.RELATED_MOVIE, TEST_MOVIE_ID);
        testValues.put(MoviesContract.ReviewsEntry.REV_COL_AUTHOR, "Ahmed Adel");
        testValues.put(MoviesContract.ReviewsEntry.REV_COL_CONTENT, "Obaa 3ala l film dahhh");

        return testValues;
    }
}
