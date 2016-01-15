package com.example.ahmad.popularmovies_final.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ahmad.popularmovies_final.data.MoviesContract.FavouriteEntry;
import com.example.ahmad.popularmovies_final.data.MoviesContract.MoviesEntry;
import com.example.ahmad.popularmovies_final.data.MoviesContract.ReviewsEntry;

/**
 * Created by Ahmad on 7/23/2015.
 */
public class AppDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 10;
    public static final String DATABASE_NAME = "movie.db";

    public AppDBHelper(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE "+ MoviesEntry.TABLE_NAME +
                "("+ MoviesEntry._ID          + " INTEGER PRIMARY KEY,"+
                MoviesEntry.MOV_COL_ID        + " INTEGER NOT NULL UNIQUE,"+
                MoviesEntry.MOV_COL_TITLE          +" TEXT NOT NULL,"+
                MoviesEntry.MOV_COL_ORIGINAL_TITLE +" TEXT NOT NULL,"+
                MoviesEntry.MOV_COL_POSTER         +" TEXT NOT NULL,"+
                MoviesEntry.MOV_COL_BACKDROP       +" TEXT NOT NULL,"+
                MoviesEntry.MOV_COL_OVERVIEW       +" TEXT NOT NULL,"+
                MoviesEntry.MOV_COL_RELEASE_DATE   +" TEXT NOT NULL,"+
                MoviesEntry.MOV_COL_POPULARITY     +" REAL NOT NULL, "+
                MoviesEntry.MOV_COL_VOTE_COUNTS    +" INTEGER NOT NULL,"+
                MoviesEntry.MOV_COL_VOTE_AVE       +" REAL NOT NULL,"+
                MoviesEntry.MOV_COL_FAVOURITE +" BOOLEAN NOT NULL DEFAULT 0);";



        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE "+ ReviewsEntry.TABLE_NAME+
                "("+ ReviewsEntry._ID   + " INTEGER PRIMARY KEY,"+
                ReviewsEntry.REVIEW_UNI_ID + " TEXT UNIQUE NOT NULL,"+
                ReviewsEntry.RELATED_MOVIE + " INTEGER NOT NULL, "  +
                ReviewsEntry.REV_COL_AUTHOR  + " TEXT NOT NULL," +
                ReviewsEntry.REV_COL_CONTENT + " TEXT NOT NULL" + ");";
//                "FOREIGN KEY("+ ReviewsEntry.RELATED_MOVIE +") REFERENCES "+ MoviesEntry.TABLE_NAME +" ( "+ MoviesEntry.MOV_COL_ID +"));";


        final String SQL_CREATE_FAVOURITE_TABLE = " CREATE TABLE " + FavouriteEntry.TABLE_NAME +
                "(" + FavouriteEntry._ID + " INTEGER PRIMARY KEY,  " +
                FavouriteEntry.RELATED_MOVIE_COL + " INTEGER UNIQUE NOT NULL " + ");";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ReviewsEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME + ";");
        onCreate(db);
    }
}
