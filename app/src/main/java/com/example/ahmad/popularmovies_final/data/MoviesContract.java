package com.example.ahmad.popularmovies_final.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ahmad on 7/23/2015.
 */
public class MoviesContract {

    /*Content Authority*/
    public static final String CONTENT_AUTHORITY = "com.example.ahmad.popularmovies_final";

    //BASE URI CONTENT
    //u can use it in the contentProviderClient class, to element some of framework work
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Name of the movie table
    public static final String PATH_MOVIE = "movie";

    //Name of the review table
    public static final String PATH_REVIEW = "review";

    //Name of the favorite table
    public static final String PATH_FAVOURITE = "favourite";



    //for changing operations
    public static final int MOVIES = 100;
    //for query all movies based on the user preferred sort type
    public static final int MOVIES_STAGE_SORT = 101;
    //for query all detail data of movie
    public static final int MOVIE_DETAIL = 102;
    //for changing operations on review table
    public static final int REVIEWS = 300;
    //for change operations on favorite table
    public static final int FAVOURITE = 400;



    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;


        public static final String SORT_ORDER = " DESC ";


        //table name
        public static final String TABLE_NAME = "movie";

        //columns names
        public static final String MOV_COL_ID = "umovie_id";
        public static final String MOV_COL_TITLE = "title";
        public static final String MOV_COL_OVERVIEW = "overview";
        public static final String MOV_COL_FAVOURITE = "favorite";
        public static final String MOV_COL_POPULARITY = "popularity";
        public static final String MOV_COL_VOTE_COUNTS = "vote_count";
        public static final String MOV_COL_ORIGINAL_TITLE = "original_title";
        public static final String MOV_COL_POSTER = "poster_path";
        public static final String MOV_COL_BACKDROP = "backdrop_path";
        public static final String MOV_COL_RELEASE_DATE = "release_date";
        public static final String MOV_COL_VOTE_AVE = "vote_average";
        public static final String LIMIT = "20";


        //get the movie detail uri, by populate the  movie has been selected
        public static Uri buildMovieWithUniIdUri(long umovie_id)
        {
            return CONTENT_URI.buildUpon().appendQueryParameter(MOV_COL_ID, String.valueOf(umovie_id)).build();
        }

        //BUILD URI for fetching movies with specific sorting type
        public  static Uri buildMovieWithSortUri(String sort_type)
        {
            return CONTENT_URI.buildUpon().appendPath(sort_type).build();
        }

        //Build URI FOR GET MOVIE WITH ID.
        public static Uri buildMovieWithIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class ReviewsEntry implements  BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        //Table Name
        public static final String TABLE_NAME = "review";

        //tables columns name
        public static final String REV_COL_AUTHOR = "author";
        public static final String REV_COL_CONTENT = "content";
        public static final String RELATED_MOVIE = "related_movie_id";
        public static final String REVIEW_UNI_ID = "review_uni_id";

        public static Uri buildMovieReviewsUri(String id)
        {
            return CONTENT_URI.buildUpon().appendQueryParameter(ReviewsEntry.RELATED_MOVIE, id).build();
        }

        public static Uri buildReviewWithMovieId(long movie_id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, movie_id);
        }
    }

    public static final class FavouriteEntry implements BaseColumns{

        public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        public static final String TABLE_NAME = PATH_FAVOURITE;

        public static final String RELATED_MOVIE_COL = "related_movie";

        public static Uri buildFavoriteUri()
        {
            return CONTENT_URI;
        }
    }
}

