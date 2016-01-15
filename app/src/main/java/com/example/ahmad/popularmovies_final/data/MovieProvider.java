package com.example.ahmad.popularmovies_final.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.example.ahmad.popularmovies_final.data.MoviesContract.MoviesEntry;
import com.example.ahmad.popularmovies_final.data.MoviesContract.ReviewsEntry;
import com.example.ahmad.popularmovies_final.MoviesStageFragment;


//TODO - DONE!
//FAVOURITE TABLE
    //INSERT +
    //BULK INSERT -
    //QUERY +
    //DELETE +
    //UPDATE +
    //URi matcher +
//----------------------


/**
 * Created by Ahmad on 7/26/2015.
 */
public class MovieProvider extends ContentProvider {

    private SQLiteOpenHelper dbHelper ;

    final static SQLiteQueryBuilder sqlMoviesBuilderQuery = new SQLiteQueryBuilder();
    static
    {
        sqlMoviesBuilderQuery.setTables(
                MoviesContract.MoviesEntry.TABLE_NAME);
    }

    final static SQLiteQueryBuilder sqlReviewsBuilderQuery = new SQLiteQueryBuilder();
    static
    {
        sqlReviewsBuilderQuery.setTables(
                ReviewsEntry.TABLE_NAME);
    }

    final static SQLiteQueryBuilder sqlFavouriteBuilderQuery = new SQLiteQueryBuilder();
//    static
//    {
//        sqlFavouriteBuilderQuery.setTables(MoviesContract.FavouriteEntry.TABLE_NAME);
//    }

    @Override
    public boolean onCreate() {
        dbHelper  = new AppDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        UriMatcher matcher = buildUriMatcher();
        Cursor c;
        SQLiteDatabase db;
        switch (matcher.match(uri))
        {
            case MoviesContract.MOVIES:
                String value_id = uri.getQueryParameter(MoviesContract.MoviesEntry.MOV_COL_ID);
                if (value_id != null) {
                    Log.d("onloadfinish", "Entered this query");
                     db = dbHelper.getReadableDatabase();
                    //ToDo
                    Log.d("onloadfinish", value_id);
                    c = sqlMoviesBuilderQuery.query(db, projection, MoviesEntry.TABLE_NAME+"."+MoviesEntry.MOV_COL_ID + " = ? ",
                            new String[]{value_id},
                            null,
                            null,
                            null);
                }
                else
                {
                    db = dbHelper.getReadableDatabase();
                    c = db.rawQuery("select * from movie limit 1 ", null);
                }
                break;
            case MoviesContract.MOVIES_STAGE_SORT:
                db = dbHelper.getReadableDatabase();
                sortOrder = uri.getLastPathSegment();
                c = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder,
                        MoviesContract.MoviesEntry.LIMIT
                );
                break;
            case MoviesContract.REVIEWS:
                String movie_id = uri.getQueryParameter(ReviewsEntry.RELATED_MOVIE);
                if (movie_id == null) {
                    throw new UnsupportedOperationException("Invalid Review URI");
                }
                else {
                    db = dbHelper.getReadableDatabase();
                    c = sqlReviewsBuilderQuery.query(db, projection,
                            ReviewsEntry.TABLE_NAME + "." + ReviewsEntry.RELATED_MOVIE + "= ?", new String[]{movie_id},
                            null,
                            null,
                            null);
                }
                break;
            case  MoviesContract.FAVOURITE:
                String related_movie_id = uri.getQueryParameter(MoviesContract.FavouriteEntry.RELATED_MOVIE_COL);
                Log.d("fav_button", "query :  " + String.valueOf(related_movie_id));
                sqlFavouriteBuilderQuery.setTables(MoviesContract.FavouriteEntry.TABLE_NAME);
                db = dbHelper.getReadableDatabase();
                if (related_movie_id != null) {
                    c = sqlFavouriteBuilderQuery.query(db,
                            projection,
                            MoviesContract.FavouriteEntry.RELATED_MOVIE_COL + " = ? ",
                            new String[] {related_movie_id},
                            null,
                            null,
                            null);

                    Log.d("fav_button", "query : c : " + c.getCount());
                }
                else {
                    sqlFavouriteBuilderQuery.setTables(MoviesEntry.TABLE_NAME + " join " + MoviesContract.FavouriteEntry.TABLE_NAME + " on " + MoviesEntry.MOV_COL_ID  + " = " + MoviesContract.FavouriteEntry.RELATED_MOVIE_COL);
                    db = dbHelper.getReadableDatabase();
                    c = sqlFavouriteBuilderQuery.query(db,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            null);
                }

                break;
            default:
                throw new UnsupportedOperationException("Invalid URI");
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {

        UriMatcher matcher = buildUriMatcher();

        switch (matcher.match(uri)){
            case MoviesContract.MOVIES:
                return MoviesEntry.CONTENT_TYPE;
            case MoviesContract.REVIEWS:
                return MoviesContract.ReviewsEntry.CONTENT_TYPE;
            case MoviesContract.MOVIES_STAGE_SORT:
                return MoviesContract.ReviewsEntry.CONTENT_TYPE;
            case MoviesContract.MOVIE_DETAIL:
                return MoviesEntry.CONTENT_ITEM_TYPE;
            case MoviesContract.FAVOURITE:
                return MoviesContract.FavouriteEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("InValid Uri");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row_id;
        UriMatcher matcher = buildUriMatcher();
        switch (matcher.match(uri)){
            case MoviesContract.MOVIES :
                row_id = db.insertWithOnConflict(MoviesEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (row_id < 0) {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;
            case MoviesContract.REVIEWS:
                row_id = db.insertWithOnConflict(MoviesContract.ReviewsEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
                if (row_id < 0) {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;
            case MoviesContract.FAVOURITE:
                row_id = db.insertWithOnConflict(MoviesContract.FavouriteEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (row_id < 0) {
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Invalid Insertion Process");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,row_id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleted_rows;
        if (selection == null) {selection ="1";}
        switch (buildUriMatcher().match(uri))
        {
            case MoviesContract.MOVIES:
                deleted_rows = db.delete(MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MoviesContract.REVIEWS:
                deleted_rows = db.delete(MoviesContract.ReviewsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MoviesContract.FAVOURITE:
                deleted_rows = db.delete(MoviesContract.FavouriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new android.database.SQLException("Invalid Deletion process of Uri : " + uri );
        }
        if (deleted_rows != 0)
            getContext().getContentResolver().notifyChange(uri,null);
        return deleted_rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updated_rows;
        if (selection == null) {selection ="1";}
        switch (buildUriMatcher().match(uri))
        {
            case MoviesContract.MOVIES:
                updated_rows = db.update(MoviesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MoviesContract.REVIEWS:
                updated_rows = db.update(ReviewsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MoviesContract.FAVOURITE:
                updated_rows = db.update(MoviesContract.FavouriteEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new android.database.SQLException("Invalid Deletion process of Uri : " + uri );
        }
        if (updated_rows != 0)
            getContext().getContentResolver().notifyChange(uri,null);
        return updated_rows;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        UriMatcher matcher = buildUriMatcher();
        int inserted_rows;
        switch (matcher.match(uri))
        {
            case MoviesContract.MOVIES:
                db.beginTransaction();
                int rows = 0;
                try {
                        for (ContentValues value: values ) {
                            long row_id = db.insertWithOnConflict(MoviesEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                            Log.d(MoviesStageFragment.TAG, String.valueOf(row_id));
                            if (row_id != -1) {
                                rows++;
                                //Add Action here to update these rows!- if it does exist
                            }
                        }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                    inserted_rows = rows;
                }
                break;
            case MoviesContract.REVIEWS:
                db.beginTransaction();
                int r_rows = 0;
                try {
                    for (ContentValues value: values ) {
                        long row_id = db.insertWithOnConflict(ReviewsEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        if (row_id != -1) {
                            r_rows++;
                            //Add Action here to update these rows!- if it does exist
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                    inserted_rows = r_rows;
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }
        return inserted_rows;
    }

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority =  MoviesContract.CONTENT_AUTHORITY;


        //the order of that adding the Uris is very important, and i have to check first whether
        //it is an id "Number", and else if it is string segment.
        //According to the questions on StackOverFlow!
        /*http://stackoverflow.com/questions/5030094/problems-with-androids-urimatcher/15015687#15015687*/

        //--deprecated
        /*Hint
        * the second was  "/#", the third is "/*"
        * Try to flip the 2nd and 3rd statement around and the test fails
        * matcher.addURI(authority, "/"+MoviesContract.PATH_MOVIE + "/#", MoviesContract.MOVIE_DETAIL)
        * */

        //You can not see what in the hint above, but i have removed it
        //because i am using Uri.Builder.appendQueryParameter Method,So the last
        //segment will not be there anymore,so this matching pattern "#" (WildCard) has been removed
        matcher.addURI(authority, "/"+MoviesContract.PATH_MOVIE, MoviesContract.MOVIES);
        matcher.addURI(authority, "/"+MoviesContract.PATH_MOVIE + "/*", MoviesContract.MOVIES_STAGE_SORT);
        matcher.addURI(authority, "/"+MoviesContract.PATH_REVIEW, MoviesContract.REVIEWS);
        matcher.addURI(authority, "/"+MoviesContract.PATH_FAVOURITE, MoviesContract.FAVOURITE);
        return matcher;


    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
